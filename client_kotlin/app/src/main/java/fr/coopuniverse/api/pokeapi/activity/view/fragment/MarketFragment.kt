package fr.coopuniverse.api.pokeapi.activity.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import MarketPagerAdapter
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.data.Account
import kotlinx.android.synthetic.main.home_fragment.*

class MarketFragment : androidx.fragment.app.Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MarketPagerAdapter(context, childFragmentManager, Account(id = this.arguments?.getString("id")!!, money = this.arguments?.getString("money"))!!)
        viewer.adapter = adapter
        tab_navigation.setupWithViewPager(viewer)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }
}
