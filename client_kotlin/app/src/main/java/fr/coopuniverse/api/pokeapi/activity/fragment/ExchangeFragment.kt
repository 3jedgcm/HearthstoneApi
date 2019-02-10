package fr.coopuniverse.api.pokeapi.activity.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackFragment


class ExchangeFragment : androidx.fragment.app.Fragment() {
    var callback: CallBackFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.exchange_fragment, container, false)
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
