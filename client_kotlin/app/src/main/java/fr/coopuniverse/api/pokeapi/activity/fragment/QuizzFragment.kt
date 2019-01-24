package fr.coopuniverse.api.pokeapi.activity.fragment


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
    override fun display(rep: Reponse, action: String) {

        answer.text = rep.data.question[0].toString()
        var reps: ArrayList<String> = rep.data.question.get(1) as ArrayList<String>
        reponse_one.isClickable = true;
        reponse_two.isClickable = true;
        reponse_three.isClickable = true;
        reponse_four.isClickable = true;
        reponse_one.text = reps[0]
        reponse_two.text = reps[1]
        reponse_three.text = reps[2]
        reponse_four.text = reps[3]

        Log.d("Chaton",rep.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quizz_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reponse_one.isClickable = false
        reponse_two.isClickable = false
        reponse_three.isClickable = false
        reponse_four.isClickable = false
        went.setOnClickListener {
            CallBackGenerator(callback = this,action = "GetQuestion",isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()
        }
    }


}
