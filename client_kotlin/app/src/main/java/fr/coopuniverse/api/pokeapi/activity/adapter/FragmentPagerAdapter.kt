

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.fragment.CraftFragment
import fr.coopuniverse.api.pokeapi.activity.fragment.MeltFragment
import fr.coopuniverse.api.pokeapi.activity.fragment.QuizzFragment


class SimpleFragmentPagerAdapter(private val mContext: Context?, fm: FragmentManager,var id: String): FragmentPagerAdapter(fm) {

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        var fr: Fragment = if (position == 0) {
            CraftFragment()
        } else if (position == 1) {
            MeltFragment()
        } else{
            QuizzFragment()
        }
        var b: Bundle = Bundle()
        b?.putString("id",this.id)
        fr.arguments = b;
        return fr;
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return 3
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        when (position) {
            0 -> return mContext?.getString(R.string.button_craft)
            1 -> return mContext?.getString(R.string.button_melt)
            2 -> return mContext?.getString(R.string.button_quizz)
            else -> return null
        }
    }

}