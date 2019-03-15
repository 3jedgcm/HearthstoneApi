package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.User

class ResponseConnectGoogle: Response  {


    val exitCode : Int? = 0
    //     val data : ArrayList<User>? = null
    val data : DataConnectGoogle? = null

}

data class DataConnectGoogle(
        val connect : Boolean,
        val user :User? = null//: ArrayList<User>? = null)
)