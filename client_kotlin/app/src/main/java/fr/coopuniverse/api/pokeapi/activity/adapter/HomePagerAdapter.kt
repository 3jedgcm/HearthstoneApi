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


class HomePagerAdapter(private val mContext: Context?, fm: FragmentManager, var acc: Account): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fr: Fragment = if (position == 0) {
            QuizzFragment()
        } else if (position == 1) {
            QuizzFragment()
        } else{
            QuizzFragment()
        }
        var b = Bundle()
        b?.putString(mContext?.getString(R.string.idUser),this.acc.id)
        b?.putString(mContext?.getString(R.string.idMoney),this.acc.money)
        fr.arguments = b
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