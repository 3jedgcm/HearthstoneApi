package fr.coopuniverse.api.pokeapi.activity;

public class UserData {

    private String idConnexion;
    private String name;
    private String surname;

    public UserData(String idConnexion, String name, String surname) {
        this.idConnexion = idConnexion;
        this.name = name;
        this.surname = surname;
    }

    public String getIdConnexion() {
        return idConnexion;
    }

    public void setIdConnexion(String idConnexion) {
        this.idConnexion = idConnexion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "ID :" + idConnexion + "\n" +
                "Name : " + name + "\n" +
                "Surname : " + surname ;
    }
}
