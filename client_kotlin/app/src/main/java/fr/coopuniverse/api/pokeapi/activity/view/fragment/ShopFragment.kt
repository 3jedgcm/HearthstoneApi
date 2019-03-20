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
import kotlinx.android.synthetic.main.shop_fragment.*


class ShopFragment : androidx.fragment.app.Fragment() {
    private var userCards_total = 0
    private var userMoney_total = 0
    private var data: ArrayList<Card>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(fr.coopuniverse.api.pokeapi.R.layout.inventory_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Account.money != null) {
            userMoney_total = Account.money.toInt()
        } else {
            userMoney_total = 0
        }
        ShopViewModel.initDataUser()
        ShopViewModel.nbCardsUser.observe(this, Observer {
            userCards_total = it
            setTextCards(userCards_total.toString())
        })

        ShopViewModel.dataAllCards.observe(this, Observer {
            data = it
            val adapterReclViewcard = CardsListAdapterStore(data, targetFragment, ShopViewModel)
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
    }

    fun setTextCredits(credits: String) {
        tCredits!!.text = this.context!!.resources.getText(R.string.label_StateAccount).toString() + " " + credits + " " + this.context!!.resources.getText(R.string.idMoney).toString()
    }

    fun setTextCards(cards: String) {
        tCards!!.text = this.context!!.resources.getText(R.string.label_StateAccount).toString()  + " " + cards + " " + this.context!!.resources.getText(R.string.content_description).toString()
    }

    override fun onResume() {
        super.onResume()

        ShopViewModel.initDataUser()
    }



}
