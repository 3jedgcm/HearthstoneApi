package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Data
import fr.coopuniverse.api.pokeapi.activity.data.User

class ResponseGetAllUser : Response {

    val exitCode : Int? = 0
    //     val data : ArrayList<User>? = null
    val data : DataGetAllUser? = null

}
data class DataGetAllUser(val user : ArrayList<User>? = null)