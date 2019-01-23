package fr.coopuniverse.api.pokeapi.activity.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackFragment
import fr.coopuniverse.api.pokeapi.activity.activity.Destination
import kotlinx.android.synthetic.main.market_fragment.*


class MarketFragment : androidx.fragment.app.Fragment() {

    var callback: CallBackFragment? = null

    companion object {
        fun newInstance() = MarketFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopButton.setOnClickListener {
            callback?.setFragment(Destination.Shop)
        }
        exchangeButton.setOnClickListener {
            callback?.setFragment(Destination.Exchange  )
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.market_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is CallBackFragment) {
            callback = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }
}
