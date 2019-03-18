package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Response.Response
import fr.coopuniverse.api.pokeapi.activity.data.Response.ResponseGetCardByUserId
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager

object InventoryViewModel : CallBackDisplay, CallBackOnClickCard {


    var dataCardsByUserID = MutableLiveData<ArrayList<Card>>()
    var nbCardsUser = MutableLiveData<Int>()
    var idCardClicked = MutableLiveData<String>()
    var costCardClicked = MutableLiveData<String>()
    var cardsUserInventory = MutableLiveData<ArrayList<Card>>()

    fun initData() {
        CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, idUserOne = Account.id, isActivateCallBack = true, url = Config.url).execute()
    }

    override fun display(abstractRep: Response, action: String) {
        var rep : Response
        if (action.equals(Route.GET_CARD_BY_USER_ID.get)) {
            rep = abstractRep as ResponseGetCardByUserId
            cardsUserInventory.postValue(rep.data!!.inventory.inventory)
        }

    }

    override fun onClickCard(cardId: String, cardCost: Int, cardCostStr: String) {
        idCardClicked.postValue(cardId)
        costCardClicked.postValue(cardCostStr)

    }
}

