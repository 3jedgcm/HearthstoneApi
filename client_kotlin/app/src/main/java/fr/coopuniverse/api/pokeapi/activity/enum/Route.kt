package fr.coopuniverse.api.pokeapi.activity.enum

enum class Route (var get:String)
{

    GET_ONE_MONEY("GetOneMoney"),
    GET_ALL_MONEY("GetAllMoney"),
    GET_ONE_USER("GetOneUser"),
    GET_ALL_USER("GetAllUser"),
    GET_CARD_BY_USER_ID("GetCardByUserId"),
    GET_ALL_CARD("GetAllCard"),
    GET_RANDOM_CARD("GetRandomCard"),
    GET_ALL_PARAMETER("GetAllParameter"),
    GET_QUESTION("GetQuestion"),
    GET_CARD_BY_FILTER("GetCardByFilter"),
    SET_ONE_CARD("SetOneCard"),
    SET_ONE_MONEY("SetOneMoney"),
    SET_ANSWER("SetAnswer"),
    EXCHANGE_CARDS("ExchangeCards"),
    MELT_CARDS("MeltCards"),
    CRAFT_ONE_CARDS("CraftOneCard"),
    CONNECT("Connect"),
    CONNECT_WITH_FACEBOOK("ConnectFacebook"),
    CONNECT_WITH_GOOGLE("ConnectGoogle"),
    REGISTER("Register"),
    REGISTER_WITH_FACEBOOK("RegisterFacebook"),
    REGISTER_WITH_GOOGLE("RegisterGoogle");

}