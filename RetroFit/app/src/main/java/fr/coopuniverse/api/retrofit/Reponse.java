package fr.coopuniverse.api.retrofit;

import org.json.JSONObject;

public class Reponse {
    private int exitCode;
    private Data data;


    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    @Override
    public String toString()
    {
        if(data != null)
            return "Exit Code : "+ exitCode + " Data : " + data.toString();
        else
            return "Exit Code : "+ exitCode;
    }
}
