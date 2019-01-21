package fr.coopuniverse.api.pokeapi.activity.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Card

class CardDetailFragment : Fragment() {

    var card: Card? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.card_detail_fragment, container, false)
    }


    fun updateCard(card: Card)
    {
        this.card = card;
    }


}
