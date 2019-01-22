package fr.coopuniverse.api.pokeapi.activity.data

import android.net.Uri


class Account(var name: String?,var surname: String?,var id: String?, var urlPicture: Any?, var connectWith: String?) {


    fun getUserName(): String? {
        return this.name
    }

    override fun toString(): String {
        return "$name,$surname,$id,$urlPicture,$connectWith"
    }


}