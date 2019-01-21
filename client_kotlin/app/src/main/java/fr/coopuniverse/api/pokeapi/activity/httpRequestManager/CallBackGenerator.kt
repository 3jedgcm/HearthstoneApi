package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackDisplay

import java.io.IOException

import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallBackGenerator(
        var callback: CallBackDisplay,
        var isActivateCallBack: Boolean? = false,
        var typeFilter: String? = null,
        var valueFilter: String? = null,
        var url: String? = null,
        var idUser: String? = null,
        var idUserTwo: String? = null,
        var idCard: String? = null,
        var idCardOne: String? = null,
        var idCardTwo: String? = null,
        var idCardThree: String? = null,
        var cardUserOne: String? = null,
        var cardUserTwo: String? = null,
        var answser: String? = null,
        var value: String? = null,
        var action: String? = null,
        var key: String? = null,
        var login: String? = null,
        var pass: String? = null)
    : AsyncTask<TextView, Void, Reponse>() {


    fun generateCallBack(): Reponse {

        Log.d("Chaton",""+pass + " " + login)
        var reponse: Reponse?
        val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(CoopUniverseService::class.java)
        val rep: Call<Reponse>
        when (action) {
            "GetOneMoney" -> rep = service.GetOneMoney(idUser!!)
            "GetAllMoney" -> rep = service.GetAllMoney()
            "GetOneUser" -> rep = service.GetOneUser(idUser!!)
            "GetAllUser" -> rep = service.GetAllUser()
            "GetCardByUserId" -> rep = service.GetCardByUserId(idUser!!)
            "GetAllCard" -> rep = service.GetAllCard()
            "GetRandomCard" -> rep = service.GetRandomCard()
            "GetAllParameter" -> rep = service.GetAllParameter()
            "GetQuestion" -> rep = service.GetQuestion()
            "GetCardByFilter" -> rep = service.GetCardByFilter(typeFilter!!,valueFilter!!)
            "SetOneCard" -> rep = service.SetOneCard(idUser!!,idCard!!)
            "SetOneMoney" -> rep = service.SetOneMoney(idUser!!, value!!)
            "SetAnswer" -> rep = service.SetAnswer(idUser!!, answser!!)
            "ExchangeCards" -> rep = service.ExchangeCards(idUser!!, idUserTwo!!, cardUserOne!!.toString(), cardUserTwo!!.toString())
            "MeltCards" -> rep = service.MeltCards(idUser!!,idCard!!)
            "CraftOneCard" -> rep = service.CraftOneCard(idUser!!,idCardOne!!,idCardTwo!!,idCardThree!!)
            "Connect" -> rep = service.SimpleLogin(login!!, pass!!)
            "ConnectFacebook" -> rep = service.FacebookLogin(key!!)
            "ConnectGoogle" -> rep = service.GoogleLogin(key!!)
            "Register" -> rep = service.SimpleRegister(login!!, pass!!)
            "RegisterFacebook" -> rep = service.FacebookRegister(key!!)
            "RegisterGoogle" -> rep = service.GoogleRegister(key!!)
            else -> rep = service.GetAllCard()
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


    }
}
