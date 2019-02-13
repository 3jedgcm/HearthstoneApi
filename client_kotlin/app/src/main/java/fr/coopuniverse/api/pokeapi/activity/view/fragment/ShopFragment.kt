package fr.coopuniverse.api.pokeapi.activity.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapterStore
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager
import kotlinx.android.synthetic.main.inventory_fragment.*


class ShopFragment : androidx.fragment.app.Fragment(), CallBackDisplay, CallBackOnClickCard {

    private var tCredits: TextView? = null
    private var tCards: TextView? = null
    private var cost = 0
    private var idCard = "N/A"
    private var userCards_total = 0
    private var userMoney_total = 0
    private var flagUpdateListofItems = true

    override fun onClickCard(idCard: String, cost: Int,costStr:String) {
        this.cost = cost
        this.idCard = idCard
        if (this.idCard != null) {
            if (this.userMoney_total < this.cost) {
                Toast.makeText(context, this.activity?.getString(R.string.insufficient_credit), Toast.LENGTH_LONG).show()
                return
            } else if (this.userMoney_total == this.cost) {
                Toast.makeText(context, this.activity?.getString(R.string.attention_credit), Toast.LENGTH_LONG).show()
            }
            this.userCards_total++
            setTextCards(this.userCards_total.toString())
            CallHttpManager(callback = this, action = "SetOneCard", isActivateCallBack = true, idUser = Account.id, idCard = idCard, url = this.activity?.getString(R.string.url)).execute()
        }
    }

    override fun display(rep: Reponse, action: String) {

        var data: ArrayList<Card>? = null
        var money: String? = null
        var userCards: ArrayList<Card>? = null
        if (rep != null && rep.data != null) {
            data = rep.data.cards
            money = rep.data.money
            userCards = rep.data.inventory
        }

        when (action) {
            "GetAllCard" -> {
                var adapterReclViewcard = CardsListAdapterStore(data, targetFragment, this)
                recView_Inventory?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
                recView_Inventory?.adapter = adapterReclViewcard
            }
            "GetOneMoney" -> {
                if (money != null) {
                    if (money.toIntOrNull() != null) {
                        this.userMoney_total = money.toIntOrNull()!!
                    }
                    setTextCredits(money.toString())
                }
                CallHttpManager(callback = this, action = "GetCardByUserId", isActivateCallBack = true, idUser = Account.id, url = this.activity?.getString(R.string.url)).execute()
            }
            "GetCardByUserId" -> {
                if (userCards != null) {
                    var userCardsCount = userCards.size
                    if (userCards != null) {
                        this.userCards_total = userCardsCount
                    }
                    setTextCards(userCardsCount.toString())
                } else {
                    setTextCards("0")
                }
                if (flagUpdateListofItems) {
                    CallHttpManager(callback = this, action = "GetAllCard", isActivateCallBack = true, url = this.activity?.getString(R.string.url)).execute()
                }
            }
            "SetOneMoney" -> {
                if (this.cost != null) {
                    flagUpdateListofItems = false
                }
            }
            "SetOneCard" -> {
                this.userMoney_total = this.userMoney_total.minus(this.cost)
                setTextCredits(this.userMoney_total.toString())
                CallHttpManager(callback = this, action = "SetOneMoney", isActivateCallBack = true, idUser = Account.id, value = userMoney_total.toString(), url = "https://api.coopuniverse.fr/").execute()
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.tCredits = inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCredits);
        this.tCards = inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCards)
        this.getUserData()
        return inflater.inflate(fr.coopuniverse.api.pokeapi.R.layout.inventory_fragment, container, false)
    }

    fun getUserData() {
        CallHttpManager(callback = this, action = "GetOneMoney", isActivateCallBack = true, idUser = Account.id, url = this.activity?.getString(R.string.url)).execute()
    }

    fun setTextCredits(credits: String) {
        val infoCreditView = view!!.findViewById(fr.coopuniverse.api.pokeapi.R.id.tCredits) as TextView
        infoCreditView.text = tCredits!!.text.toString() + " " + credits + " credits "
    }

    fun setTextCards(cards: String) {
        val textView = view!!.findViewById(fr.coopuniverse.api.pokeapi.R.id.tCards) as TextView
        textView.text = tCards!!.text.toString() + " " + cards + " cards "
    }

    companion object {
        fun newInstance() = ShopFragment()
    }

}
