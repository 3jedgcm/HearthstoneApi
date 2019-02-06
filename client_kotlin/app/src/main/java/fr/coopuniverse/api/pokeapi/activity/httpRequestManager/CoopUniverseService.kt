package fr.coopuniverse.api.pokeapi.activity.httpRequestManager

import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import retrofit2.Call

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoopUniverseService {


    ////////////////////////////// GET ////////////////////////////////////
    //////////////////////////
    ////// GET ALL ///////
    @GET("parameter/")
    fun GetAllParameter(): Call<Reponse>
    @GET("money/")
    fun GetAllMoney(): Call<Reponse>
    @GET("user/")
    fun GetAllUser(): Call<Reponse>
    @GET("card/")
    fun GetAllCard(): Call<Reponse>
    ///////////////////////////
    ////// GET BY USER ID /////
    @GET("money/{idUser}")
    fun GetOneMoney(@Path("idUser") idUser: String): Call<Reponse>
    @GET("user/{idUser}")
    fun GetOneUser(@Path("idUser") idUser: String): Call<Reponse>
    @GET("inventory/{idUser}")
    fun GetCardByUserId(@Path("idUser") idUser: String): Call<Reponse>
    //////////////////////////
    ////// SPECIAL //////////
    @GET("card/random")
    fun GetRandomCard(): Call<Reponse>
    @GET("other/quizz/")
    fun GetQuestion(): Call<Reponse>
    @GET("card?filter={typeFilter}&value_filter={valueFilter}")
    fun GetCardByFilter(@Path("typeFilter") typeFilter: String,@Path("valueFilter") valueFilter: String): Call<Reponse>
    /////////////////////////////////////////////////////////////////////
    ////////////////////////////POST////////////////////////////////
    //////// REGISTER ////////
    @FormUrlEncoded
    @POST("register")
    fun SimpleRegister(@Field("login") login: String,@Field("pass") pass: String): Call<Reponse>
    @FormUrlEncoded
    @POST("register/facebook")
    fun FacebookRegister(@Field("key") key: String): Call<Reponse>
    @FormUrlEncoded
    @POST("register/google")
    fun GoogleRegister(@Field("key") key: String): Call<Reponse>
    //////////////////////////
    //////// CONNECT ////////
    @FormUrlEncoded
    @POST("connect")
    fun SimpleLogin(@Field("login") login: String,@Field("pass") pass: String): Call<Reponse>
    @FormUrlEncoded
    @POST("connect/facebook")
    fun FacebookLogin(@Field("key") key: String): Call<Reponse>
    @FormUrlEncoded
    @POST("connect/google")
    fun GoogleLogin(@Field("key") key: String): Call<Reponse>
    //////////////////////////
    //////// SET BY ID USER ////////
    @FormUrlEncoded
    @POST("money/{idUser}")
    fun SetOneMoney(@Path("idUser") idUser: String, @Field("value") money: String): Call<Reponse>
    @FormUrlEncoded
    @POST("inventory/{idUser}")
    fun SetOneCard(@Path("idUser") idUser: String, @Field("idCard") idCard: String): Call<Reponse>
    //////////////////////////
    ///////// EXCHANGE ////////
    @FormUrlEncoded
    @POST("inventory/exchange/")
    fun ExchangeCards(@Field("idUserOne") idUserOne: String, @Field("idUserTwo") idUserTwo: String, @Field("cardUserOne") cardUserOne: String, @Field("cardUserTwo") cardUserTwo: String): Call<Reponse>
    //////////////////////////
    //////// SPECIAL ////////
    @FormUrlEncoded
    @POST("other/meltCard/{idUser}")
    fun MeltCards(@Path("idUser") idUser: String, @Field("idCard") idCard: String): Call<Reponse>
    @FormUrlEncoded
    @POST("other/craftCard/{idUser}")
    fun CraftOneCard(@Path("idUser") idUser: String, @Field("idCardOne") idCardOne: String, @Field("idCardTwo") idCardTwo: String, @Field("idCardThree") idCardThree: String): Call<Reponse>
    //////////////////////////
    //////// QUESTION ////////
    @FormUrlEncoded
    @POST("other/quizz")
    fun SetAnswer(@Field("answer") answer: String,@Field("question") question: String): Call<Reponse>
}
