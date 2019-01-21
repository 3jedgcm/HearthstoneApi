package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

import java.util.HashMap


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

    private val additionalProperties = HashMap<String, Any>()


    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }


    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }


    fun getImage():String {

        var url: String = "https://art.hearthstonejson.com/v1/render/latest/frFR/512x/"
        this.image = url + id.toString()
        return image as String
    }



}


/*

name, "id", "cost"

  "cardClass": "SHAMAN",
                "id": "CS2_038",
                "name": "Esprit ancestral",
                "playerClass": "SHAMAN",
                 "health": 10,
                 "attack": 10,
                  "text": "Ne peut pas avoir moins de 1 PV pendant ce tour.",
                  type
                  "rarity"

 */