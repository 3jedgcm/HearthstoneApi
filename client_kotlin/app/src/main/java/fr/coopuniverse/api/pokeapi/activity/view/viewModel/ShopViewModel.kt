@file:Suppress("UNREACHABLE_CODE")

package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import android.provider.ContactsContract
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.adapter.CardsListAdapterStore
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import fr.coopuniverse.api.pokeapi.activity.enum.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager
import kotlinx.android.synthetic.main.inventory_fragment.*

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


    fun initDataUser() {

        if (flagUpdateListofItems) {
            CallHttpManager(callback = this, action = Route.GET_ALL_CARD.get, isActivateCallBack = true, url = Config.url).execute()
        }
    }


    override fun display(rep: Reponse, action: String) {


        var data: ArrayList<Card>? = null

        var money: String? = null
        var userCards: ArrayList<Card>? = null
        if (rep != null && rep.data != null) {
            data = rep.data.cards
            money = rep.data.money
            userCards = rep.data.inventory
        }


        if (!rep.connect) {
            when (action) {
                "GetAllCard" -> {
                    dataAllCards.postValue(data)

                    CallHttpManager(callback = this, action = Route.GET_ONE_MONEY.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()

                }
                "GetOneMoney" -> {

                    if (money == null) {
                        money = "0"
                    }
                    dataMoneyUser.postValue(Integer.valueOf(money))

                    Account.money = money.toString() //(?) update Account

                    this.userMoney_total = money.toIntOrNull()!!
                    this.userMoney_total_Mutable.postValue(money.toIntOrNull()!!)
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
