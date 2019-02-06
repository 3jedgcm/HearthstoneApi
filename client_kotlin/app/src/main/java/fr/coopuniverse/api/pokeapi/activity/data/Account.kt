package fr.coopuniverse.api.pokeapi.activity.data

import android.net.Uri


class Account(
        var name: String? = null,
        var surname: String? = null,
        var user: Any? = null,
        var money: String? = null,
        var id: String? = null,
        var urlPicture: Any? = null,
        var connectWith: String? = null
) {


    fun getUserName(): String? {
        return this.name
    }

    override fun toString(): String {
        return "$name,$surname,$id,$urlPicture,$connectWith"
    }


}