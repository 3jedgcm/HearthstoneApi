package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card
import fr.coopuniverse.api.pokeapi.activity.data.User


class ResponseGetCardByUserId : Response {

    val exitCode : Int? = 0
    val data :DataGetCardByUserId ? = null

}
data class DataGetCardByUserId(
        val inventory: InventoryGetCardByUserId
)

data class InventoryGetCardByUserId(
        val user : String? = null, //EBE TODO to change to userId? to be more explicite?
        val inventory : ArrayList<Card>
)