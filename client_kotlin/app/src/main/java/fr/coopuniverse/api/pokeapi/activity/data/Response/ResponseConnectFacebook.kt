
import fr.coopuniverse.api.pokeapi.activity.data.Response.Response
import fr.coopuniverse.api.pokeapi.activity.data.User

class ResponseConnectFacebook : Response {

    val exitCode : Int? = 0
    //     val data : ArrayList<User>? = null
    val data : DataConnectFacebook? = null

}


data class DataConnectFacebook(
        val connect : Boolean,
        val user    : User? = null//: ArrayList<User>? = null)
)



/*
   "data": {
       "connect": false,
       "user": {}
   }
 */