package fr.coopuniverse.api.pokeapi.activity.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapter
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Response.Response
import fr.coopuniverse.api.pokeapi.activity.data.Response.ResponseSimple
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager


class InventoryFragment : androidx.fragment.app.Fragment(), CallBackOnClickCard {

    var recView_Inventory: androidx.recyclerview.widget.RecyclerView? = null;
    private var anotherView: View? = null
    private var cards: ArrayList<Card> = ArrayList()
    override fun onClickCard(cardId: String,cost:Int ,costStr:String) {
        var fragment = CardDetailFragment()
        for (c: Card in cards)
        {
            if (c.id.equals(cardId))
           {
            fragment.card = c
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.contentHome,fragment)?.commit()
           }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       this.anotherView =  inflater.inflate(R.layout.inventory_fragment, container, false)
        var linearL: View = anotherView!!.findViewById(R.id.layoutText)
        linearL.visibility =  View.GONE


        var call = CallHttpManager( action = Route.GET_CARD_BY_USER_ID.get, idUser = Account.id, url = this.activity?.getString(R.string.url))
        var currentAction = ""
        call.currentAction.observe(this, Observer {currentAction = it})
        call.liveResponse.observe(this, Observer {
            when(currentAction)
            {
                Route.GET_CARD_BY_USER_ID.get-> {
                    var rs = it as ResponseSimple
                    cards = rs.cards as ArrayList<Card>
                    this.recView_Inventory = anotherView!!.findViewById(R.id.recView_Inventory)
                    var adapterReclView = CardsListAdapter(cards, targetFragment, this)
                    recView_Inventory?.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this.context, 3)
                    recView_Inventory?.adapter=  adapterReclView
                }
            }
        })
        call.execute()




       return this.anotherView
    }
}
