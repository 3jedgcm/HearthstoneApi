package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import android.app.AlertDialog
import android.util.Log
import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager

object MeltViewModel : CallBackDisplay {

    var inventory = ArrayList<Card>()
    var listName = MutableLiveData<ArrayList<String>>()
    var result = MutableLiveData<String>()

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
                listName.postValue(arrayS)
            }
            Route.MELT_CARDS.get -> {
                if(rep.result === false)
                {
                    result.postValue("You have inadvertently dropped your card, your card has become illegible and unusable sorry 🤪")
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

    fun meltCard(position:Int) {
        CallHttpManager(callback = this, action = Route.MELT_CARDS.get, isActivateCallBack = true, idUser = Account.id, idCard = inventory[position].id, url = Config.url).execute()
    }


}
