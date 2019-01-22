package fr.coopuniverse.api.pokeapi.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapter
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Card

class InventoryFragment : Fragment() {


    private var listener:CallBackOnClickCard?= null


    companion object {
        fun newInstance() = InventoryFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

       val view =  inflater.inflate(R.layout.inventory_fragment, container, false)


       // Set the adapter


        val  recView_Inventory:RecyclerView = view.findViewById(R.id.recView_Inventory)
        var listCards : ArrayList<Card> = ArrayList()
        listCards= initList()

        var adapterReclView: CardsListAdapter = CardsListAdapter(listCards, targetFragment, listener)

        recView_Inventory.layoutManager = GridLayoutManager(this.context,3)
        recView_Inventory.adapter=  adapterReclView

        return view

    }


    fun initList(): ArrayList<Card> {

        val list: ArrayList<Card> = ArrayList()

        val iterator = (1..3).iterator()
       // skip an element
        if (iterator.hasNext()) {
            iterator.next()

        }

     // do something with the rest of elements
    /*    iterator.forEach {
            var card : Card = Card()
            card.attack=1
            card.name="CardPI"
            card.id= "EX1_349"
            card.getImage()
            list.add(card)

            println("The element is $it")
        }
*/
        var card : Card = Card()
        card.attack=1
        card.name="CardP1"
        card.id= "EX1_349"
        card.getImage()
        list.add(card)

        var card1 : Card = Card()
        card1.attack=2
        card1.name="CardP2"
        card1.id= "CS2_038"
        card1.getImage()
        list.add(card)


        //println(list)
        return list
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

}
