package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

import android.util.Log
import java.util.*


class Card {


    var name: String? = null
    var id: String? = null
    var cardClass: String? = null
    var cost: Int = 0
    var playerClass: String? = null
    var health: Int = 0
    var attack: Int = 0
    var text: String? = null
    var type: String? = null //important  pour classification de prix
    var rarity: String? = null //pour classification de prix
    var money: String? = null

    private var image: String? = null

    private val additionalProperties = HashMap<String, Any>()


    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }


    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties[name] = value
    }


    fun getImage(): String {

        var url: String = "https://art.hearthstonejson.com/v1/render/latest/frFR/512x/"
        this.image = url + id + ".png"
        return image as String
    }

    fun getMoneyByTypeCard(): Int {

        var money: Int = 0
        var strRarity = "NULL"
        if (this.rarity != null) {
            strRarity = this.rarity.toString()
        }

        var strTypeRarity = (this.type + "_" + strRarity).toString().toUpperCase()

        Log.d("Moneytype", this.type + "_" + this.rarity)

        var moneyTypeInt = Money.valueOf(strTypeRarity).getValueMoney();

        var moneyType = Money.valueOf(strTypeRarity)


        Log.d("Moneytype2", moneyType.getRarityCard() + "/ " + moneyType.getTypeCard())
        if (this.type != null && moneyType != null) {
            money = moneyType.getValueMoney()
        }

        return money

    }

    override fun toString(): String {
        return "Card(name=$name, id=$id, cardClass=$cardClass, cost=$cost, playerClass=$playerClass, health=$health, attack=$attack, text=$text, type=$type, rarity=$rarity)"
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