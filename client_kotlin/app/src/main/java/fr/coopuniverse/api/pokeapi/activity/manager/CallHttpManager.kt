package fr.coopuniverse.api.pokeapi.activity.manager

import android.os.AsyncTask
import android.widget.TextView
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Response.*
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
    : AsyncTask<TextView, Void, Response>() {

    private fun generateCallBack(): Response {

        val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(CoopUniverseService::class.java)
        try {
            var response : Response?
        when (action) {
            "GetOneMoney" -> {
                response = service.GetOneMoney(idUser!!).execute().body()
            }
            "GetOneUser" -> {
                response = service.GetOneUser(idUser!!).execute().body()
            }
            "GetAllUser" -> {
                response = service.GetAllUser().execute().body()
            }
            "GetCardByUserId" -> {
                response = service.GetCardByUserId(idUser!!).execute().body()
            }
            "GetAllCard" -> {
                response = service.GetAllCard().execute().body()
            }
            "GetRandomCard" -> {
                response = service.GetRandomCard().execute().body()
            }
            "GetAllParameter" -> {
                response = service.GetAllParameter().execute().body()
            }
            "GetQuestion" -> {
                response = service.GetQuestion().execute().body()
            }
            "GetCardByFilter" -> {
                response = service.GetCardByFilter(typeFilter!!, valueFilter!!).execute().body()
            }
            "SetOneCard" -> {
                response = service.SetOneCard(idUser!!, idCard!!).execute().body()
            }
            "SetOneMoney" -> {
                response = service.SetOneMoney(idUser!!, value!!).execute().body()
            }
            "SetAnswer" -> {
                response = service.SetAnswer(answser!!, value!!).execute().body()
            }
            "ExchangeCards" -> {
                response = service.ExchangeCards(idUser!!, idUserTwo!!, cardUserOne!!.toString(), cardUserTwo!!.toString()).execute().body() as ResponseExchangeCards
            }
            "MeltCards" -> {

                response = service.MeltCards(idUser!!, idCard!!).execute().body()
            }
            "CraftOneCard" -> {
                response = service.CraftOneCard(idUser!!, idCardOne!!, idCardTwo!!, idCardThree!!).execute().body() as ResponseCraftOneCard
            }
            "Connect" -> {
                response = service.SimpleLogin(login!!, pass!!).execute().body()
            }
            "ConnectFacebook" -> {
                response =  service.FacebookLogin(key!!).execute().body() as ResponseConnectFacebook
            }
            "ConnectGoogle" -> {
                response = service.GoogleLogin(key!!).execute().body() as ResponseConnectGoogle
            }
            "Register" -> {
                response = service.SimpleRegister(login!!, pass!!).execute().body() as ResponseRegister
            }
            "RegisterFacebook" -> {
                response = service.FacebookRegister(key!!).execute().body() as ResponseRegisterFacebook
            }
            "RegisterGoogle" -> {
                response = service.GoogleRegister(key!!).execute().body()
            }
            else -> {

                response = service.GetAllCard().execute().body()
            }
        }


            return response!!

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ResponseSimple()
    }

    override fun doInBackground(vararg TextViews: TextView?): Response? {
        return generateCallBack()
    }

    override fun onPostExecute(result: Response) {
        if (isActivateCallBack == true) {
            callback.display(result, this.action!!)
        }
    }
}
