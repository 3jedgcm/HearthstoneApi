package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card

class ResponseCraftOneCard: Response  {

    val exitCode : Int? = 0
    val data : DataCraftOneCard? = null
}


data class DataCraftOneCard(
        val result : Boolean,
        val name   : String
)

