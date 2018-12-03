package fr.coopuniverse.api.pokeapi.activity.httpRequestManager


class Reponse(var exitCode: Int = -1, var data: Data = Data(-1)) {


    override fun toString(): String {
        return if (data != null)
            "Exit Code : " + exitCode + " Data : " + data!!.toString()
        else
            "Exit Code : $exitCode"
    }
}
