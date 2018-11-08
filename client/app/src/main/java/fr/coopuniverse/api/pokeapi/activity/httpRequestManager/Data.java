package fr.coopuniverse.api.pokeapi.activity.httpRequestManager;

public class Data {

    private Object money;
    private int user;

    public Data(int user,Object money) {
        this.money = money;
        this.user = user;
    }

    public Object getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "Money : " + money + " Id User : " + user;
    }

}
