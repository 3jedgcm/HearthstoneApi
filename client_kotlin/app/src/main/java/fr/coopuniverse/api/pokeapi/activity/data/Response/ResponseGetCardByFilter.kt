package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card

class ResponseGetCardByFilter: Response  {
    val exitCode : Int? = 0

    val data : DataGetCardByFilter? = null
}


data class DataGetCardByFilter(
        val cards : ArrayList<Card>
)