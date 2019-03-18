package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Response.*
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager

object MainActivityViewModel : CallBackDisplay {
    var simpleSignInStateButton = MutableLiveData<Boolean>()
    var infoError = MutableLiveData<String>()
    var changeActivity = MutableLiveData<Boolean>()



    override fun display(abstractRep: Response, action: String) {
        simpleSignInStateButton.postValue(true)
        var rep: Response
        var connected = false
        when (action)
        {
            Route.CONNECT_WITH_FACEBOOK.get -> {
                rep  = abstractRep as ResponseConnectFacebook
                connected = rep.data!!.connect
            }
            Route.CONNECT_WITH_GOOGLE.get -> {
                rep  = abstractRep as ResponseConnectGoogle
                connected = rep.data!!.connect
            }
            Route.CONNECT.get -> {
                rep  = abstractRep as ResponseConnect
                connected = rep.data!!.connect
            }
        }

        if (!connected) {
            when (action) {
                Route.CONNECT_WITH_FACEBOOK.get -> {
                    CallHttpManager(callback = this, action = Route.REGISTER_WITH_FACEBOOK.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
                }
                Route.CONNECT_WITH_GOOGLE.get -> {
                    CallHttpManager(callback = this, action = Route.REGISTER_WITH_GOOGLE.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
                }
                Route.REGISTER_WITH_GOOGLE.get -> {
                    CallHttpManager(callback = this, action = Route.CONNECT_WITH_GOOGLE.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
                }
                Route.CONNECT_WITH_FACEBOOK.get -> {
                    CallHttpManager(callback = this, action = Route.CONNECT_WITH_FACEBOOK.get, isActivateCallBack = true, key = Account.key, url = Config.url).execute()
                }
                Route.CONNECT.get -> {
                    infoError.postValue("Mauvais login ou mot de passe")
                }
            }
        } else {

            when (action)
            {
                Route.CONNECT_WITH_FACEBOOK.get -> {
                    rep  = abstractRep as ResponseConnectFacebook
                    Account.id = rep.data!!.user!!.IdUser
                    Account.money = rep.data!!.user!!.Money
                    Account.user = rep.data!!.user  //EBE add
                }
                Route.CONNECT_WITH_GOOGLE.get -> {
                    rep  = abstractRep as ResponseConnectGoogle
                    Account.id = rep.data!!.user!!.IdUser
                    Account.money = rep.data!!.user!!.Money
                    Account.user = rep.data!!.user  //EBE add
                }
                Route.CONNECT.get -> {
                    rep  = abstractRep as ResponseConnect
                    Account.id = rep.data!!.user!!.IdUser
                    Account.money = rep.data!!.user!!.Money
                    Account.user = rep.data!!.user  //EBE add
                }
            }


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