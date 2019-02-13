package fr.coopuniverse.api.pokeapi.activity.manager

import android.os.AsyncTask
import android.widget.TextView
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import fr.coopuniverse.api.pokeapi.activity.route.CoopUniverseService

import java.io.IOException

import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallHttpManager(
        private var callback: CallBackDisplay,
        private var isActivateCallBack: Boolean? = false,
        private var typeFilter: String? = "",
        private var valueFilter: String? = "",
        private var url: String? = "",
        private var idUser: String? = "",
        private var idUserTwo: String? = "",
        private var idCard: String? = "",
        private var idCardOne: String? = "",
        private var idCardTwo: String? = "",
        private var idCardThree: String? = "",
        private var cardUserOne: String? = "",
        private var cardUserTwo: String? = "",
        private var answser: String? = "",
        private var value: String? = "",
        private var action: String? = "",
        private var key: String? = "",
        private var login: String? = "",
        private var pass: String? = "")
    : AsyncTask<TextView, Void, Reponse>() {

    private fun generateCallBack(): Reponse {
        val response: Reponse?
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
            "GetCardByFilter" -> rep = service.GetCardByFilter(typeFilter!!, valueFilter!!)
            "SetOneCard" -> rep = service.SetOneCard(idUser!!, idCard!!)
            "SetOneMoney" -> rep = service.SetOneMoney(idUser!!, value!!)
            "SetAnswer" -> rep = service.SetAnswer(answser!!, value!!)
            "ExchangeCards" -> rep = service.ExchangeCards(idUser!!, idUserTwo!!, cardUserOne!!.toString(), cardUserTwo!!.toString())
            "MeltCards" -> rep = service.MeltCards(idUser!!, idCard!!)
            "CraftOneCard" -> rep = service.CraftOneCard(idUser!!, idCardOne!!, idCardTwo!!, idCardThree!!)
            "Connect" -> rep = service.SimpleLogin(login!!, pass!!)
            "ConnectFacebook" -> rep = service.FacebookLogin(key!!)
            "ConnectGoogle" -> rep = service.GoogleLogin(key!!)
            "Register" -> rep = service.SimpleRegister(login!!, pass!!)
            "RegisterFacebook" -> rep = service.FacebookRegister(key!!)
            "RegisterGoogle" -> rep = service.GoogleRegister(key!!)
            else -> rep = service.GetAllCard()
        }
        try {
            response = rep.execute().body()
            return response

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Reponse(key!!)
    }

    override fun doInBackground(vararg TextViews: TextView?): Reponse? {
        return generateCallBack()
    }

    override fun onPostExecute(result: Reponse) {
        if (isActivateCallBack == true) {
            result.id = key!!
            callback.display(result, this.action!!)
        }
    }
}
