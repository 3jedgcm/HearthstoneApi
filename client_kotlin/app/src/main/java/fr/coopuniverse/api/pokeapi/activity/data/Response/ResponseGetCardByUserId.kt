package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.User


class ResponseGetCardByUserId {

    val exitCode : Int? = 0
    val data :DataGetCardByUserId ? = null

}
data class DataGetCardByUserId(
        val user : String? = null, //: ArrayList<User>? = null,
        val inventory : ArrayList<Card>
)
