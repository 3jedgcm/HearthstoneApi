package fr.coopuniverse.api.pokeapi.activity

import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Reponse

interface CallBackDisplay {
    fun display(rep: Reponse)
}
