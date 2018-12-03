package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

import retrofit2.Call

import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoopUniverseService {

    /* Param */

    @GET("parameter/")
    fun GetAllParameter(): Call<Reponse>
    /* Money */

    //GetOneMoney
    @GET("money/{idUser}")
    fun GetOneMoney(@Path("idUser") idUser: String): Call<Reponse>

    //GetAllMoney
    @GET("money/")
    fun GetAllMoney(): Call<Reponse>

    //SetOneMoney
    @FormUrlEncoded
    @POST("money/{idUser}")
    fun SetOneMoney(@Path("idUser") idUser: String, @Field("value") money: String): Call<Reponse>

    //SetAllMoney
    @FormUrlEncoded
    @POST("money/")
    fun SetAllMoney(@Field("value") money: String): Call<Reponse>  //Manque param post

    /* User */

    //GetOneUser
    @GET("user/{idUser}")
    fun GetOneUser(@Path("idUser") idUser: String): Call<Reponse>

    //SetOneUser
    @FormUrlEncoded
    @POST("user/{idUser}")
    fun SetOneUser(@Path("idUser") idUser: String, @Field("value") value: String): Call<Reponse>

    //GetAllUser
    @GET("user/")
    fun GetAllUser(): Call<Reponse>

    //SetAllUser ## deprecated
    @FormUrlEncoded
    @POST("user/")
    fun SetAllUser(@Field("value") value: String): Call<Reponse>

    /* Card */

    //GetOneCardByUserId
    @GET("card/{idUser}")
    fun GetOneCardByUserId(@Path("idUser") idUser: String): Call<Reponse>

    //GetAllCard
    @GET("card/")
    fun GetAllCard(): Call<Reponse>

    //SetOneCardByUserId
    @FormUrlEncoded
    @POST("card/{idUser}")
    fun SetOneCardByUserId(@Path("idUser") idUser: String, @Field("cards") cards: String): Call<Reponse>

    //DeleteOneCardByUserid
    @DELETE("card/{idUser}")
    fun DeleteOneCardByUserid(@Path("idUser") idUser: String): Call<Reponse>

    //ExchangeCards
    @FormUrlEncoded
    @POST("card/exchange/")
    fun ExchangeCards(@Field("idUserOne") idUserOne: String, @Field("idUserTwo") idUserTwo: String, @Field("cardUserOne") cardUserOne: String, @Field("cardUserTwo") cardUserTwo: String): Call<Reponse>

    //GetRandomCard
    @GET("card/random/{idUser}")
    fun GetRandomCard(@Path("idUser") idUser: String): Call<Reponse>

    /* Other */
    //MeltCards
    @FormUrlEncoded
    @POST("other/melteCard/{idUser}")
    fun MeltCards(@Path("idUser") idUser: String, @Field("cards") cards: String): Call<Reponse>

    //CraftOneCard
    @FormUrlEncoded
    @POST("other/craftCard/{idUser}")
    fun CraftOneCard(@Path("idUser") idUser: String, @Field("cards") cards: String): Call<Reponse>

    //GetQuestion
    @GET("other/quizz/{idUser}")
    fun getQuestion(@Path("idUser") idUser: String): Call<Reponse>

    //SetQuestion
    @FormUrlEncoded
    @POST("other/quizz/{idUser}")
    fun setAnswer(@Path("idUser") idUser: String, @Field("answer") answer: String): Call<Reponse>

    //register
    @FormUrlEncoded
    @POST("register/")
    fun register(@Path("pass") pass: String, @Field("login") login: String): Call<Reponse>


    //register facebook
    @FormUrlEncoded
    @POST("register/facebook")
    fun registerFacebook(@Path("key") key: String): Call<Reponse>

    //register google
    @FormUrlEncoded
    @POST("register/google")
    fun registerGoogle(@Path("key") key: String): Call<Reponse>


    //Connect
    @FormUrlEncoded
    @POST("connect/")
    fun connect(@Path("pass") pass: String, @Field("login") login: String): Call<Reponse>

    @FormUrlEncoded
    @POST("connect/facebook")
    fun connectFacebook(@Path("key") key: String): Call<Reponse>

    @FormUrlEncoded
    @POST("connect/google")
    fun connectGoogle(@Path("key") key: String): Call<Reponse>




    @GET("{action}/{idUser}")
    fun getAction(@Path("idUser") idUser: String, @Path("action") action: String): Call<Reponse>

}
