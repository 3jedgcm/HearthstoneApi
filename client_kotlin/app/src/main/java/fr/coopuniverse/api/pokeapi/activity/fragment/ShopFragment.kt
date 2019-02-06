package fr.coopuniverse.api.pokeapi.activity.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapterStore
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import kotlinx.android.synthetic.main.inventory_fragment.*

class ShopFragment  : androidx.fragment.app.Fragment(), CallBackDisplay, CallBackOnClickCard {


    var recView_Shop: androidx.recyclerview.widget.RecyclerView? = null;
    var anotherView: View? = null

    override fun onClickCard(str: String) {
        Log.d("Chaton",str.toString())
    }

    override fun display(rep: Reponse, action: String) {
        var cards = rep.data.cards
        var adapterReclViewcard: CardsListAdapterStore = CardsListAdapterStore(cards, targetFragment, this)
        recView_Inventory?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)//GridLayoutManager(this.context, 1) //androidx.recyclerview.widget.LinearLayoutManager(this.context)
        recView_Inventory?.adapter=  adapterReclViewcard

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        anotherView = inflater.inflate(R.layout.inventory_fragment, container, false)
        CallBackGenerator(callback = this,action = "GetAllCard",isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()
        return inflater.inflate(R.layout.inventory_fragment, container, false)
    }

}
