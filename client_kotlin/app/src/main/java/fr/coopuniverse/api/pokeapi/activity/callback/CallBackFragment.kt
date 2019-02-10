package fr.coopuniverse.api.pokeapi.activity.callback

import fr.coopuniverse.api.pokeapi.activity.activity.Destination

interface CallBackFragment {
    fun setFragment(dest: Destination)
}