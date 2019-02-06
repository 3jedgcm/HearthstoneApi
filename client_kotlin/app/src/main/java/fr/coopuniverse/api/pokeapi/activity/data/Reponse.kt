package fr.coopuniverse.api.pokeapi.activity.data


class Reponse(var id: String, var result: Boolean? = null, var connect: Boolean = false, var user: User? = null, var exitCode: Int = -1, var data: Data = Data(-1)) {


    override fun toString(): String {
        return "Reponse(id='$id', connect=$connect, user=$user, exitCode=$exitCode, data=$data)"
    }
}
