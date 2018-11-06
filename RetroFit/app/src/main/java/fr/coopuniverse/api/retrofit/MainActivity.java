package fr.coopuniverse.api.retrofit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private TextView label_tV;
    private TextView type_tV;
    private TextView result_tV;
    private String label;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.label_tV = findViewById(R.id.label_edt);
        this.type_tV = findViewById(R.id.type_edt);
        this.result_tV = findViewById(R.id.result_tv);






        }

        public void onClick(View v){
            switch(v.getId())
            {
                case(R.id.delete_button):
                {
                    this.label = (String) this.label_tV.getText();
                    this.type = (String) this.type_tV.getText();
                    delete();
                    break;
                }
                case(R.id.get_button):
                {
                    this.label = this.label_tV.getText().toString();
                    this.type =  this.type_tV.getText().toString();
                    Log.d("test",type + " " + label);
                    getObject();
                    break;
                }
                case(R.id.post_button):
                {
                    break;
                }

            }
        }

        private void getObject()
        {
        AsyncTask<Void, Void, Void> reponse = new CallBackGenerator().setIdUser("1").setUrl("https://api.coopuniverse.fr").setAction("GetOneMoney").execute();
        //Log.d("reponse",reponse.toString());
        }

    private void delete()
    {


    }

}
