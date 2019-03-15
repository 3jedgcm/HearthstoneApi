package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card

class ResponseGetRandomCard : Response {
    val exitCode : Int? = 0
    val data :  DataGetOneMoney? = null

}
data class DataGetRandomCard(
        val card : Card? = null
)

