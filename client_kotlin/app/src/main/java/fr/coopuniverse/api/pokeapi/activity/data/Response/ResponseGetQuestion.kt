package fr.coopuniverse.api.pokeapi.activity.data.Response

class ResponseGetQuestion : Response {
    val exitCode : Int? = 0
    val data :  DataGetQuestion? = null

}
data class DataGetQuestion(
        val question:  ArrayList<Any>? =null

)


