package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Response.Response
import fr.coopuniverse.api.pokeapi.activity.data.Response.ResponseGetCardByUserId
import fr.coopuniverse.api.pokeapi.activity.data.Response.ResponseMeltCards
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager

object MeltViewModel : CallBackDisplay {

    var inventory = ArrayList<Card>()
    var listName = MutableLiveData<ArrayList<String>>()
    var result = MutableLiveData<String>()
    var stateButton = MutableLiveData<Boolean>()
    var viewInProgress = MutableLiveData<Boolean>()

    override fun display(abstractResponse: Response, action: String) {
        var rep : Response
        when(action) {
            Route.GET_CARD_BY_USER_ID.get -> {
                rep = abstractResponse as ResponseGetCardByUserId
                this.inventory = rep.data!!.inventory.inventory
                CraftViewModel.inventory = this.inventory
                CraftViewModel.updateCard()
                this.updateCard()
            }
            Route.MELT_CARDS.get -> {
                rep = abstractResponse as ResponseMeltCards
                if(rep.data!!.result === false)
                {
                    result.postValue("You have inadvertently dropped your card, your card has become illegible and unusable sorry \uD83D\uDE31")
                }
                else
                {
                    val castedCard = rep.data!!.name
                    result.postValue("🎉 Congratulations, you created " + castedCard)
                }
                CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUserOne = Account.id, url = Config.url).execute()

            }
        }

    }

    fun initData() {
        CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, isActivateCallBack = true, idUserOne = Account.id, url = Config.url).execute()
    }

    fun meltCard(position:Int) {
        viewInProgress.postValue(true)
        stateButton.postValue(false)
        Handler().postDelayed({
            CallHttpManager(callback = this, action = Route.MELT_CARDS.get, isActivateCallBack = true, idUserOne = Account.id, idCard = inventory[position].id, url = Config.url).execute()
        }, 3000)

    }

    fun updateCard() {
        var i = 0
        var arrayS = ArrayList<String>()
        while(i < inventory.size) {
            arrayS.add(inventory[i].name.toString())
            i++
        }
        listName.postValue(arrayS)
    }


}
