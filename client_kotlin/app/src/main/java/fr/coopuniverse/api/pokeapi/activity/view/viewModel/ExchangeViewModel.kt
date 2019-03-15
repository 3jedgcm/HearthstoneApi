package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.*
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager



import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Account.money

import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Response.*


object  ExchangeViewModel  : CallBackDisplay {


    var nbCardsUser = MutableLiveData<Int>()
    var dataCardsUser = MutableLiveData<ArrayList<Card>>()
    var dataMoneyUser = MutableLiveData<Int>()

    var comunicate = MutableLiveData<Int>()
    var userCards_total_Mutable = MutableLiveData<Int>()

    var userMoney_total_Mutable = MutableLiveData<Int>()
    var listofUsers_Mutable = MutableLiveData<ArrayList<User>>()


    private var cost = 0
    private var idCard = "N/A"
    private var userCards_total = 0
    private var userMoney_total = 0
    private var flagUpdateListofItems = true
    private var currentUser: String = ""
    private var listofUsers: ArrayList<User>? = null

    fun exchangeCards( _idUser: String,_idUserTwo: String, _idCard: String, _idCardTwo: String ){

        if (_idUser !=null &&_idUserTwo != null && _idCard!= null && _idCardTwo!= null){

        CallHttpManager(callback = this, action = Route.EXCHANGE_CARDS.get, isActivateCallBack = true,
                idUser = _idUser, //Account.id,
                idUserTwo = _idUserTwo,
                idCard = _idCard,
                idCardTwo = _idCardTwo,
                url = Config.url).execute()
       }
    }

    fun getAllUsers(_idCurrentUser: String){
        currentUser = _idCurrentUser
        CallHttpManager(callback = this, action = Route.GET_ALL_USER.get, isActivateCallBack = true, url = Config.url).execute()
    }
    fun getCardofUser(_idUser: String ){
        CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUser = _idUser, url = Config.url).execute()
    }


    private var idUser2: String? =""
    private val idCard1: String?= ""
    private val idCard2: String? = ""


    override fun display(abstractRep: Response, action: String) {


        //var data: ArrayList<Card>? = null

        //var usersData: ArrayList<Any> ?= null

        //var money: String? = null
        //var userCards: ArrayList<Card>? = null
        var rep: Response

        /*
        if (rep != null && rep. != null) {
            data = rep.data.cards
            money = rep.data.money
            userCards = rep.data.inventory
        }
*/
            when (action) {

                Route.GET_ALL_USER.get -> {
                    rep = abstractRep as ResponseGetAllUser
                    //rep.data!!.user.remove(Account.id);
                    listofUsers = rep.data!!.user;
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
                    CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()

                }
                Route.GET_CARD_BY_USER_ID.get -> {
                    rep = abstractRep as ResponseGetCardByUserId
                    var userCardsCount = 0
                    if (rep.data!!.inventory != null) {

                        if (rep.data!!.inventory != null) {
                            userCardsCount = rep.data!!.inventory.inventory.count()
                            this.userCards_total = rep.data!!.inventory.inventory.count()
                        }
                    }
                    nbCardsUser.postValue(userCardsCount)
                }

               Route.EXCHANGE_CARDS.get -> {
                   rep = abstractRep as ResponseExchangeCards
               }
            }
    }
}


