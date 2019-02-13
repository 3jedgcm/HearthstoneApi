import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.view.fragment.CraftFragment
import fr.coopuniverse.api.pokeapi.activity.view.fragment.MeltFragment
import fr.coopuniverse.api.pokeapi.activity.view.fragment.QuizzFragment


class HomePagerAdapter(private val mContext: Context?, fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fr: Fragment = if (position == 0) {
            CraftFragment()
        } else if (position == 1) {
            MeltFragment()
        } else{
            QuizzFragment()
        }
        return fr
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> mContext?.getString(R.string.button_craft)
            1 -> mContext?.getString(R.string.button_melt)
            2 -> mContext?.getString(R.string.button_quizz)
            else -> null
        }
    }

}