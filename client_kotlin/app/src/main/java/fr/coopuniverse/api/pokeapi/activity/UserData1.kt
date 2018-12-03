package fr.coopuniverse.api.pokeapi.activity

class UserData(var idConnexion: String?, var name: String?, var surname: String?) {

    override fun toString(): String {
        return "ID :" + idConnexion + "\n" +
                "Name : " + name + "\n" +
                "Surname : " + surname
    }
}
