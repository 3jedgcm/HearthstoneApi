package fr.coopuniverse.api.pokeapi.activity.manager

import android.os.AsyncTask
import android.widget.TextView
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Response.*
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.route.CoopUniverseService

import java.io.IOException

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallHttpManager(
        private var callback: CallBackDisplay,
        private var isActivateCallBack: Boolean? = false,
        private var typeFilter: String? = "",
        private var valueFilter: String? = "",
        private var url: String? = "",
        private var idUserOne: String? = "",
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
            Route.GET_ONE_MONEY.get -> {
                response = service.GetOneMoney(idUserOne!!).execute().body()
            }
            Route.GET_ONE_USER.get -> {
                response = service.GetOneUser(idUserOne!!).execute().body()
            }
            Route.GET_ALL_USER.get -> {
                response = service.GetAllUser().execute().body()
            }
            Route.GET_CARD_BY_USER_ID.get -> {
                response = service.GetCardByUserId(idUserOne!!).execute().body()
            }
            Route.GET_ALL_CARD.get -> {
                response = service.GetAllCard().execute().body()
            }
            Route.GET_RANDOM_CARD.get -> {
                response = service.GetRandomCard().execute().body()
            }
            Route.GET_ALL_PARAMETER.get -> {
                response = service.GetAllParameter().execute().body()
            }
            Route.GET_QUESTION.get -> {
                response = service.GetQuestion().execute().body()
            }
            Route.GET_CARD_BY_FILTER.get -> {
                response = service.GetCardByFilter(typeFilter!!, valueFilter!!).execute().body()
            }
            Route.SET_ONE_CARD.get -> {
                response = service.SetOneCard(idUserOne!!, idCard!!).execute().body()
            }
            Route.SET_ONE_MONEY.get -> {
                response = service.SetOneMoney(idUserOne!!, value!!).execute().body()
            }
            Route.SET_ANSWER.get -> {
                response = service.SetAnswer(answser!!, value!!).execute().body()
            }
            Route.EXCHANGE_CARDS.get -> {
                response = service.ExchangeCards(idUserOne!!, idUserTwo!!, cardUserOne!!, cardUserTwo!!).execute().body() as ResponseExchangeCards
            }
            Route.MELT_CARDS.get -> {

                response = service.MeltCards(idUserOne!!, idCard!!).execute().body()
            }
            Route.CRAFT_ONE_CARDS.get -> {
                response = service.CraftOneCard(idUserOne!!, idCardOne!!, idCardTwo!!, idCardThree!!).execute().body() as ResponseCraftOneCard
            }
            Route.CONNECT.get -> {
                response = service.SimpleLogin(login!!, pass!!).execute().body()
            }
            Route.CONNECT_WITH_FACEBOOK.get -> {
                response =  service.FacebookLogin(key!!).execute().body() as ResponseConnectFacebook
            }
            Route.CONNECT_WITH_GOOGLE.get -> {
                response = service.GoogleLogin(key!!).execute().body() as ResponseConnectGoogle
            }
            Route.REGISTER.get -> {
                response = service.SimpleRegister(login!!, pass!!).execute().body() as ResponseRegister
            }
            Route.REGISTER_WITH_FACEBOOK.get -> {
                response = service.FacebookRegister(key!!).execute().body() as ResponseRegisterFacebook
            }
            Route.REGISTER_WITH_GOOGLE.get -> {
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
