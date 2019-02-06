package fr.coopuniverse.api.pokeapi.activity.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Card
import kotlinx.android.synthetic.main.custom_card_view.view.*


class CardsListAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<CardsListAdapter.ViewHolder>{

    private var cardsObjectsList: ArrayList<Card>? = null
    private var fragment: androidx.fragment.app.Fragment? = null
    private var context: Context? = null
    private var mListener: CallBackOnClickCard? = null
    private var currentViewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder? = null
    constructor(cardsObjectsList: ArrayList<Card>, fragment: androidx.fragment.app.Fragment?, listenerOfAdapter: CallBackOnClickCard?) {
        this.cardsObjectsList = cardsObjectsList
        this.fragment = fragment
        this.mListener = listenerOfAdapter
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardsListAdapter.ViewHolder {
        this.context= viewGroup.context;
        var itemLayoutView = LayoutInflater.from(viewGroup.context).inflate(R.layout.custom_card_view, viewGroup, false)
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
            itemView.setOnClickListener {
                clickListener?.onClickCard(iCardImage.contentDescription.toString())
            }
            return this
        }
        override fun toString(): String {
            return super.toString()
        }
    }
}
