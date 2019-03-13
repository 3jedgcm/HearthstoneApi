package fr.coopuniverse.api.pokeapi.activity.data.Response

import fr.coopuniverse.api.pokeapi.activity.data.Card

class ResponseSetAnswer {
    val exitCode : Int? = 0
    val data : DataSetAnswer? = null
}

data class DataSetAnswer(

        val result : Boolean? = false
)