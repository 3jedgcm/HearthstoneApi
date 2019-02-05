package fr.coopuniverse.api.pokeapi.activity.fragment


import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.activity.Destination
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Reponse
import kotlinx.android.synthetic.main.quizz_fragment.*


class QuizzFragment : androidx.fragment.app.Fragment() , CallBackDisplay {

    var id: String? = null;
    var newMoney = 0;
    var hashAnswer: String? = null;

    override fun display(rep: Reponse, action: String) {
        when (action)
        {
            "GetQuestion" ->
            {
                answer.text = rep.data.question[0].toString()
                this.hashAnswer = rep.data.question.get(2) as String
                var reps: ArrayList<String> = rep.data.question.get(1) as ArrayList<String>
                reponse_one.isEnabled = true;
                reponse_two.isEnabled = true;
                reponse_three.isEnabled = true;
                reponse_four.isEnabled = true;
                went.isEnabled = false;
                reponse_one.text = reps[0]
                reponse_two.text = reps[1]
                reponse_three.text = reps[2]
                reponse_four.text = reps[3]
            }
            "GetOneMoney" ->
            {
                Log.d("Chaton","Get one money " +rep.data.money )
                newMoney = rep.data.money!!.toInt() - 10
                currentMoney.text = newMoney.toString();
                if(newMoney >= 0)
                {
                    info.text = ""
                    CallBackGenerator(callback = this,action = "SetOneMoney",isActivateCallBack = true,idUser = this.id,value = this.newMoney.toString(), url = "https://api.coopuniverse.fr/").execute()
                }
                else
                {
                    info.text = "No much money";
                }
            }
            "SetOneMoney" ->
            {
                Log.d("Chaton","Set one money " +rep.exitCode)
                if(rep.exitCode == 0)
                {
                    CallBackGenerator(callback = this,action = "GetQuestion",isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()
                }
                else
                {
                    info.text = "Error exit code";
                }

            }
            "SetAnswer" ->
            {
                reponse_one.isEnabled = false;
                reponse_two.isEnabled = false;
                reponse_three.isEnabled = false;
                reponse_four.isEnabled = false;
                went.isEnabled = true;
                if(rep.result == true)
                {
                    this.newMoney = this.newMoney + 20;
                    currentMoney.text = newMoney.toString();
                    info.text = "Bonne reponse ! Vous gagnez 20 Golds"
                    CallBackGenerator(callback = this,action = "SetOneMoney",isActivateCallBack = false,idUser = this.id,value = this.newMoney.toString(), url = "https://api.coopuniverse.fr/").execute()
                }
                else
                {
                    info.text = "C'etait la mauvaise reponse :)"
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quizz_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reponse_one.isEnabled = false
        reponse_two.isEnabled = false
        reponse_three.isEnabled = false
        reponse_four.isEnabled = false
        went.isEnabled = true;
        this.id = this.getArguments()?.getString("id");
        went.setOnClickListener {
            CallBackGenerator(callback = this,action = "GetOneMoney",isActivateCallBack = true,idUser = this.id, url = "https://api.coopuniverse.fr/").execute()
        }
        reponse_one.setOnClickListener {
            CallBackGenerator(callback = this,action = "SetAnswer",isActivateCallBack = true,answser = this.hashAnswer,value = reponse_one.text.toString(), url = "https://api.coopuniverse.fr/").execute()
        }
        reponse_two.setOnClickListener {
            CallBackGenerator(callback = this,action = "SetAnswer",isActivateCallBack = true,answser = this.hashAnswer,value = reponse_two.text.toString(), url = "https://api.coopuniverse.fr/").execute()
        }
        reponse_three.setOnClickListener {
            CallBackGenerator(callback = this,action = "SetAnswer",isActivateCallBack = true,answser = this.hashAnswer,value = reponse_three.text.toString(), url = "https://api.coopuniverse.fr/").execute()
        }
        reponse_four.setOnClickListener {
            CallBackGenerator(callback = this,action = "SetAnswer",isActivateCallBack = true,answser = this.hashAnswer,value = reponse_four.text.toString(), url = "https://api.coopuniverse.fr/").execute()
        }
    }


}
