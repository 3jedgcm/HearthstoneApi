package fr.coopuniverse.api.pokeapi.activity.callback

import fr.coopuniverse.api.pokeapi.activity.data.Response.Response

interface CallBackDisplay {
    fun display(abstractRep: Response, action: String)
}
