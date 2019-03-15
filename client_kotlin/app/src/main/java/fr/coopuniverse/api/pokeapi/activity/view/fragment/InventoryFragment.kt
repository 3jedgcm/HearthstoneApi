package fr.coopuniverse.api.pokeapi.activity.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapter
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.view.viewModel.InventoryViewModel


class InventoryFragment : androidx.fragment.app.Fragment() {

    var recView_Inventory: androidx.recyclerview.widget.RecyclerView? = null
    private var anotherView: View? = null
    private var cards: ArrayList<Card> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.anotherView = inflater.inflate(R.layout.inventory_fragment, container, false)
        val linearL: View = anotherView!!.findViewById(R.id.layoutText)
        linearL.visibility = View.GONE

        InventoryViewModel.initData()


        /// Observation ///
        InventoryViewModel.idCardClicked.observe(this, Observer {

            var cardId = it
            var fragment = CardDetailFragment()

            for (c: Card in cards) {
                if (c.id.equals(cardId)) {
                    fragment.card = c
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.contentHome, fragment)?.commit()
                }
            }
        })

        InventoryViewModel.costCardClicked.observe(this, Observer {
            // Value money of card
        })

        InventoryViewModel.cardsUserInventory.observe(this, Observer {

            cards = it
            this.recView_Inventory = anotherView!!.findViewById(R.id.recView_Inventory)
            var adapterReclView = CardsListAdapter(cards, targetFragment, InventoryViewModel)
            recView_Inventory?.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this.context, 3)
            recView_Inventory?.adapter = adapterReclView
        })

        return this.anotherView
    }
}