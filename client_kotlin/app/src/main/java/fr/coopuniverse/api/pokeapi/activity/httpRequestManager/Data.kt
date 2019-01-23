package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

class Data(var user: Int,var question: ArrayList<Any> = ArrayList(), var money: Any? = null, var cards: ArrayList<Card> = ArrayList(), var parameter: Any? = null) {


    fun toStringCard(): String {
        return cards.toString()
    }

    override fun toString(): String {
        return "Data(question=$question, user=$user, money=$money, cards=$cards, parameter=$parameter)"
    }
}