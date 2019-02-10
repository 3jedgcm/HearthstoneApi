package fr.coopuniverse.api.pokeapi.activity.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Card
import kotlinx.android.synthetic.main.custom_card_view_shop.view.*


class CardsListAdapterStore : androidx.recyclerview.widget.RecyclerView.Adapter<CardsListAdapterStore.ViewHolder> {

    private var cardsObjectsList: ArrayList<Card>? = null
    private var fragment: androidx.fragment.app.Fragment? = null
    private var context: Context? = null
    private var mListener: CallBackOnClickCard? = null
    private var currentViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder? = null

    constructor(cardsObjectsList: ArrayList<Card>?, fragment: androidx.fragment.app.Fragment?, listenerOfAdapter: CallBackOnClickCard?) {
        this.cardsObjectsList = cardsObjectsList;
        this.fragment = fragment
        this.mListener = listenerOfAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context;
        var itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.custom_card_view_shop, parent, false)
        this.currentViewHolder = ViewHolder(itemLayoutView).bind(this.mListener)
        return ViewHolder(itemLayoutView)!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, p1: Int) {
        Glide.with(viewHolder.iCardImage)
                .load(cardsObjectsList!![p1].getImage())
                .thumbnail(Glide
                        .with(viewHolder.iCardImage)
                        .load(R.drawable.card_default))
                .into(viewHolder.iCardImage)
        viewHolder.iCardImage.contentDescription = cardsObjectsList!![p1].id
        viewHolder.bBuy.contentDescription = cardsObjectsList!![p1].getMoneyByTypeCard().toString() //cardsObjectsList!![p1].id
        var txtCost = cardsObjectsList!![p1].getMoneyByTypeCard().toString()// cardsObjectsList!![p1].cost.toString()
        viewHolder.tPrice.text = this.context!!.getString(R.string.label_price) + txtCost;
    }

    override fun getItemCount(): Int {
        return if (cardsObjectsList != null) {
            cardsObjectsList!!.size
        } else
            0
    }

    inner class ViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {
        val tPrice: TextView = mView.findViewById(R.id.tPrice)
        val iCardImage: ImageView = mView.iCardStore
        val bBuy: Button = mView.bBuy
        fun bind(clickListener: CallBackOnClickCard?): ViewHolder {
            this.bBuy.setOnClickListener { clickListener?.onClickCard(this.iCardImage.contentDescription.toString(), this.bBuy.contentDescription.toString().toInt(), this.bBuy.contentDescription.toString()) }
            return this
        }
        override fun toString(): String {

            return super.toString()
        }

    }
}
