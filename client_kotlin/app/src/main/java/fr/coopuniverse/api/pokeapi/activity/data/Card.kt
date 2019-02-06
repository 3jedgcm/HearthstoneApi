package fr.coopuniverse.api.pokeapi.activity.data

class Card {
    var name: String? = null
    var id: String? = null
    var cardClass: String? = null
    var cost: Int = 0
    var playerClass: String? = null
    var health: Int = 0
    var attack: Int = 0
    var text: String? = null
    var type: String? = null
    var rarity: String? = null
    private var image:String? = null

    fun getImage():String {

        var url = "https://art.hearthstonejson.com/v1/render/latest/frFR/512x/"
        this.image = url + id + ".png"
        return image as String
    }

    override fun toString(): String {
        return "Card(name=$name, id=$id, cardClass=$cardClass, cost=$cost, playerClass=$playerClass, health=$health, attack=$attack, text=$text, type=$type, rarity=$rarity, image=$image)"
    }

}
