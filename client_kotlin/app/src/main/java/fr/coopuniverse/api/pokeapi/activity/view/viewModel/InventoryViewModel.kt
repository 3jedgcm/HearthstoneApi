package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackOnClickCard
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager

object InventoryViewModel : CallBackDisplay, CallBackOnClickCard {


    var dataCardsByUserID = MutableLiveData<ArrayList<Card>>()
    var nbCardsUser = MutableLiveData<Int>()
    var idCardClicked = MutableLiveData<String>()
    var costCardClicked = MutableLiveData<String>()
    var cardsUserInventory = MutableLiveData<ArrayList<Card>>()

    fun initData() {
        CallHttpManager(callback = this, action = Route.GET_CARD_BY_USER_ID.get, idUser = Account.id, isActivateCallBack = true, url = Config.url).execute()
    }

    override fun display(rep: Reponse, action: String) {

        if (action.equals(Route.GET_CARD_BY_USER_ID.get)) {
            cardsUserInventory.postValue(rep.data.inventory)
        }

    }

    override fun onClickCard(cardId: String, cardCost: Int, cardCostStr: String) {

        idCardClicked.postValue(cardId)
        costCardClicked.postValue(cardCostStr)

    }


}

