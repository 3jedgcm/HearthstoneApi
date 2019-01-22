package fr.coopuniverse.api.pokeapi.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapter
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Card
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Reponse

class InventoryFragment : Fragment(), CallBackDisplay  {

    var anotherView: View? = null;

    override fun display(rep: Reponse, action: String) {
        var cards = rep.data.cards
        Log.d("Chaton",cards.toString())
        val  recView_Inventory:RecyclerView = anotherView!!.findViewById(R.id.recView_Inventory)
        var adapterReclView: CardsListAdapter = CardsListAdapter(cards, targetFragment, listener)
        recView_Inventory.layoutManager = GridLayoutManager(this.context,3)
        recView_Inventory.adapter=  adapterReclView
    }


    private var listener:CallBackOnClickCard?= null


    companion object {
        fun newInstance() = InventoryFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

       anotherView =  inflater.inflate(R.layout.inventory_fragment, container, false)
        CallBackGenerator(callback = this,action = "GetAllCard",isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()

        return anotherView

    }
}
