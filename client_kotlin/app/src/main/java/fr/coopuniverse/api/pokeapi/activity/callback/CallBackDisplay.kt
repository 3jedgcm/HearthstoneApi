package fr.coopuniverse.api.pokeapi.activity.activity

import fr.coopuniverse.api.pokeapi.activity.data.Reponse

interface CallBackDisplay {
    fun display(rep: Reponse, action: String)
}