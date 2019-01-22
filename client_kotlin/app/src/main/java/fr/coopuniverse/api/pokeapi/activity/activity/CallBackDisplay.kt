package fr.coopuniverse.api.pokeapi.activity.activity

import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Reponse

interface CallBackDisplay {
    fun display(rep: Reponse,action: String)
}
