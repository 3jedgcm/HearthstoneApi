package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import fr.coopuniverse.api.pokeapi.activity.enum.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager

object MainActivityViewModel : CallBackDisplay {
    var simpleSignInStateButton = MutableLiveData<Boolean>()
    var infoError = MutableLiveData<String>()
    var changeActivity = MutableLiveData<Boolean>()



    override fun display(rep: Reponse, action: String) {
        simpleSignInStateButton.postValue(true)

        if (!rep.connect) {
            when (action) {
                "ConnectFacebook" -> {
                    CallHttpManager(callback = this, action = Route.REGISTER_WITH_FACEBOOK.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
                }
                "ConnectGoogle" -> {
                    CallHttpManager(callback = this, action = Route.REGISTER_WITH_GOOGLE.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
                }
                "RegisterGoogle" -> {
                    CallHttpManager(callback = this, action = Route.CONNECT_WITH_GOOGLE.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
                }
                "RegisterFacebook" -> {
                    CallHttpManager(callback = this, action = Route.CONNECT_WITH_FACEBOOK.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
                }
                "Connect" -> {
                    infoError.postValue("Mauvais login ou mot de passe")
                }
            }
        } else {
            Account.id = rep.user.IdUser
            Account.money = rep.user.Money
            this.changeActivity.postValue(true)
        }
    }

    fun signIn()
    {
        when (Account.connectWith) {
            "Facebook" -> {
                CallHttpManager(callback = this, action = Route.CONNECT_WITH_FACEBOOK.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
            }
            "Google" -> {
                CallHttpManager(callback = this, action = Route.CONNECT_WITH_GOOGLE.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
            }
            "Simple" -> {
                CallHttpManager(callback = this, action = Route.CONNECT.get, isActivateCallBack = true, login = Account.name, pass = Account.password, url = Config.url).execute()
            }
        }
    }


}