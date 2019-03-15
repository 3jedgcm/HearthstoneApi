package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.User

class ResponseConnect: Response  {

    val exitCode : Int? = 0
    //     val data : ArrayList<User>? = null
    val data : DataConnect? = null
}

data class DataConnect(
        val connect : Boolean,
        val user :User? = null //: ArrayList<User>? = null)
)