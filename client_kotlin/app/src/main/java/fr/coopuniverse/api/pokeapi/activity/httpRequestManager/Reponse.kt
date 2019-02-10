package fr.coopuniverse.api.pokeapi.activity.httpRequestManager


class Reponse(var id: String, var connect: Boolean = false, var exitCode: Int = -1, var data: Data = Data(-1)) {


    override fun toString(): String {
        return if (data != null)
            "Connect : " + connect + " Exit Code : " + exitCode + " Data : " + data!!.toString()
        else
            "Exit Code : $exitCode"
    }
}
