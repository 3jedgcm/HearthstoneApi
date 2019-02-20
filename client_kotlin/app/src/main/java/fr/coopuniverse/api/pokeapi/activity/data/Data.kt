package fr.coopuniverse.api.pokeapi.activity.data

 class Data(var user: Int,
           var question: ArrayList<Any> = ArrayList(),
           var money: String? = null,
           var cards: ArrayList<Card> = ArrayList(),
           var inventory:ArrayList<Card> = ArrayList(),
           var parameter: Any? = null)