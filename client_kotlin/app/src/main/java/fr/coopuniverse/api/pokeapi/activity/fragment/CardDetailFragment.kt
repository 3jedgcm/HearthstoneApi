package fr.coopuniverse.api.pokeapi.activity.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Card
import kotlinx.android.synthetic.main.card_detail_fragment.*

class CardDetailFragment : androidx.fragment.app.Fragment() {

    var card: Card? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {




        return inflater.inflate(R.layout.card_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(this.context!!).load(this.card!!.getImage()).into(cardImage)
        cardType.text = "Type: " + this.card?.type
        rarity.text =  "Rarity: " + this.card?.rarity
        classCard.text =  "Class: " + this.card?.cardClass
        idCard.text =  "Id: " + this.card?.id
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateCard(card: Card)
    {
        this.card = card;
    }


}
