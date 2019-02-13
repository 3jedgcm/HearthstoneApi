import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.view.fragment.ShopFragment


class MarketPagerAdapter(private val mContext: Context?, fm: FragmentManager, var acc: Account): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fr = ShopFragment()
        /*
        var fr: Fragment = if (position == 0) {
            ShopFragment()
        } else if (position == 1) {
            ShopFragment()
        } else{
            ShopFragment()
        }
        */
        var b = Bundle()
        b?.putString(mContext?.getString(R.string.idUser),this.acc.id)
        b?.putString(mContext?.getString(R.string.idMoney),this.acc.money)
        fr.arguments = b
        return fr
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> mContext?.getString(R.string.button_market)
            1 -> mContext?.getString(R.string.button_exchange)
            2 -> mContext?.getString(R.string.button_market)
            else -> null
        }
    }

}