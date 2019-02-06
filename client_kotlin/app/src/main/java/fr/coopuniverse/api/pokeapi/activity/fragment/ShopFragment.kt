package fr.coopuniverse.api.pokeapi.activity.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapterStore
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import kotlinx.android.synthetic.main.inventory_fragment.*

class ShopFragment  : androidx.fragment.app.Fragment(), CallBackDisplay, CallBackOnClickCard {


    var recView_Shop: androidx.recyclerview.widget.RecyclerView? = null;
    var anotherView: View? = null
    var tCredits : TextView?= null  //credit/money par user
    var tCards   : TextView?= null //nb cards par user


    override fun onClickCard(idCard: String,cost:String) {
        Log.d("Chaton",idCard.toString())


    //    CallBackGenerator(callback = this,action = "SetOneMoney",isActivateCallBack = true,idUser="1" ,value=cost, url = "https://api.coopuniverse.fr/").execute()

    //    CallBackGenerator(callback = this,action = "SetOneCard",isActivateCallBack = true,idUser="1" ,idCard=idCard, url = "https://api.coopuniverse.fr/").execute()

    }

    override fun display(rep: Reponse, action: String) {


        var data = rep.data.cards

        Log.d("chat","Shop1: " +data.toString() )


        when(action){
             "GetAllCard"->{
                 var adapterReclViewcard: CardsListAdapterStore = CardsListAdapterStore(data, targetFragment, this)

                 recView_Inventory?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)//GridLayoutManager(this.context, 1) //androidx.recyclerview.widget.LinearLayoutManager(this.context)
                 recView_Inventory?.adapter=  adapterReclViewcard

              }        //-> rep = service.GetAllCard()

             "GetOneMoney"->{
                 tCredits!!.setText(data.toString());

             }       //-> rep = service.GetOneMoney(idUser!!) //alimenter le popup
             "GetCardByUserId"->{
                 var str :String = data.size.toString()

                 tCredits!!.setText(data.size.toString())

             }   //-> rep = service.GetCardByUserId(idUser!!)    //alimenter le popup


            "SetOneMoney"->{
                 Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show() }       // -> rep = service.SetOneMoney(idUser!!, value!!) //après achat
             "SetOneCard"->{
                 Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show() }        //rep = service.SetOneCard(idUser!!,idCard!!)     //après achat



        }





    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

       // var view:View = inflater.inflate(R.layout.inventory_fragment, container, false)  //inflater.inflate(R.layout.shop_fragment, container, false)

        anotherView= inflater.inflate(R.layout.inventory_fragment, container, false)

        tCredits  =inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCredits)
        tCards    =inflater.inflate(R.layout.inventory_fragment, container, false).findViewById(R.id.tCards)

        CallBackGenerator(callback = this,action = "GetAllCard",isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()

        CallBackGenerator(callback = this,action = "GetOneMoney",isActivateCallBack = true, idUser="1", url = "https://api.coopuniverse.fr/").execute()
        CallBackGenerator(callback = this,action = "GetCardByUserId",isActivateCallBack = true,idUser="1", url = "https://api.coopuniverse.fr/").execute()




        return inflater.inflate(R.layout.inventory_fragment, container, false)
    }

}
