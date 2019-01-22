package fr.coopuniverse.api.pokeapi.activity.adapter


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
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
import kotlinx.android.synthetic.main.fragment_item.view.*


import java.text.SimpleDateFormat





class CardsListAdapter : RecyclerView.Adapter<CardsListAdapter.ViewHolder>{



    private var cardsObjectsList: ArrayList<Card>? = null
    private var fragment: Fragment? = null

    private var context: Context? = null
    private var mListener: CallBackOnClickCard? = null
    // private ComicsFragmentPresenter mPresenter;
    private var currentViewHolder: RecyclerView.ViewHolder? = null


    constructor(comicsObjectsList: ArrayList<Card>, fragment: Fragment) {
        this.cardsObjectsList = comicsObjectsList
        this.fragment = fragment

    }

    constructor(cardsObjectsList: ArrayList<Card>, fragment: Fragment?, listenerOfAdapter: CallBackOnClickCard?) {
        this.cardsObjectsList = cardsObjectsList;
        this.fragment = fragment
        this.mListener = listenerOfAdapter

    }

    constructor(context: Context, cardsObjectsList: ArrayList<Card>, cardsFragment: Fragment?, mListener: CallBackOnClickCard?) {

        this.context = context
        this.cardsObjectsList = cardsObjectsList
        this.fragment = fragment
        this.mListener = mListener


    }





    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardsListAdapter.ViewHolder {
        //Context context = viewGroup.getContext();
        // LayoutInflater inflater = LayoutInflater.from(context);

        this.context= viewGroup.context;

        val itemLayoutView = LayoutInflater.from(viewGroup.context).inflate(R.layout.custom_card_view, viewGroup, false)

        this.currentViewHolder = ViewHolder(itemLayoutView)

        return ViewHolder(itemLayoutView)!!

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, p1: Int) {


        val cardsItem = cardsObjectsList!![viewHolder.getAdapterPosition()]

        /* val tvTitle = viewHolder.tvTitle
         val tvDate = viewHolder.tvDate
         val tvPageCount = viewHolder.tvPageCount
         val ivComics = viewHolder.ivComics


         tvTitle.append(cardsObjectsList!![i].getTitle())
         tvDate.append(date)
         tvPageCount.append(Integer.toString(cardsObjectsList!![i].getPageCount()))
       */
        Glide.with(this.context!!)
                .load(cardsObjectsList!![p1].getImage())
                .into(viewHolder.iCardImage)

        val itemLayoutView = viewHolder.itemView


        val lastObject = cardsObjectsList!!.size - 1

        if (p1 == lastObject) {
            Log.d("debug:", "lastObject=  $p1")
         //   viewHolder.init(itemLayoutView)
            // mListener.finishedInit(true);
        }


    }






    override fun getItemCount(): Int {
        return if (cardsObjectsList != null) {
            cardsObjectsList!!.size
        } else
            0
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {


        val iCardImage: ImageView = mView.iCard // findViewById(R.id.iCard);


        override fun toString(): String {

            return super.toString()
        }

    }

  /*  inner class ViewHolder(itemView: View) : BaseViewHolder(itemView), ComicsView//RecyclerView.ViewHolder
    {

        var tvTitle: TextView
        var tvDate: TextView
        var tvPageCount: TextView
        var ivComics: ImageView


        init {


            this.setLayoutId(R.layout.custom_comics_view)
            // this.setPresenter(mPresenter);

            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvPageCount = itemView.findViewById(R.id.tvPageCount)
            ivComics = itemView.findViewById(R.id.ivComics)

            ivComics.setOnClickListener {
                val position = getAdapterPosition()
                val comicsItem = cardsObjectsList!![getAdapterPosition()]
                //notificaton send to Fragment via this listener
                mListener!!.onClickCard(comicsItem)
            }


        }
    }
    */



}
