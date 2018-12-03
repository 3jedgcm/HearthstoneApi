package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import fr.coopuniverse.api.pokeapi.activity.CallBackDisplay

import java.io.IOException

import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallBackGenerator(
        var callback: CallBackDisplay,
        var isActivateCallBack: Boolean? = false,
        var url: String? = null,
        var idUser: String? = null,
        var idUserTwo: String? = null,
        var cards: List<Card>? = null,
        var cardUserOne: Card? = null,
        var cardUserTwo: Card? = null,
        var answser: String? = null,
        var value: String? = null,
        var action: String? = null,
        var key: String? = null,
        var login: String = null,
        var pass: String? = null)
    : AsyncTask<TextView, Void, Reponse>() {


    fun generateCallBack(): Reponse {

        var reponse: Reponse?

        val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(CoopUniverseService::class.java)
        val rep: Call<Reponse>
        when (action) {
            "GetOneMoney" -> rep = service.GetOneMoney(idUser!!)
            "GetAllMoney" -> rep = service.GetAllMoney()
            "SetOneMoney" -> rep = service.SetOneMoney(idUser!!, value!!)
            "SetAllMoney" -> rep = service.SetAllMoney(value!!)
            "GetOneUser" -> rep = service.GetOneUser(idUser!!)
            "SetOneUser" -> rep = service.SetOneUser(idUser!!, value!!)
            "GetAllUser" -> rep = service.GetAllUser()
            "SetAllUser" -> rep = service.SetAllUser(value!!)
            "GetOneCardByUserId" -> rep = service.GetOneCardByUserId(idUser!!)
            "GetAllCard" -> rep = service.GetAllCard()
            "SetOneCardByUserId" -> rep = service.SetOneCardByUserId(idUser!!, cardUserOne!!.toString())
            "DeleteOneCardByUserid" -> rep = service.DeleteOneCardByUserid(idUser!!)
            "ExchangeCards" -> rep = service.ExchangeCards(idUser!!, idUserTwo!!, cardUserOne!!.toString(), cardUserTwo!!.toString())
            "GetRandomCard" -> rep = service.GetRandomCard(idUser!!)
            "MeltCards" -> rep = service.MeltCards(idUser!!, cards!!.toString())
            "CraftOneCard" -> rep = service.CraftOneCard(idUser!!, cards!!.toString())
            "getQuestion" -> rep = service.getQuestion(idUser!!)
            "setAnswer" -> rep = service.setAnswer(idUser!!, answser!!)
            "GetAllParameter" -> rep = service.GetAllParameter()
            "Connect" -> rep = service.connect(login!!, pass!!)
            "ConnectFacebook" -> rep = service.connectFacebook(key!!)
            "ConnectGoogle" -> rep = service.connectGoogle(key!!)
            "Register" -> rep = service.connect(login!!, pass!!)
            "RegisterFacebook" -> rep = service.connectFacebook(key!!)
            "RegisterGoogle" -> rep = service.connectGoogle(key!!)

            else -> rep = service.getQuestion(idUser!!)
        }

        try {
            reponse = rep.execute().body()
            return reponse

        } catch (e: IOException) {
            e.printStackTrace()

        }
        return Reponse()

    }

    override fun doInBackground(vararg TextViews: TextView?): Reponse? {

        return generateCallBack()
    }

    override fun onPostExecute(result: Reponse)
    {
        if(isActivateCallBack == true)
        {
            callback.display(result)
        }

        Log.d("Chaton",""+result.toString())
    }
}
