package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card

class ResponseGetAllCard {

    val exitCode : Int? = 0

    val data : DataGetAllCard? = null
}


data class DataGetAllCard(
         val cards : ArrayList<Card>
       )