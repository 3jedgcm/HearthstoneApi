package fr.coopuniverse.api.pokeapi.activity.view.viewModel


import android.os.Handler
import android.view.View
import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Account.money
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Response.*
import fr.coopuniverse.api.pokeapi.activity.data.User
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager


object ExchangeViewModel : CallBackDisplay {

    var nbCardsUser = MutableLiveData<Int>()
    var dataCardsUser = MutableLiveData<ArrayList<Card>>()
    var dataCardsCurrentUser = MutableLiveData<ArrayList<Card>>()
    var dataMoneyUser = MutableLiveData<Int>()
    var userMoney_total_Mutable = MutableLiveData<Int>()
    var listofUsers_Mutable = MutableLiveData<ArrayList<User>>()
    var viewInProgress = MutableLiveData<Boolean>()

    private var userCards_total = 0
    private var userMoney_total = 0
    private var currentUserID: String = ""
    private var currentUsr: User? = null
    private var listofUsers: ArrayList<User>? = null
    private var idUserTwo = ""
    private var idUserOne = ""

    fun exchangeCards(idUser: String, idUserTwo: String, idCard: String, idCardTwo: String) {
        viewInProgress.postValue(true)
        this.idUserTwo = idUserTwo
        this.idUserOne = idUser

        Handler().postDelayed({
            if (idUser != null && idUserTwo != null && idCard != null && idCardTwo != null) {
                CallHttpManager(callback = this, action = Route.EXCHANGE_CARDS.get, isActivateCallBack = true,
                        idUserOne = idUser,
                        idUserTwo = idUserTwo,
                        cardUserOne = idCard,
                        cardUserTwo = idCardTwo,
                        url = Config.url).execute()
            }
           }, 3000)
    }

    fun getAllUsers(_idCurrentUser: String) {
        currentUserID = _idCurrentUser
        CallHttpManager(callback = this, action = Route.GET_ALL_USER.get, isActivateCallBack = true, url = Config.url).execute()
    }

    fun getCardofUser(_idUser: String) {
        CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUserOne = _idUser, url = Config.url).execute()
    }

    override fun display(abstractRep: Response, action: String) {
        var rep: Response
        when (action) {
            Route.GET_ALL_USER.get -> {
                rep = abstractRep as ResponseGetAllUser
                listofUsers = rep.data!!.user;
                if (Account!!.user != null) {
                    currentUsr = Account!!.user!! as User
                } else {
                    listofUsers!!.forEach {
                        if (it.IdUser.equals(currentUserID)) {
                            currentUsr = it
                        }
                    }
                }
                listofUsers_Mutable.postValue(listofUsers)
            }

            Route.GET_ONE_MONEY.get -> {
                rep = abstractRep as ResponseGetOneMoney
                if (rep.data!!.money == null) {
                    money = "0"
                }
                dataMoneyUser.postValue(Integer.valueOf(rep.data!!.money!!.money))
                Account.money = rep.data!!.money!!.money!!
                this.userMoney_total = rep.data!!.money!!.money!!.toIntOrNull()!!
                this.userMoney_total_Mutable.postValue(rep.data!!.money!!.money!!.toIntOrNull()!!)
                CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUserOne = Account.id, url = Config.url).execute()

            }
            Route.GET_CARD_BY_USER_ID.get -> {
                rep = abstractRep as ResponseGetCardByUserId
                var userCardsCount = 0
                var user: String?
                if (rep.data != null) {
                    if (rep.data!!.inventory.inventory != null) {
                        userCardsCount = rep.data!!.inventory.inventory.count()
                        this.userCards_total = rep.data!!.inventory.inventory.count()
                    }
                    user = rep.data!!.inventory.user
                    if (user != null && user != currentUserID) {
                        dataCardsUser.postValue((rep.data!!.inventory.inventory))
                    } else {
                        dataCardsCurrentUser.postValue((rep.data!!.inventory.inventory))
                    }
                    nbCardsUser.postValue(userCardsCount)
                }
            }

            Route.EXCHANGE_CARDS.get -> {
                rep = abstractRep as ResponseExchangeCards
                if (rep.exitCode!!.equals(0)) {
                    getCardofUser(this.idUserOne)
                    getCardofUser(this.idUserTwo)
                }
                viewInProgress.postValue(false)
            }
        }
    }
}


