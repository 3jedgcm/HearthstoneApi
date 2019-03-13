package fr.coopuniverse.api.pokeapi.activity.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapterStore
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import kotlinx.android.synthetic.main.inventory_fragment.*
import fr.coopuniverse.api.pokeapi.activity.view.viewModel.ShopViewModel


class ShopFragment : androidx.fragment.app.Fragment() {

    private var tCredits: TextView? = null
    private var tCards: TextView? = null
    private var cost = 0
    private var idCard = "N/A"
    private var userCards_total = 0
    private var userMoney_total = 0
    private var flagUpdateListofItems = true

    var data: ArrayList<Card>? = null
    var infoCreditView: TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.tCredits = inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCredits)
        this.tCards = inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCards)
        //this.getUserData()

        val view = inflater.inflate(fr.coopuniverse.api.pokeapi.R.layout.inventory_fragment, container, false)
        infoCreditView = view!!.findViewById(fr.coopuniverse.api.pokeapi.R.id.tCredits) as TextView

        if (Account.money != null) {
            userMoney_total = Account.money.toInt()
        } else {
            userMoney_total = 0
        }
        //  setTextCredits(userMoney_total.toString())

        ShopViewModel.initDataUser()


        ////Observe////

        ShopViewModel.nbCardsUser.observe(this, Observer {

            userCards_total = it
            setTextCards(userCards_total.toString())
        })

        ShopViewModel.dataAllCards.observe(this, Observer {
            data = it
            val adapterReclViewcard = CardsListAdapterStore(data, targetFragment, ShopViewModel) //ShopViewModel extends mon listener donc je peux l'en mettre à la place de  listener
            recView_Inventory?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
            recView_Inventory?.adapter = adapterReclViewcard

        })

        ShopViewModel.dataMoneyUser.observe(this, Observer {
            userMoney_total = it
            setTextCredits(it.toString())

        })
        ShopViewModel.comunicate.observe(this, Observer {

            Toast.makeText(context, this.activity?.getString(it), Toast.LENGTH_LONG).show()

        })

        ShopViewModel.userCards_total_Mutable.observe(this, Observer {
            userCards_total = it
            setTextCards(it.toString())


        })
        ShopViewModel.userMoney_total_Mutable.observe(this, Observer {
            userMoney_total = it
            setTextCredits(it.toString())

        })


        return inflater.inflate(fr.coopuniverse.api.pokeapi.R.layout.inventory_fragment, container, false)
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
