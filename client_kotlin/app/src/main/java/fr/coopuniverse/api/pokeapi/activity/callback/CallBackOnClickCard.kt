package fr.coopuniverse.api.pokeapi.activity.callback

interface CallBackOnClickCard {
    fun onClickCard(cardId: String,cardCost:Int,cardCostStr:String )
}