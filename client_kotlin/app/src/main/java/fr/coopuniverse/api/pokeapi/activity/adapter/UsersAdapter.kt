package fr.coopuniverse.api.pokeapi.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.data.User


class UsersAdapter(private val context: Context?,
                   private val dataSource: ArrayList<User>) : BaseAdapter() {

    private val inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    companion object {
        private val LABEL_COLORS = hashMapOf(
                "Orange" to R.color.colorButton,
                "Black" to R.color.colorText,
                "Blue-Dark-Dark" to R.color.colorAccent,
                "Blue-Dark" to R.color.colorTitleText,
                "Gray" to R.color.abc_secondary_text_material_light,
                "Vegetarian" to R.color.abc_btn_colored_borderless_text_material
        )


    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Get view for row item
        val view: View
        val holder: ViewHolder

        if (convertView == null) {

            view = inflater.inflate(R.layout.spinner_dropdown_item, parent, false)

            holder = ViewHolder()

            holder.nameTextView = view.findViewById(R.id.textViewName) as TextView
            view.tag = holder

        } else {

            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val nameTxtView = holder.nameTextView
        val user: User = getItem(position) as User
        if(user.Login == "")
        {
            nameTxtView.text = "Id User :" + user.IdUser
        }
        else
        {
            nameTxtView.text = "Id User :" + user.IdUser + "      Login :" + user.Login
        }



        val idTypeFace = ResourcesCompat.getFont(context!!, R.font.roboto_slab_bold)


        nameTxtView.setTextColor(
                ContextCompat.getColor(context, LABEL_COLORS[user.Login]
                        ?: R.color.abc_btn_colored_borderless_text_material))



        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    private class ViewHolder {

        lateinit var nameTextView: TextView


    }
}