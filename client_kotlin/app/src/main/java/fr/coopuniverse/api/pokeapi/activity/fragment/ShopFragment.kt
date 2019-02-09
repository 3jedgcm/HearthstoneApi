package fr.coopuniverse.api.pokeapi.activity.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapterStore
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import kotlinx.android.synthetic.main.inventory_fragment.*


class ShopFragment : androidx.fragment.app.Fragment(), CallBackDisplay, CallBackOnClickCard {


    var recView_Shop: androidx.recyclerview.widget.RecyclerView? = null;
    var anotherView: View? = null
    var tCredits: TextView? = null  //credit/money par user
    var tCards: TextView? = null //nb cards par user
    var _idUser = "23"
    var _cost = "0"
    var _idCard = "N/A"


    var userCards_total = 0
    var userMoney_total = 0


    override fun onClickCard(idCard: String, cost: String) {
        Log.d("Chaton", idCard.toString())
        _cost = cost;
        _idCard = idCard;

        //  CallBackGenerator(callback = this,action = "SetOneMoney",isActivateCallBack = true,idUser=_idUser ,value=cost, url = "https://api.coopuniverse.fr/").execute()
        CallBackGenerator(callback = this, action = "SetOneCard", isActivateCallBack = true, idUser = _idUser, idCard = idCard, url = "https://api.coopuniverse.fr/").execute()

    }


    override fun display(rep: Reponse, action: String) {

        if (rep == null || rep.data == null) {
            return
        }
        var data = rep.data.cards
        var money = rep.data.money;//.toString();
        var user = rep.data.user;//.toString();
        var question = rep.data.question;
        var userCards = rep.data.cards;


//        Log.d("chat","Shop1: " +data.toString() )


        when (action) {
            "GetAllCard" -> {
                var adapterReclViewcard: CardsListAdapterStore = CardsListAdapterStore(data, targetFragment, this)

                recView_Inventory?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context) as RecyclerView.LayoutManager?//GridLayoutManager(this.context, 1) //androidx.recyclerview.widget.LinearLayoutManager(this.context)
                recView_Inventory?.adapter = adapterReclViewcard

            }        //-> rep = service.GetAllCard()

            "GetOneMoney" -> {
                if (money != null) {

                    var _money = money.toString()
                    if (_money.toIntOrNull() != null) {
                        this.userMoney_total = _money.toIntOrNull()!!;
                    }

                    setTextCredits(money.toString());
                }
                CallBackGenerator(callback = this, action = "GetCardByUserId", isActivateCallBack = true, idUser = "23", url = "https://api.coopuniverse.fr/").execute()

            }       //-> rep = service.GetOneMoney(idUser!!) //alimenter le popup
            "GetCardByUserId" -> {
                if (data != null) {
                    var userCards = rep.data.cards.size.toString();
                    var str: String = data.size.toString()

                    if (userCards.toIntOrNull() != null) {
                        this.userCards_total = userCards.toIntOrNull()!!
                    }
                    setTextCards(userCards);
                } else {
                    setTextCards("0");
                }
                CallBackGenerator(callback = this, action = "GetAllCard", isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()

            }   //-> rep = service.GetCardByUserId(idUser!!)    //alimenter le popup


            "SetOneMoney" -> {

                if (data != null) {
                    Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show()
                    // this.userMoney_total= this.userMoney_total - data.


                }

            }       // -> rep = service.SetOneMoney(idUser!!, value!!) //après achat
            "SetOneCard" -> {

                if (data != null) {
                    Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show()
                    //      this.userCards_total = this.userCards_total +

                    //  setTextCards( "0");
                    CallBackGenerator(callback = this, action = "SetOneMoney", isActivateCallBack = true, idUser = _idUser, value = _cost, url = "https://api.coopuniverse.fr/").execute()
                }
            }        //rep = service.SetOneCard(idUser!!,idCard!!)     //après achat


        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // var view:View = inflater.inflate(R.layout.inventory_fragment, container, false)  //inflater.inflate(R.layout.shop_fragment, container, false)

        //anotherView= inflater.inflate(R.layout.inventory_fragment, container, false)

        tCredits = inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCredits);

        // tCredits.addTextChangedListener(new TextWatcher);
        tCards = inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCards)

        //on execute trois requetes , comment les mettre en ordre  pour qu'un ne derrange à l'autre de recouperer les results ?
        // CallBackGenerator(callback = this,action = "GetAllCard",isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()
        getUserData();
        // CallBackGenerator(callback = this,action = "GetOneMoney",isActivateCallBack = true, idUser="23", url = "https://api.coopuniverse.fr/").execute()
        //   CallBackGenerator(callback = this,action = "GetCardByUserId",isActivateCallBack = true,idUser="23", url = "https://api.coopuniverse.fr/").execute()


        return inflater.inflate(fr.coopuniverse.api.pokeapi.R.layout.inventory_fragment, container, false)


    }

    fun getUserData() {
        CallBackGenerator(callback = this, action = "GetOneMoney", isActivateCallBack = true, idUser = "23", url = "https://api.coopuniverse.fr/").execute()
    }

    fun setTextCredits(text: String) {
        val textView = view!!.findViewById(fr.coopuniverse.api.pokeapi.R.id.tCredits) as TextView
        var txt = tCredits!!.text.toString();
        var txtCredit = " credits ";
        textView.text = txt + " " + text + txtCredit;
        return inflater.inflate(R.layout.inventory_fragment, container, false)
    }

    fun setTextCards(text: String) {
        val textView = view!!.findViewById(fr.coopuniverse.api.pokeapi.R.id.tCards) as TextView
        var txt = tCards!!.text.toString();
        var txtCards = " cards "
        textView.text = txt + " " + text + txtCards
    }


    companion object {
        fun newInstance() = ShopFragment()
    }


}
