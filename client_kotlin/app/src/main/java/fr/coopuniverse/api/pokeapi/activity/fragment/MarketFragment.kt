package fr.coopuniverse.api.pokeapi.activity.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import MarketPagerAdapter
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackFragment
import fr.coopuniverse.api.pokeapi.activity.activity.Destination
import fr.coopuniverse.api.pokeapi.activity.data.Account
import kotlinx.android.synthetic.main.home_fragment.*

class MarketFragment : androidx.fragment.app.Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MarketPagerAdapter(context, activity?.supportFragmentManager!!, Account(id = this.getArguments()?.getString("id")!!, money = this.getArguments()?.getString("money"))!!)
        viewer.adapter = adapter
        tab_navigation.setupWithViewPager(viewer)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }
}
