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
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Card
import android.widget.Button
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.custom_card_view_shop.view.*


class CardsListAdapterStore : androidx.recyclerview.widget.RecyclerView.Adapter<CardsListAdapterStore.ViewHolder> {


    private var cardsObjectsList: ArrayList<Card>? = null
    private var fragment: androidx.fragment.app.Fragment? = null

    private var context: Context? = null
    private var mListener: CallBackOnClickCard? = null

    private var currentViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder? = null

    private var costCard: String? = null

    constructor(comicsObjectsList: ArrayList<Card>, fragment: androidx.fragment.app.Fragment) {
        this.cardsObjectsList = comicsObjectsList
        this.fragment = fragment

    }

    constructor(cardsObjectsList: ArrayList<Card>, fragment: androidx.fragment.app.Fragment?, listenerOfAdapter: CallBackOnClickCard?) {
        this.cardsObjectsList = cardsObjectsList;
        this.fragment = fragment
        this.mListener = listenerOfAdapter
        Log.d("chat", "Shop5: " + fragment.toString())

    }

    constructor(context: Context, cardsObjectsList: ArrayList<Card>, cardsFragment: androidx.fragment.app.Fragment?, mListener: CallBackOnClickCard?) {

        this.context = context
        this.cardsObjectsList = cardsObjectsList
        this.fragment = fragment
        this.mListener = mListener


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        this.context = parent.context;

        Log.d("chat", "Shop6: " + this.context)
        var itemLayoutView: View;


        Log.d("chat", "Shop2: ")
        itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.custom_card_view_shop, parent, false)



        this.currentViewHolder = ViewHolder(itemLayoutView).bind(this.mListener)

        return ViewHolder(itemLayoutView)!!


    }


    override fun onBindViewHolder(viewHolder: ViewHolder, p1: Int) {

        Log.d("chat", "Shop2.1: ")


        val cardsItem = cardsObjectsList!![viewHolder.getAdapterPosition()]


        Glide.with(viewHolder.iCardImage)
                .load(cardsObjectsList!![p1].getImage())
                .thumbnail(Glide
                        .with(viewHolder.iCardImage)
                        .load(R.drawable.card_default))
                .transition(withCrossFade())
                .into(viewHolder.iCardImage)


        viewHolder.iCardImage.contentDescription = cardsObjectsList!![p1].id
        viewHolder.bBuy.contentDescription = cardsObjectsList!![p1].id
        costCard = cardsObjectsList!![p1].cost.toString();

        /*  Glide.with(this.context!!)
                  .load(cardsObjectsList!![p1].getImage())
                  .transition(withCrossFade())
                  .into(viewHolder.iCardImage)
             */
        val itemLayoutView = viewHolder.itemView


        var txtCost: String = ""
        txtCost = cardsObjectsList!![p1].cost.toString()


        viewHolder.tPrice.text = this.context!!.getString(R.string.label_price) + txtCost;


        val lastObject = cardsObjectsList!!.size - 1

        if (p1 == lastObject) {
            Log.d("chat:", "lastObject=  $p1")

        }


    }


    override fun getItemCount(): Int {
        return if (cardsObjectsList != null) {
            cardsObjectsList!!.size
        } else
            0
    }

    inner class ViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {


        val tPrice: TextView = mView.findViewById(R.id.tPrice);
        val bBuy: Button = mView.findViewById(R.id.bBuy);


        val iCardImage: ImageView = mView.iCardStore // findViewById(R.id.iCard);


        fun bind(clickListener: CallBackOnClickCard?): ViewHolder {

            // itemView.setOnClickListener {clickListener?.onClickCard(Card())}

            bBuy.setOnClickListener { clickListener?.onClickCard(iCardImage.contentDescription.toString(), costCard.toString()) }


            Log.d("chat", "Shop4: ")
            return this
        }

        override fun toString(): String {

            return super.toString()
        }

    }


}
