package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager

object CraftViewModel : CallBackDisplay {

    var inventory = ArrayList<Card>()
    var listNameCard = MutableLiveData<ArrayList<String>>()
    var result = MutableLiveData<String>()
    var stateButton = MutableLiveData<Boolean>()
    var viewInProgress = MutableLiveData<Boolean>()

    override fun display(rep: Reponse, action: String) {
        when(action) {
            Route.GET_CARD_BY_USER_ID.get -> {
                this.inventory = rep.data.inventory

                var i = 0
                var arrayS = ArrayList<String>()
                while(i < inventory.size)
                {
                    arrayS.add(inventory[i].name.toString())
                    i++
                }
                listNameCard.postValue(arrayS)
            }
            Route.MELT_CARDS.get -> {
                if(rep.result === false)
                {
                    result.postValue("You have inadvertently dropped your card, your card has become illegible and unusable sorry \uD83D\uDE31")
                }
                else
                {
                    val castedCard = rep.result as String
                    result.postValue("🎉 Congratulations, you created " + castedCard)
                }
                CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()
            }
        }

    }

    fun initData() {
        CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()
    }

    fun craftCard(positionCardOne:Int,positionCardTwo: Int,positionCardThree: Int) {
        viewInProgress.postValue(true)
        stateButton.postValue(false)
        Handler().postDelayed({
            CallHttpManager(callback = this, action = Route.MELT_CARDS.get, isActivateCallBack = true, idUser = Account.id, idCardOne = inventory[positionCardOne].id, idCardTwo = inventory[positionCardTwo].id, idCardThree = inventory[positionCardThree].id, url = Config.url).execute()
        }, 1000)

    }
}
