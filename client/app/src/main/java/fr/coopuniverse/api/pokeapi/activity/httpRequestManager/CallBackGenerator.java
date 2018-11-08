package fr.coopuniverse.api.pokeapi.activity.httpRequestManager;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fr.coopuniverse.api.pokeapi.activity.InventoryActivity;
import retrofit2.Call;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallBackGenerator extends AsyncTask<Void, Void, Void> {

    public String url;
    private String idUser;
    private String idUserTwo;
    private List<Card> cards;
    private Card cardUserOne;
    private Card cardUserTwo;
    private String answser;
    private String value;
    private Reponse reponse;
    private String action;

    public CallBackGenerator setUrl(String url) {
        this.url = url;
        return this;
    }

    public CallBackGenerator setIdUser(String idUser) {
        this.idUser = idUser;
        return this;
    }

    public CallBackGenerator setIdUserTwo(String idUserTwo) {
        this.idUserTwo = idUserTwo;
        return this;
    }

    public CallBackGenerator setCards(List<Card> cards) {
        this.cards = cards;
        return this;
    }

    public CallBackGenerator setCardUserOne(Card cardUserOne) {
        this.cardUserOne = cardUserOne;
        return this;
    }

    public CallBackGenerator setCardUserTwo(Card cardUserTwo) {
        this.cardUserTwo = cardUserTwo;
        return this;
    }

    public CallBackGenerator setAnswser(String answser) {
        this.answser = answser;
        return this;
    }

    public CallBackGenerator setValue(String value) {
        this.value = value;
        return this;
    }
    public CallBackGenerator setAction(String action) {
        this.action = action;
        return this;
    }

    public void generateCallBack() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        CoopUniverseService service = retrofit.create(CoopUniverseService.class);
        Call<Reponse> rep;
        switch (action)
        {
            case("GetOneMoney"):
                rep = service.GetOneMoney(this.idUser);
                break;
            case("GetAllMoney"):
                rep = service.GetAllMoney();
                break;
            case("SetOneMoney"):
                rep = service.SetOneMoney(this.idUser, this.value);
                break;
            case("SetAllMoney"):
                rep = service.SetAllMoney(this.value);
                break;
            case("GetOneUser"):
                rep = service.GetOneUser(this.idUser);
                break;
            case("SetOneUser"):
                rep = service.SetOneUser(this.idUser,this.value);
                break;
            case("GetAllUser"):
                rep = service.GetAllUser();
                break;
            case("SetAllUser"):
                rep = service.SetAllUser(this.value);
                break;
            case("GetOneCardByUserId"):
                rep = service.GetOneCardByUserId(this.idUser);
                break;
            case("GetAllCard"):
                rep = service.GetAllCard();
                break;
            case("SetOneCardByUserId"):
                rep = service.SetOneCardByUserId(this.idUser,this.cardUserOne.toString());
                break;
            case("DeleteOneCardByUserid"):
                rep = service.DeleteOneCardByUserid(this.idUser);
                break;
            case("ExchangeCards"):
                rep = service.ExchangeCards(this.idUser,this.idUserTwo,this.cardUserOne.toString(),this.cardUserTwo.toString());
                break;
            case("GetRandomCard"):
                rep = service.GetRandomCard(this.idUser);
                break;
            case("MeltCards"):
                rep = service.MeltCards(this.idUser,this.cards.toString());
                break;
            case("CraftOneCard"):
                rep = service.CraftOneCard(this.idUser, this.cards.toString());
                break;
                default:
            case("getQuestion"):
                rep = service.getQuestion(this.idUser);
                break;
            case("setAnswer"):
                rep = service.setAnswer(this.idUser, this.answser);
                break;
        }

        try {
            reponse = rep.execute().body();

        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        generateCallBack();
        return null;
    }

    protected void onPostExecute(Void result)
    {
        if(reponse != null)
            InventoryActivity.respond.setText(reponse.toString());


    }
}
