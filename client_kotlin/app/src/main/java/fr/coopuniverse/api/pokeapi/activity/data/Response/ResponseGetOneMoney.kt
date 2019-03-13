package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card

class ResponseGetOneMoney {
    val exitCode : Int? = 0
    val data :  DataGetOneMoney? = null

}
data class DataGetOneMoney(
        val money : MoneyGetOneMoney? = null
)

data class MoneyGetOneMoney(
        val user : String? = null, //: ArrayList<User>? = null,
        val money :String?= null
)
