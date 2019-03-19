package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import android.os.Handler
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
    var viewInProgress = MutableLiveData<String>()
    private var hashAnswer = ""


    fun setResponse(numAnswer: Int) {
        CallHttpManager(callback = this, action = Route.SET_ANSWER.get, isActivateCallBack = true, answser = this.hashAnswer, value = response.value!!.get(numAnswer - 1), url = Config.url).execute()
    }

    fun getAnswer() {
        CallHttpManager(callback = this, action = Route.GET_ONE_MONEY.get, isActivateCallBack = true, idUserOne = Account.id, url = Config.url).execute()
    }

    override fun display(abstractRep: Response, action: String) {
        var rep: Response
        if(abstractRep != null) {
            when (action) {
                Route.GET_QUESTION.get -> {
                    rep = abstractRep as ResponseGetQuestion
                    this.answer.postValue(rep.data!!.question!![0].toString())
                    this.hashAnswer = rep.data!!.question!![2] as String
                    var reps: ArrayList<String> = rep.data!!.question!![1] as ArrayList<String>
                    response.postValue(reps)
                    enableButton.postValue(false)

                }
                Route.GET_ONE_MONEY.get -> {
                    rep = abstractRep as ResponseGetOneMoney
                    Account.money = (rep.data!!.money!!.money!!.toInt() - Config.costQuizz).toString()

                    if (Account.money.toInt() >= 0) {
                        this.money.postValue(Account.money.toInt())
                        info.postValue(Info.VOID)
                        CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = true, idUserOne = Account.id, value = Account.money, url = Config.url).execute()
                    } else {
                        Account.money = (rep.data!!.money!!.money!!.toInt()).toString()
                        info.postValue(Info.NO_MUCH_MONEY)
                        this.money.postValue(Account.money.toInt())
                        this.enableButton.postValue(true)
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
                        viewInProgress.postValue("win")
                        Handler().postDelayed({
                            Account.money = (Account.money.toInt() + Config.winningQuizz).toString()
                            this.money.postValue(Account.money.toInt())
                            this.info.postValue(Info.RIGHT_ANSWER)
                            CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = false, idUserOne = Account.id, value = Account.money, url = Config.url).execute()
                            viewInProgress.postValue("")
                        }, 3000)

                    } else {

                        viewInProgress.postValue("loose")
                        Handler().postDelayed({
                            info.postValue(Info.WRONG_ANSWER)
                            viewInProgress.postValue("")
                        }, 3000)

                    }
                }
            }
        }
    }
}
