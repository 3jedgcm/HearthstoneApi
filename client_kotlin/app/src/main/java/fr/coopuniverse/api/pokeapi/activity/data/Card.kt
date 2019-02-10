package fr.coopuniverse.api.pokeapi.activity.data

import android.util.Log
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Money
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
    var type: String? = null
    var rarity: String? = null
    var money: Int = 0
    private var image: String? = null

    fun getImage(): String {
        var url: String = "https://art.hearthstonejson.com/v1/render/latest/frFR/512x/"
        this.image = url + id + ".png"
        return image as String
    }

    fun getMoneyByTypeCard(): Int {
        var strRarity = "NULL"
        if (this.rarity != null) {
            strRarity = this.rarity.toString()
        }
        var strTypeRarity = (this.type + "_" + strRarity).toUpperCase()
        var moneyTypeInt: Int
        var moneyType: Money
        try {

            //verif if Enum exists
            var arrMoney: Array<Money>? = null
            arrMoney = Money.values()
            var flagExistinEnum: Boolean = false
            for (f: Money in arrMoney) {

                if (f.name.equals(strTypeRarity)) {
                    flagExistinEnum = true
                }

            }
            if (!flagExistinEnum) {
                return money
            }

            moneyTypeInt = Money.valueOf(strTypeRarity).getValueMoney();
            moneyType = Money.valueOf(strTypeRarity)
        } catch (e: EnumConstantNotPresentException) {
            return money
        }

        if (this.type != null && moneyType != null)
            money = moneyType.getValueMoney()


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