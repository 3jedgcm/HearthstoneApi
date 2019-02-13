package fr.coopuniverse.api.pokeapi.activity.callback

import fr.coopuniverse.api.pokeapi.activity.enum.Destination

interface CallBackFragment {
    fun setFragment(dest: Destination)
}