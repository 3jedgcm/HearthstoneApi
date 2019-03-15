package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card

class ResponseMeltCards : Response {
    val exitCode : Int? = 0

    val data : DataMeltCards? = null
}


data class DataMeltCards(
        val result : Boolean,
        val name   : String

)
