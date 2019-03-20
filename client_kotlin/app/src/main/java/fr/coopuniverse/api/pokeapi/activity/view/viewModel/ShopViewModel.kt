
package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Account.money
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Response.*
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager

object ShopViewModel : CallBackDisplay, CallBackOnClickCard {


    var dataAllCards = MutableLiveData<ArrayList<Card>>()
    var nbCardsUser = MutableLiveData<Int>()
    var dataCardsUser = MutableLiveData<ArrayList<Card>>()
    var dataMoneyUser = MutableLiveData<Int>()

    var comunicate = MutableLiveData<Int>()
    var userCards_total_Mutable = MutableLiveData<Int>()
    var userMoney_total_Mutable = MutableLiveData<Int>()
    var onBuy = false

    private var cost = 0
    private var idCard = "N/A"
    private var userCards_total = 0
    private var userMoney_total = 0
    private var flagUpdateListofItems = true


    fun initDataUser() {

        if (flagUpdateListofItems) {
            CallHttpManager(callback = this, action = Route.GET_ALL_CARD.get, isActivateCallBack = true, url = Config.url).execute()
        }
    }


    override fun display(abstractRep: Response, action: String) {

        var rep: Response

            when (action) {
                Route.GET_ALL_CARD.get-> {
                    rep = abstractRep as ResponseGetAllCard
                    dataAllCards.postValue(rep.data!!.cards)
                    CallHttpManager(callback = this, action = Route.GET_ONE_MONEY.get, isActivateCallBack = true, idUserOne = Account.id, url = Config.url).execute()

                }
                Route.GET_ONE_MONEY.get -> {
                    rep = abstractRep as ResponseGetOneMoney
                    if (rep.data!!.money!!.money == null) {
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
                    if (rep.data!!.inventory != null) {
                            userCardsCount = rep.data!!.inventory.inventory.count()
                            this.userCards_total = rep.data!!.inventory.inventory.count()
                        }
                    nbCardsUser.postValue(userCardsCount)
                }
                Route.SET_ONE_MONEY.get -> {
                    this.onBuy = false
                    if (this.cost != null) {
                        ExchangeViewModel.getCardofUser(Account.id)
                        ExchangeViewModel.getAllUsers(Account.id)
                        flagUpdateListofItems = false
                    }
                }
                Route.SET_ONE_CARD.get -> {
                    this.userMoney_total = this.userMoney_total.minus(this.cost)
                    userMoney_total_Mutable.postValue(this.userMoney_total)
                    CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = true, idUserOne = Account.id, value = userMoney_total.toString(), url = Config.url).execute()
                }


        }
    }


    override fun onClickCard(idCard: String, cost: Int, costStr: String) {
        this.cost = cost
        this.idCard = idCard
        if(!this.onBuy)
        {
            this.onBuy = true
            if (this.idCard != null) {
                if (this.userMoney_total < this.cost) {
                    comunicate.postValue(R.string.insufficient_credit)
                    return
                }
                else if (this.userMoney_total == this.cost) {
                    comunicate.postValue(R.string.attention_credit)
                }
                this.userCards_total++
                userCards_total_Mutable.postValue(this.userCards_total)
                CallHttpManager(callback = this, action = Route.SET_ONE_CARD.get, isActivateCallBack = true, idUserOne = Account.id, idCard = idCard, url = Config.url).execute()
            }
        }
    }


}
