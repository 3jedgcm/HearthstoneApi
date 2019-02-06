package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

class Data(var user: Int,var question: ArrayList<Any> = ArrayList(), var money: String? = null, var cards: ArrayList<Card> = ArrayList(), var parameter: Any? = null) {

    override fun toString(): String {
        return "Data(question=$question, user=$user, money=$money, cards=$cards, parameter=$parameter)"
    }
}