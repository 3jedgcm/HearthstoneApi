@file:Suppress("UNREACHABLE_CODE")

package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Response.ResponseSimple
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager
import okhttp3.Response

object ShopViewModel : CallBackDisplay, CallBackOnClickCard {


    var dataAllCards = MutableLiveData<ArrayList<Card>>()
    var nbCardsUser = MutableLiveData<Int>()
    var dataCardsUser = MutableLiveData<ArrayList<Card>>()
    var dataMoneyUser = MutableLiveData<Int>()

    var comunicate = MutableLiveData<Int>()
    var userCards_total_Mutable = MutableLiveData<Int>()
    var userMoney_total_Mutable = MutableLiveData<Int>()

    private var cost = 0
    private var idCard = "N/A"
    private var userCards_total = 0
    private var userMoney_total = 0
    private var flagUpdateListofItems = true
    private lateinit var responseObserver: Observer<String>
    private lateinit var actionObserver: Observer<String>


    fun initDataUser() {

        if (flagUpdateListofItems) {
           getAllCard()
        }
    }

    fun getAllCard()
    {
        var call = CallHttpManager(action = Route.GET_ALL_CARD.get, url = Config.url)
        var currentAction = ""
        actionObserver = Observer {
            currentAction = it
        }
        call.currentAction.observeForever(observer)

        call.liveResponse.observeForever {
            var response = it as (ResponseSimple)
            when(currentAction)
            {
                Route.GET_ALL_CARD.get-> {
                    //TODO rep.data.cards
                    dataAllCards.postValue(ArrayList())

                    call.currentAction.removeObserver(observer)
                }
            }
        }
        call.execute()
    }


    fun getOneMoney()
    {
        var call = CallHttpManager(action = Route.GET_ONE_MONEY.get, url = Config.url)
        var currentAction = ""
        observer = Observer {
            currentAction = it
        }
        call.currentAction.observeForever(observer)

        call.liveResponse.observeForever {
            var response = it as (ResponseSimple)
            when(currentAction)
            {
                Route.GET_ONE_MONEY.get-> {
                    //TODO rep.data.cards
                    dataAllCards.postValue(ArrayList())

                    call.currentAction.removeObserver(observer)
                }
            }
        }
        call.execute()
    }






    override fun display(rep: Reponse, action: String) {


        var data: ArrayList<Card>? = null

        var money: String? = null
        var userCards: ArrayList<Card>? = null
        if (rep != null && rep.data != null) {
            data =
            money = rep.data.money
            userCards = rep.data.inventory
        }


        if (!rep.connect) {
            when (action) {
                "GetAllCard" -> {


                    CallHttpManager(callback = this, action = Route.GET_ONE_MONEY.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()

                }
                "GetOneMoney" -> {


                    CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()

                }
                "GetCardByUserId" -> {

                    var userCardsCount = 0
                    if (userCards != null) {

                        if (userCards != null) {
                            userCardsCount = userCards.count()
                            this.userCards_total = userCards.count()
                        }
                    }

                    nbCardsUser.postValue(userCardsCount)


                }
                "SetOneMoney" -> {
                    if (this.cost != null) {
                        flagUpdateListofItems = false
                    }
                }
                "SetOneCard" -> {
                    this.userMoney_total = this.userMoney_total.minus(this.cost)
                    userMoney_total_Mutable.postValue(this.userMoney_total)
                    //setTextCredits(this.userMoney_total.toString())
                    CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = true, idUser = Account.id, value = userMoney_total.toString(), url = Config.url).execute()
                }
            }

        }
    }


    override fun onClickCard(idCard: String, cost: Int, costStr: String) {


        this.cost = cost
        this.idCard = idCard
        if (this.idCard != null) {
            if (this.userMoney_total < this.cost) {
                comunicate.postValue(R.string.insufficient_credit)
                return
            } else if (this.userMoney_total == this.cost) {
                comunicate.postValue(R.string.attention_credit)
            }
            this.userCards_total++

            userCards_total_Mutable.postValue(this.userCards_total)
            CallHttpManager(callback = this, action = Route.SET_ONE_CARD.get, isActivateCallBack = true, idUser = Account.id, idCard = idCard, url = Config.url).execute()
        }


    }


}
