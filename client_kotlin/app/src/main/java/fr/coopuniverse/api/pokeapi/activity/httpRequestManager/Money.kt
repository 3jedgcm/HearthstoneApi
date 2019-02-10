package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

enum class Money(  var type: String, var rarity: String, var valueCard: Int)
{


    HERO_NULL("HERO", "NULL", 250),
    HERO_COMMON("HERO", "COMMON", 25),
    HERO_RARE("HERO", "RARE", 50),
    HERO_EPIC("HERO", "EPIC", 75),
    HERO_LEGENDARY("HERO", "LEGENDARY", 100),

    HERO_FREE("HERO", "FREE", 110),

    HERO_POWER_NULL("HERO_POWER", "", 150),
    HERO_POWER_COMMON("HERO_POWER", "COMMON", 25),
    HERO_POWER_RARE("HERO_POWER", "RARE", 50),
    HERO_POWER_EPIC("HERO_POWER", "EPIC", 75),
    HERO_POWER_LEGENDARY("HERO_POWER", "LEGENDARY", 100),
    HERO_POWER_FREE("HERO_POWER", "FREE", 110),



    MINION_NULL("MINION", "NULL", 250),
    MINION_COMMON("MINION", "COMMON", 25),
    MINION_RARE("MINION", "RARE", 50),
    MINION_EPIC("MINION", "EPIC", 75),
    MINION_LEGENDARY("MINION", "LEGENDARY", 100),
    MINION_FREE("MINION", "FREE", 110),

    SPELL_NULL("SPELL", "NULL", 250),
    SPELL_COMMON("SPELL", "COMMON", 25),
    SPELL_RARE("SPELL", "RARE", 50),
    SPELL_EPIC("SPELL", "EPIC", 75),
    SPELL_LEGENDARY("SPELL", "LEGENDARY", 100),
    SPELL_FREE("SPELL", "FREE", 110),

    ENCHANTMENT_NULL("ENCHANTMENT", "NULL", 260),
    ENCHANTMENT_COMMON("ENCHANTMENT", "COMMON", 25),
    ENCHANTMENT_RARE("ENCHANTMENT", "RARE", 50),
    ENCHANTMENT_EPIC("ENCHANTMENT", "EPIC", 75),
    ENCHANTMENT_LEGENDARY("ENCHANTMENT", "LEGENDARY", 100),
    ENCHANTMENT_FREE("ENCHANTMENT", "FREE", 110),

    WEAPON_NULL("WEAPON", "NULL", 250),
    WEAPON_COMMON("WEAPON", "COMMON", 25),
    WEAPON_RARE("WEAPON", "RARE", 50),
    WEAPON_EPIC("WEAPON", "EPIC", 75),
    WEAPON_LEGENDARY("WEAPON", "LEGENDARY", 100),
    WEAPON_FREE("WEAPON", "FREE", 110);


   fun Money(  type: String,  rarity: String,  valueCard: Int){
        this.type = type
        this.rarity = rarity
        this.valueCard = valueCard
    }
  fun getTypeCard():String {
        return this.type
    }

    fun getNameOfEnum():String {
        return this.name
    }

    fun getRarityCard():String {
        return this.rarity
    }

    fun getValueMoney():Int {
        return this.valueCard
    }
}