package fr.coopuniverse.api.pokeapi.activity.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.data.Card
import kotlinx.android.synthetic.main.card_detail_fragment.*

class CardDetailFragment : androidx.fragment.app.Fragment() {

    var card: Card? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.card_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(this.context!!).load(this.card!!.getImage()).into(cardImage)
        cardType.text = R.string.types.toString() + this.card?.type
        rarity.text =  R.string.rarity.toString() + this.card?.rarity
        classCard.text =  R.string.classCard.toString() + this.card?.cardClass
        idCard.text =  R.string.id.toString() + this.card?.id
        super.onViewCreated(view, savedInstanceState)
    }
}
