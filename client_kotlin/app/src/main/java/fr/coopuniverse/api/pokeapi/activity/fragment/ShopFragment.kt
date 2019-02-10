package fr.coopuniverse.api.pokeapi.activity.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapterStore
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import kotlinx.android.synthetic.main.inventory_fragment.*


class ShopFragment : androidx.fragment.app.Fragment(), CallBackDisplay, CallBackOnClickCard {


    var recView_Shop: androidx.recyclerview.widget.RecyclerView? = null;
    var anotherView: View? = null
    var tCredits: TextView? = null
    var tCards: TextView? = null
    var _idUser = "23"
    var _cost = 0
    var _idCard = "N/A"
    var userCards_total = 0
    var userMoney_total = 0
    var flagUpdateListofItems = true
    private var acc: Account = Account()

    override fun onClickCard(idCard: String, cost: Int) {
        Log.d("Chaton", idCard.toString())
        _cost = cost
        _idCard = idCard
        if (_idCard != null) {
            if (this.userMoney_total < _cost) {
                Toast.makeText(context, "Attention: Your Credit is insufficient! The operation canceled", Toast.LENGTH_LONG).show()
                return
            } else if (this.userMoney_total == _cost) {
                Toast.makeText(context, "Attention: Your will use all your Credit! ", Toast.LENGTH_LONG).show()
            }
            this.userCards_total++
            setTextCards(this.userCards_total.toString())
            CallBackGenerator(callback = this, action = "SetOneCard", isActivateCallBack = true, idUser = _idUser, idCard = idCard, url = "https://api.coopuniverse.fr/").execute()
        }
    }

    override fun display(rep: Reponse, action: String) {

        var data: ArrayList<Card>? = null
        var money: String? = null
        var user: Int? = null
        var question: ArrayList<Any>? = null
        var userCards: ArrayList<String>? = null
        if (rep != null && rep.data != null) {
            data = rep.data.cards
            money = rep.data.money
            user = rep.data.user
            question = rep.data.question
            userCards = rep.data.inventory
        }

        when (action) {
            "GetAllCard" -> {
                var adapterReclViewcard: CardsListAdapterStore = CardsListAdapterStore(data, targetFragment, this)
                recView_Inventory?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
                recView_Inventory?.adapter = adapterReclViewcard
            }
            "GetOneMoney" -> {
                if (money != null) {
                    var _money = money.toString()
                    if (_money.toIntOrNull() != null) {
                        this.userMoney_total = _money.toIntOrNull()!!
                    }
                    setTextCredits(money.toString())
                }
                CallBackGenerator(callback = this, action = "GetCardByUserId", isActivateCallBack = true, idUser = this.acc.id, url = "https://api.coopuniverse.fr/").execute()
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
                    CallBackGenerator(callback = this, action = "GetAllCard", isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()
                }
            }
            "SetOneMoney" -> {
                if (_cost != null) {
                    flagUpdateListofItems = false
                }
            }
            "SetOneCard" -> {
                this.userMoney_total = this.userMoney_total.minus(_cost)
                setTextCredits(this.userMoney_total.toString())
                CallBackGenerator(callback = this, action = "SetOneMoney", isActivateCallBack = true, idUser = this.acc.id, value = userMoney_total.toString(), url = "https://api.coopuniverse.fr/").execute()
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        tCredits = inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCredits);
        tCards = inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCards)
        this.acc.id = this.getArguments()?.getString(this.activity?.getString(R.string.idUser))!!
        this.acc.money = this.getArguments()?.getString(this.activity?.getString(R.string.idMoney))
        getUserData()
        return inflater.inflate(fr.coopuniverse.api.pokeapi.R.layout.inventory_fragment, container, false)
    }

    fun getUserData() {
        CallBackGenerator(callback = this, action = "GetOneMoney", isActivateCallBack = true, idUser = this.acc.id, url = "https://api.coopuniverse.fr/").execute()
    }

    fun setTextCredits(text: String) {
        val textView = view!!.findViewById(fr.coopuniverse.api.pokeapi.R.id.tCredits) as TextView
        var txt = tCredits!!.text.toString()
        var txtCredit = " credits "
        textView.text = txt + " " + text + txtCredit

    }

    fun setTextCards(text: String) {
        val textView = view!!.findViewById(fr.coopuniverse.api.pokeapi.R.id.tCards) as TextView
        var txt = tCards!!.text.toString()
        var txtCards = " cards "
        textView.text = txt + " " + text + txtCards
    }


    companion object {
        fun newInstance() = ShopFragment()
    }


}
