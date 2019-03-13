package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.*
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager




import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Reponse


object  ExchangeViewModel  : CallBackDisplay {


   // var dataAllCards = MutableLiveData<ArrayList<Card>>()

    var nbCardsUser = MutableLiveData<Int>()
    var dataCardsUser = MutableLiveData<ArrayList<Card>>()
    var dataMoneyUser = MutableLiveData<Int>()

    var comunicate = MutableLiveData<Int>()
    var userCards_total_Mutable = MutableLiveData<Int>()

    var userMoney_total_Mutable = MutableLiveData<Int>()
    var listofUsers_Mutable = MutableLiveData<ArrayList<Any>>()


    private var cost = 0
    private var idCard = "N/A"
    private var userCards_total = 0
    private var userMoney_total = 0
    private var flagUpdateListofItems = true
    private var currentUser: String = ""
    private var listofUsers: ArrayList<Any>? = null


    /*  fun initDataUser() {

          if (flagUpdateListofItems) {
              CallHttpManager(callback = this, action = Route.GET_ALL_CARD.get, isActivateCallBack = true, url = Config.url).execute()
          }
      }
  */

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
    //whitout current user
        currentUser = _idCurrentUser
        CallHttpManager(callback = this, action = Route.GET_ALL_USER.get, isActivateCallBack = true, url = Config.url).execute()


    }
    fun getCardofUser(_idUser: String ){


        CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUser = _idUser, url = Config.url).execute()


    }


    private var idUser2: String? =""
    private val idCard1: String?= ""
    private val idCard2: String? = ""


    override fun display(rep: Reponse, action: String) {


        var data: ArrayList<Card>? = null

        var usersData: ArrayList<Any> ?= null

        var money: String? = null
        var userCards: ArrayList<Card>? = null

        if (rep != null && rep.data != null) {
            data = rep.data.cards
            money = rep.data.money
            userCards = rep.data.inventory
          //  usersData = rep.data.user; //ALL USERS


        }


        if (!rep.connect) {
            when (action) {

             /*   Route.GET_ALL_CARD.get -> {
                    dataAllCards.postValue(data)

                    CallHttpManager(callback = this, action = Route.GET_ONE_MONEY.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()

                }

                */

                Route.GET_ALL_USER.get -> {
                //TODO
                   // listofUsers = usersData.remove(currentUser[])
                   //Account.user

                    usersData!!.remove(Account.id);

                    listofUsers = usersData;

                    // recoup allUsers without currentUser  update spinner Users
                    listofUsers_Mutable.postValue(listofUsers)

                }


                Route.GET_ONE_MONEY.get -> {

                    if (money == null) {
                        money = "0"
                    }
                    dataMoneyUser.postValue(Integer.valueOf(money))

                    Account.money = money.toString() //(?) update Account

                    this.userMoney_total = money.toIntOrNull()!!
                    this.userMoney_total_Mutable.postValue(money.toIntOrNull()!!)
                    CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()

                }
                Route.GET_CARD_BY_USER_ID.get -> {

                    var userCardsCount = 0
                    if (userCards != null) {

                        if (userCards != null) {
                            userCardsCount = userCards.count()
                            this.userCards_total = userCards.count()
                        }
                    }
                    nbCardsUser.postValue(userCardsCount)


                }

               Route.EXCHANGE_CARDS.get -> {

                   //idUserOne, cardUserOne, idUserTwo, cardUserTwo


                   var userCardsCount = 0
                   if (userCards != null){
                       userCardsCount = userCards.count()
                   }

                   //update the spinnercards user1 , update spinnerCard user2



               }

               /* Route.SET_ONE_MONEY.get -> {
                    if (this.cost != null) {
                        flagUpdateListofItems = false
                    }
                }
                Route.SET_ONE_CARD.get -> {
                    this.userMoney_total = this.userMoney_total.minus(this.cost)
                    userMoney_total_Mutable.postValue(this.userMoney_total)
                    //setTextCredits(this.userMoney_total.toString())
                    CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = true, idUser = Account.id, value = userMoney_total.toString(), url = Config.url).execute()
                }
                */
            }

        }
    }
}


