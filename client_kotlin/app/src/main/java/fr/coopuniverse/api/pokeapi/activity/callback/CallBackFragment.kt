package fr.coopuniverse.api.pokeapi.activity.callback

import fr.coopuniverse.api.pokeapi.activity.enums.Destination

interface CallBackFragment {
    fun setFragment(dest: Destination)
}