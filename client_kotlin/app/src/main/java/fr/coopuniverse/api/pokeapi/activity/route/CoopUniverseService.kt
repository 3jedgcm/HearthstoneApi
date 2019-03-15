package fr.coopuniverse.api.pokeapi.activity.route

import fr.coopuniverse.api.pokeapi.activity.data.Response.*
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
    fun GetAllParameter(): Call<ResponseGetAllParameter>
    @GET("money/")
    fun GetAllMoney(): Call<Response>
    @GET("user/")
    fun GetAllUser(): Call<ResponseGetAllUser>
    @GET("card/")
    fun GetAllCard(): Call<ResponseGetAllCard>
    ///////////////////////////
    ////// GET BY USER ID /////
    @GET("money/{idUser}")
    fun GetOneMoney(@Path("idUser") idUser: String): Call<ResponseGetOneMoney>
    @GET("user/{idUser}")
    fun GetOneUser(@Path("idUser") idUser: String): Call<ResponseGetOneUser>
    @GET("inventory/{idUser}")
    fun GetCardByUserId(@Path("idUser") idUser: String): Call<ResponseGetCardByUserId>
    //////////////////////////
    ////// SPECIAL //////////
    @GET("card/random")
    fun GetRandomCard(): Call<ResponseGetRandomCard>
    @GET("other/quizz/")
    fun GetQuestion(): Call<ResponseGetQuestion>
    @GET("card?filter={typeFilter}&value_filter={valueFilter}")
    fun GetCardByFilter(@Path("typeFilter") typeFilter: String,@Path("valueFilter") valueFilter: String): Call<Response>
    /////////////////////////////////////////////////////////////////////
    ////////////////////////////POST////////////////////////////////
    //////// REGISTER ////////
    @FormUrlEncoded
    @POST("register")
    fun SimpleRegister(@Field("login") login: String,@Field("pass") pass: String): Call<ResponseRegister>
    @FormUrlEncoded
    @POST("register/facebook")
    fun FacebookRegister(@Field("key") key: String): Call<ResponseRegisterFacebook>
    @FormUrlEncoded
    @POST("register/google")
    fun GoogleRegister(@Field("key") key: String): Call<ResponseRegisterGoogle>
    //////////////////////////
    //////// CONNECT ////////
    @FormUrlEncoded
    @POST("connect")
    fun SimpleLogin(@Field("login") login: String,@Field("pass") pass: String): Call<ResponseConnect>
    @FormUrlEncoded
    @POST("connect/facebook")
    fun FacebookLogin(@Field("key") key: String): Call<ResponseConnectFacebook>
    @FormUrlEncoded
    @POST("connect/google")
    fun GoogleLogin(@Field("key") key: String): Call<ResponseConnectGoogle>
    //////////////////////////
    //////// SET BY ID USER ////////
    @FormUrlEncoded
    @POST("money/{idUser}")
    fun SetOneMoney(@Path("idUser") idUser: String, @Field("value") money: String): Call<ResponseSetOneMoney>
    @FormUrlEncoded
    @POST("inventory/{idUser}")
    fun SetOneCard(@Path("idUser") idUser: String, @Field("idCard") idCard: String): Call<ResponseSetOneCard>
    //////////////////////////
    ///////// EXCHANGE ////////
    @FormUrlEncoded
    @POST("inventory/exchange/")
    fun ExchangeCards(@Field("idUserOne") idUserOne: String, @Field("idUserTwo") idUserTwo: String, @Field("cardUserOne") cardUserOne: String, @Field("cardUserTwo") cardUserTwo: String): Call<ResponseExchangeCards>
    //////////////////////////
    //////// SPECIAL ////////
    @FormUrlEncoded
    @POST("other/meltcard/{idUser}")
    fun MeltCards(@Path("idUser") idUser: String, @Field("idCard") idCard: String): Call<ResponseMeltCards>
    @FormUrlEncoded
    @POST("other/craftcard/{idUser}")
    fun CraftOneCard(@Path("idUser") idUser: String, @Field("idCardOne") idCardOne: String, @Field("idCardTwo") idCardTwo: String, @Field("idCardThree") idCardThree: String): Call<ResponseCraftOneCard>
    //////////////////////////
    //////// QUESTION ////////
    @FormUrlEncoded
    @POST("other/quizz")
    fun SetAnswer(@Field("answer") answer: String,@Field("question") question: String): Call<ResponseSetAnswer>
}
