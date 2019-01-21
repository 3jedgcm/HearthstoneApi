package fr.coopuniverse.api.pokeapi.activity.Interfaces

import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Card

interface OnListFragmentInteractionListener {

    //  recuperation the clicked comics object from adapter to use it in our ComicsFragmentDetails
    fun clickOnImage(comicsObject: Card)
}
