package fr.coopuniverse.api.pokeapi.activity.data


class Reponse(
              var result: Any? = null,
              var connect: Boolean = false,
              var user: User = User("","","","","",""),
              var exitCode: Int = -1,
              var data: Data = Data(-1))

