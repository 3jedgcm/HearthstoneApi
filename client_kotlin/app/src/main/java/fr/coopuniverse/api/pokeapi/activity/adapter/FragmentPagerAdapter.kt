import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.fragment.CraftFragment
import fr.coopuniverse.api.pokeapi.activity.fragment.MeltFragment
import fr.coopuniverse.api.pokeapi.activity.fragment.QuizzFragment


class SimpleFragmentPagerAdapter(private val mContext: Context?, fm: FragmentManager,var acc: Account): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fr: Fragment = if (position == 0) {
            QuizzFragment()
        } else if (position == 1) {
            MeltFragment()
        } else{
            CraftFragment()
        }
        var b = Bundle()
        b?.putString("id",this.acc.id)
        b?.putString("money",this.acc.money)
        fr.arguments = b
        return fr
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {

        when (position) {
            0 -> return mContext?.getString(R.string.button_quizz)
            1 -> return mContext?.getString(R.string.button_melt)
            2 -> return mContext?.getString(R.string.button_craft)
            else -> return null
        }
    }

}