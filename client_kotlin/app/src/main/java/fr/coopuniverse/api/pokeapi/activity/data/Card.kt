package fr.coopuniverse.api.pokeapi.activity.data

import fr.coopuniverse.api.pokeapi.activity.enums.Money


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
    private val urlArtApi: String = "https://art.hearthstonejson.com/v1/render/latest/frFR/512x/"
    private val imageFormat: String = ".png"

    fun getImage(): String {
        return this.urlArtApi + id + this.imageFormat
    }

    fun getMoneyByTypeCard(): Int {
        var strRarity = "NULL"
        if (this.rarity != null) {
            strRarity = this.rarity.toString()
        }
        var strTypeRarity = (this.type + "_" + strRarity).toUpperCase()
        var moneyType: Money
        try {
            var arrMoney: Array<Money>?
            arrMoney = Money.values()
            var flagExistinEnum: Boolean = false
            for (f: Money in arrMoney) {
                if (f.name.equals(strTypeRarity))
                    flagExistinEnum = true
            }
            if (!flagExistinEnum)
                return money
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