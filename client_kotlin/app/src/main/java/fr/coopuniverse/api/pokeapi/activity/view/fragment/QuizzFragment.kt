package fr.coopuniverse.api.pokeapi.activity.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.manager.CallHttpManager
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import kotlinx.android.synthetic.main.quizz_fragment.*


class QuizzFragment : androidx.fragment.app.Fragment() , CallBackDisplay {

    private var hashAnswer: String = ""

    override fun display(rep: Reponse, action: String) {
        when (action)
        {
            "GetQuestion" ->
            {
                answer.text = rep.data.question[0].toString()
                this.hashAnswer = rep.data.question[2] as String
                var reps: ArrayList<String> = rep.data.question[1] as ArrayList<String>

                reponse_one.text = reps[0]
                reponse_two.text = reps[1]
                reponse_three.text = reps[2]
                reponse_four.text = reps[3]
            }
            "GetOneMoney" ->
            {
                Account.money = (rep.data.money!!.toInt() - 10).toString()
                currentMoney.text = this.activity?.getString(R.string.you_have) + Account.money + " " + this.activity?.getString(R.string.golds)
                if(Account.money.toInt() >= 0)
                {
                    info.text = ""
                    CallHttpManager(callback = this, action = "SetOneMoney", isActivateCallBack = true, idUser = Account.id, value = Account.money, url = this.activity?.getString(R.string.url)).execute()
                }
                else
                {
                    info.text = "No much money"
                }
            }
            "SetOneMoney" ->
            {
                if(rep.exitCode == 0)
                {
                    CallHttpManager(callback = this, action = "GetQuestion", isActivateCallBack = true, url = this.activity?.getString(R.string.url)).execute()
                }
                else
                {
                    info.text = "Error exit code"
                }
            }
            "SetAnswer" ->
            {
                this.enableButton()
                if(rep.result == true)
                {
                    Account.money = (Account.money.toInt() + 20).toString()
                    currentMoney.text = this.activity?.getString(R.string.you_have) + Account.money + " " + this.activity?.getString(R.string.golds)
                    info.text = this.activity?.getString(R.string.good_answer)
                    CallHttpManager(callback = this, action = "SetOneMoney", isActivateCallBack = false, idUser = Account.id, value = Account.money, url = this.activity?.getString(R.string.url)).execute()
                }
                else
                {
                    info.text = this.activity?.getString(R.string.bad_answer)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.quizz_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.enableButton()
        currentMoney.text = this.activity?.getString(R.string.you_have) + Account.money + " " + this.activity?.getString(R.string.golds)


        went.setOnClickListener {
            this.disableButton()
            CallHttpManager(callback = this, action = "GetOneMoney", isActivateCallBack = true, idUser = Account.id, url = this.activity?.getString(R.string.url)).execute()
        }
        reponse_one.setOnClickListener {
            CallHttpManager(callback = this, action = "SetAnswer", isActivateCallBack = true, answser = this.hashAnswer, value = reponse_one.text.toString(), url = this.activity?.getString(R.string.url)).execute()
        }
        reponse_two.setOnClickListener {
            CallHttpManager(callback = this, action = "SetAnswer", isActivateCallBack = true, answser = this.hashAnswer, value = reponse_two.text.toString(), url = this.activity?.getString(R.string.url)).execute()
        }
        reponse_three.setOnClickListener {
            CallHttpManager(callback = this, action = "SetAnswer", isActivateCallBack = true, answser = this.hashAnswer, value = reponse_three.text.toString(), url = this.activity?.getString(R.string.url)).execute()
        }
        reponse_four.setOnClickListener {
            CallHttpManager(callback = this, action = "SetAnswer", isActivateCallBack = true, answser = this.hashAnswer, value = reponse_four.text.toString(), url = "https://api.coopuniverse.fr/").execute()
        }
    }

    private fun enableButton()
    {
        reponse_one.isEnabled = false
        reponse_two.isEnabled = false
        reponse_three.isEnabled = false
        reponse_four.isEnabled = false
        went.isEnabled = true
    }

    private fun disableButton()
    {
        reponse_one.isEnabled = true
        reponse_two.isEnabled = true
        reponse_three.isEnabled = true
        reponse_four.isEnabled = true
        went.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
