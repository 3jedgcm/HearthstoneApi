package fr.coopuniverse.api.pokeapi.activity.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackDisplay
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Reponse


class QuizzFragment : androidx.fragment.app.Fragment() , CallBackDisplay {
    override fun display(rep: Reponse, action: String) {
        Log.d("Chaton",rep.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quizz_fragment, container, false)
    }

    fun onClick(v:View)
    {
        CallBackGenerator(callback = this,action = "GetQuestion",isActivateCallBack = true, url = "https://api.coopuniverse.fr/").execute()
    }


}
