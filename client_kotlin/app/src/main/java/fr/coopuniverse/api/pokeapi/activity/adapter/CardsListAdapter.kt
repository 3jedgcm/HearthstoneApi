package fr.coopuniverse.api.pokeapi.activity.adapter


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Card
import kotlinx.android.synthetic.main.custom_card_view.view.*



import java.text.SimpleDateFormat

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder


class CardsListAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<CardsListAdapter.ViewHolder>{



    private var cardsObjectsList: ArrayList<Card>? = null
    private var fragment: androidx.fragment.app.Fragment? = null

    private var context: Context? = null
    private var mListener: CallBackOnClickCard? = null

    private var currentViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder? = null


    constructor(comicsObjectsList: ArrayList<Card>, fragment: androidx.fragment.app.Fragment) {
        this.cardsObjectsList = comicsObjectsList
        this.fragment = fragment

    }

    constructor(cardsObjectsList: ArrayList<Card>, fragment: androidx.fragment.app.Fragment?, listenerOfAdapter: CallBackOnClickCard?) {
        this.cardsObjectsList = cardsObjectsList;
        this.fragment = fragment
        this.mListener = listenerOfAdapter

    }

    constructor(context: Context, cardsObjectsList: ArrayList<Card>, cardsFragment: androidx.fragment.app.Fragment?, mListener: CallBackOnClickCard?) {

        this.context = context
        this.cardsObjectsList = cardsObjectsList
        this.fragment = fragment
        this.mListener = mListener


    }







    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardsListAdapter.ViewHolder {

        this.context= viewGroup.context;

        val itemLayoutView = LayoutInflater.from(viewGroup.context).inflate(R.layout.custom_card_view, viewGroup, false)

        this.currentViewHolder = ViewHolder(itemLayoutView).bind(this.mListener)

        return ViewHolder(itemLayoutView)!!

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, p1: Int) {


        val cardsItem = cardsObjectsList!![viewHolder.getAdapterPosition()]


        Glide.with(this.context!!)
                .load(cardsObjectsList!![p1].getImage())
                .into(viewHolder.iCardImage)

        val itemLayoutView = viewHolder.itemView


        val lastObject = cardsObjectsList!!.size - 1

        if (p1 == lastObject) {
            Log.d("debug:", "lastObject=  $p1")

        }


    }






    override fun getItemCount(): Int {
        return if (cardsObjectsList != null) {
            cardsObjectsList!!.size
        } else
            0
    }

    inner class ViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {


        val iCardImage: ImageView = mView.iCard // findViewById(R.id.iCard);

        fun bind(clickListener: CallBackOnClickCard? ): ViewHolder {

            itemView.setOnClickListener {clickListener?.onClickCard(Card())}
            return this
        }

        override fun toString(): String {

            return super.toString()
        }

    }


}
