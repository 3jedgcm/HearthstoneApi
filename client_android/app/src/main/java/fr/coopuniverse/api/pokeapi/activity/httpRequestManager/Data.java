package fr.coopuniverse.api.pokeapi.activity.httpRequestManager;

public class Data {

    private Object money;
    private int user;
    private Object parameter;

    public Data(int user,Object money,Object parameter) {
        this.money = money;
        this.user = user;
        this.parameter = parameter;
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

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "Money : " + money + " Id User : " + user + " Param :" + parameter;
    }

}
