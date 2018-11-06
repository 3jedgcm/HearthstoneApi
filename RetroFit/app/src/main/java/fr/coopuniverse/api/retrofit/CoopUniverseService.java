package fr.coopuniverse.api.retrofit;

import retrofit2.Call;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CoopUniverseService
{
    /* Money */

    //GetOneMoney
    @GET("money/{idUser}")
    Call<Reponse> GetOneMoney(@Path("idUser") String idUser);
    //GetAllMoney
    @GET("money/")
    Call<Reponse> GetAllMoney();
    //SetOneMoney
    @FormUrlEncoded
    @POST("money/{idUser}")
    Call<Reponse> SetOneMoney(@Path("idUser") String idUser,@Field("value") String money);
    //SetAllMoney
    @FormUrlEncoded
    @POST("money/")
    Call<Reponse> SetAllMoney(@Field("value") String money); //Manque param post

    /* User */

    //GetOneUser
    @GET("user/{idUser}")
    Call<Reponse> GetOneUser(@Path("idUser") String idUser);
    //SetOneUser
    @FormUrlEncoded
    @POST("user/{idUser}")
    Call<Reponse> SetOneUser(@Path("idUser") String idUser,@Field("value") String value);
    //GetAllUser
    @GET("user/")
    Call<Reponse> GetAllUser();
    //SetAllUser ## deprecated
    @FormUrlEncoded
    @POST("user/")
    Call<Reponse> SetAllUser(@Field("value") String value);

    /* Card */

    //GetOneCardByUserId
    @GET("card/{idUser}")
    Call<Reponse> GetOneCardByUserId(@Path("idUser") String idUser);
    //GetAllCard
    @GET("card/")
    Call<Reponse> GetAllCard();
    //SetOneCardByUserId
    @FormUrlEncoded
    @POST("card/{idUser}")
    Call<Reponse> SetOneCardByUserId(@Path("idUser") String idUser,@Field("cards") String cards);
    //DeleteOneCardByUserid
    @DELETE("card/{idUser}")
    Call<Reponse> DeleteOneCardByUserid(@Path("idUser") String idUser);
    //ExchangeCards
    @FormUrlEncoded
    @POST("card/exchange/")
    Call<Reponse> ExchangeCards(@Field("idUserOne") String idUserOne,@Field("idUserTwo") String idUserTwo,@Field("cardUserOne") String cardUserOne,@Field("cardUserTwo") String cardUserTwo);
    //GetRandomCard
    @GET("card/random/{idUser}")
    Call<Reponse> GetRandomCard(@Path("idUser") String idUser);

    /* Other */
    //MeltCards
    @FormUrlEncoded
    @POST("other/melteCard/{idUser}")
    Call<Reponse> MeltCards(@Path("idUser") String idUser,@Field("cards") String cards);
    //CraftOneCard
    @FormUrlEncoded
    @POST("other/craftCard/{idUser}")
    Call<Reponse> CraftOneCard(@Path("idUser") String idUser,@Field("cards") String cards);
    //GetQuestion
    @GET("other/quizz/{idUser}")
    Call<Reponse> getQuestion(@Path("idUser") String idUser);
    //SetQuestion
    @FormUrlEncoded
    @POST("other/quizz/{idUser}")
    Call<Reponse> setAnswer(@Path("idUser") String idUser,@Field("answer") String answer);

    @GET("{action}/{idUser}")
    Call<Reponse> getAction(@Path("idUser") String idUser,@Path("action") String action);

}
