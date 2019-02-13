package fr.coopuniverse.api.pokeapi.activity.view.viewModel

import androidx.lifecycle.MutableLiveData
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.data.Config
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import fr.coopuniverse.api.pokeapi.activity.enum.Route
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager


object QuizzViewModel : CallBackDisplay {
    var answer = MutableLiveData<String>()
    var response = MutableLiveData<ArrayList<String>>()
    var money = MutableLiveData<String>()
    var info = MutableLiveData<String>()
    var enableButton = MutableLiveData<Boolean>()
    private var hashAnswer = ""


    fun setResponse(numAnswer: Int) {
        CallHttpManager(callback = this, action = Route.SET_ANSWER.get, isActivateCallBack = true, answser = this.hashAnswer, value = response.value!!.get(numAnswer - 1), url = Config.url).execute()
    }

    fun getAnswer() {
        CallHttpManager(callback = this, action = Route.GET_ONE_MONEY.get, isActivateCallBack = true, idUser = Account.id, url = Config.url).execute()
    }

    override fun display(rep: Reponse, action: String) {
        when (action) {
            "GetQuestion" -> {
                this.answer.postValue(rep.data.question[0].toString())
                this.hashAnswer = rep.data.question[2] as String
                var reps: ArrayList<String> = rep.data.question[1] as ArrayList<String>
                response.postValue(reps)

            }
            "GetOneMoney" -> {
                Account.money = (rep.data.money!!.toInt() - 10).toString()
                this.money.postValue("You have" + Account.money + " " + "golds")

                if (Account.money.toInt() >= 0) {

                    info.postValue("")
                    CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = true, idUser = Account.id, value = Account.money, url = Config.url).execute()
                } else {
                    info.postValue("No much money")
                }

            }
            "SetOneMoney" -> {
                if (rep.exitCode == 0) {
                    CallHttpManager(callback = this, action = Route.GET_QUESTION.get, isActivateCallBack = true, url = Config.url).execute()
                } else info.postValue("Error exit code: " + rep.exitCode)

            }
            "SetAnswer" -> {
                this.enableButton.postValue(true)
                if (rep.result == true) {
                    Account.money = (Account.money.toInt() + 30).toString()
                    this.money.postValue("You have" + Account.money + " " + "golds")
                    this.info.postValue("Good Answer you won 30 golds :D")
                    CallHttpManager(callback = this, action = Route.SET_ONE_MONEY.get, isActivateCallBack = false, idUser = Account.id, value = Account.money, url = Config.url).execute()
                } else {
                    info.postValue("Wrong Answer ! Sorry :( ")
                }
            }
        }
    }

}
