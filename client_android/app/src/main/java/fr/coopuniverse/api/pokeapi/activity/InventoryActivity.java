package fr.coopuniverse.api.pokeapi.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.*;
import fr.coopuniverse.api.pokeapi.R;

public class InventoryActivity extends AppCompatActivity {
    private TextView profilTV;
    private EditText idUser;
    private EditText idPost1;
    private EditText idPost2;
    private EditText idPost3;
    private EditText idPost4;
    private Button sendButton;
    private Spinner actionSpinner;
    public static TextView respond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        this.profilTV = findViewById(R.id.profil);

        this.profilTV.setText(getIntent().getStringExtra("PERSONNAL_INFORMATION"));
        this.idPost1 = findViewById(R.id.paramPost1);
        this.idPost2 = findViewById(R.id.paramPost2);
        this.idPost3 = findViewById(R.id.paramPost3);
        this.idPost4 = findViewById(R.id.paramPost4);
        this.idUser = findViewById(R.id.idUser);
        this.sendButton = findViewById(R.id.send);
        this.actionSpinner = findViewById(R.id.spinner);
        respond = findViewById(R.id.respond);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                       // new CallBackGenerator().setUrl("https://api.coopuniverse.fr").setAction(actionSpinner.getSelectedItem().toString()).setIdUser(idUser.getText().toString()).generateCallBack();

                new CallBackGenerator().setUrl("https://api.coopuniverse.fr").setAction(actionSpinner.getSelectedItem().toString()).setIdUser(idUser.getText().toString()).setValue(idPost1.getText().toString()).execute();

            }
        });
    }

}
