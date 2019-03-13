package fr.coopuniverse.api.pokeapi.activity.route

import fr.coopuniverse.api.pokeapi.activity.data.Response.Response
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
    fun GetAllParameter(): Call<Response>
    @GET("money/")
    fun GetAllMoney(): Call<Response>
    @GET("user/")
    fun GetAllUser(): Call<Response>
    @GET("card/")
    fun GetAllCard(): Call<Response>
    ///////////////////////////
    ////// GET BY USER ID /////
    @GET("money/{idUser}")
    fun GetOneMoney(@Path("idUser") idUser: String): Call<Response>
    @GET("user/{idUser}")
    fun GetOneUser(@Path("idUser") idUser: String): Call<Response>
    @GET("inventory/{idUser}")
    fun GetCardByUserId(@Path("idUser") idUser: String): Call<Response>
    //////////////////////////
    ////// SPECIAL //////////
    @GET("card/random")
    fun GetRandomCard(): Call<Response>
    @GET("other/quizz/")
    fun GetQuestion(): Call<Response>
    @GET("card?filter={typeFilter}&value_filter={valueFilter}")
    fun GetCardByFilter(@Path("typeFilter") typeFilter: String,@Path("valueFilter") valueFilter: String): Call<Response>
    /////////////////////////////////////////////////////////////////////
    ////////////////////////////POST////////////////////////////////
    //////// REGISTER ////////
    @FormUrlEncoded
    @POST("register")
    fun SimpleRegister(@Field("login") login: String,@Field("pass") pass: String): Call<Response>
    @FormUrlEncoded
    @POST("register/facebook")
    fun FacebookRegister(@Field("key") key: String): Call<Response>
    @FormUrlEncoded
    @POST("register/google")
    fun GoogleRegister(@Field("key") key: String): Call<Response>
    //////////////////////////
    //////// CONNECT ////////
    @FormUrlEncoded
    @POST("connect")
    fun SimpleLogin(@Field("login") login: String,@Field("pass") pass: String): Call<Response>
    @FormUrlEncoded
    @POST("connect/facebook")
    fun FacebookLogin(@Field("key") key: String): Call<Response>
    @FormUrlEncoded
    @POST("connect/google")
    fun GoogleLogin(@Field("key") key: String): Call<Response>
    //////////////////////////
    //////// SET BY ID USER ////////
    @FormUrlEncoded
    @POST("money/{idUser}")
    fun SetOneMoney(@Path("idUser") idUser: String, @Field("value") money: String): Call<Response>
    @FormUrlEncoded
    @POST("inventory/{idUser}")
    fun SetOneCard(@Path("idUser") idUser: String, @Field("idCard") idCard: String): Call<Response>
    //////////////////////////
    ///////// EXCHANGE ////////
    @FormUrlEncoded
    @POST("inventory/exchange/")
    fun ExchangeCards(@Field("idUserOne") idUserOne: String, @Field("idUserTwo") idUserTwo: String, @Field("cardUserOne") cardUserOne: String, @Field("cardUserTwo") cardUserTwo: String): Call<Response>
    //////////////////////////
    //////// SPECIAL ////////
    @FormUrlEncoded
    @POST("other/meltcard/{idUser}")
    fun MeltCards(@Path("idUser") idUser: String, @Field("idCard") idCard: String): Call<Response>
    @FormUrlEncoded
    @POST("other/craftcard/{idUser}")
    fun CraftOneCard(@Path("idUser") idUser: String, @Field("idCardOne") idCardOne: String, @Field("idCardTwo") idCardTwo: String, @Field("idCardThree") idCardThree: String): Call<Response>
    //////////////////////////
    //////// QUESTION ////////
    @FormUrlEncoded
    @POST("other/quizz")
    fun SetAnswer(@Field("answer") answer: String,@Field("question") question: String): Call<Response>
}
