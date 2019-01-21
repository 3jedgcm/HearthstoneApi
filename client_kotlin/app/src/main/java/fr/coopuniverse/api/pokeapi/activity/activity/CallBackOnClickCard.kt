package fr.coopuniverse.api.pokeapi.activity.activity
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Card

interface CallBackOnClickCard {
    fun onClickCard(card: Card)
}