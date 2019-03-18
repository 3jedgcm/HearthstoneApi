package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Response.*
import fr.coopuniverse.api.pokeapi.activity.enums.Info
import fr.coopuniverse.api.pokeapi.activity.enums.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager


object QuizzViewModel : CallBackDisplay {
    var answer = MutableLiveData<String>()
    var response = MutableLiveData<ArrayList<String>>()
    var money = MutableLiveData<Int>()
    var info = MutableLiveData<Info>()
    var enableButton = MutableLiveData<Boolean>()
    private var hashAnswer = ""


    fun setResponse(numAnswer: Int) {
        CallHttpManager(callback = this, action = Route.SET_ANSWER.get, isActivateCallBack = true, answser = this.hashAnswer, value = response.value!!.get(numAnswer - 1), url = Config.url).execute()
    }

    fun getAnswer() {
        CallHttpManager(callback = this, action = Route.GET_ONE_MONEY.get, isActivateCallBack = true, idUserOne = Account.id, url = Config.url).execute()
    }

    override fun display(abstractRep: Response, action: String) {
        var rep: Response

        when (action) {
            Route.GET_QUESTION.get -> {
                rep = abstractRep as ResponseGetQuestion

                this.answer.postValue(rep.data!!.question!![0].toString())
                this.hashAnswer = rep.data!!.question!![2] as String
                var reps: ArrayList<String> = rep.data!!.question!![1] as ArrayList<String>
                response.postValue(reps)
            }
            Route.GET_ONE_MONEY.get -> {
                rep = abstractRep as ResponseGetOneMoney
                Account.money = (rep.data!!.money!!.money!!.toInt() - 10).toString()
                this.money.postValue(Account.money.toInt())
                if (Account.money.toInt() >= 0) {
                    info.postValue(Info.VOID)
                    CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = true, idUserOne = Account.id, value = Account.money, url = Config.url).execute()
                } else {
                    info.postValue(Info.NO_MUCH_MONEY)
                }
            }
            Route.SET_ONE_MONEY.get -> {
                rep = abstractRep as ResponseSetOneMoney
                if (rep.exitCode == 0) {
                    CallHttpManager(callback = this, action = Route.GET_QUESTION.get, isActivateCallBack = true, url = Config.url).execute()
                } else info.postValue(Info.ERROR)

            }
            Route.SET_ANSWER.get -> {
                rep = abstractRep as ResponseSetAnswer
                this.enableButton.postValue(true)
                if (rep.data!!.result == true) {
                    Account.money = (Account.money.toInt() + 30).toString()
                    this.money.postValue(Account.money.toInt())
                    this.info.postValue(Info.RIGHT_ANSWER)
                    CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = false, idUserOne = Account.id, value = Account.money, url = Config.url).execute()
                } else {
                    info.postValue(Info.WRONG_ANSWER)
                }
            }
        }
    }

}
