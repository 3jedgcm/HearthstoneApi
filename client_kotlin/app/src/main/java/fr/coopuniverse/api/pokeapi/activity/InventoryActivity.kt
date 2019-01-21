package fr.coopuniverse.api.pokeapi.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.*
import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.activity.CallBackDisplay


class InventoryActivity : CallBackDisplay,AppCompatActivity() {


    private var profilTV: TextView? = null
    private var idUser: EditText? = null
    private var idPost1: EditText? = null
    private var idPost2: EditText? = null
    private var idPost3: EditText? = null
    private var idPost4: EditText? = null
    private var sendButton: Button? = null
    private var respond: TextView? = null


    private var actionSpinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        this.profilTV = findViewById(R.id.profil)

        this.profilTV!!.text = intent.getStringExtra("PERSONNAL_INFORMATION")
        this.idPost1 = findViewById(R.id.paramPost1)
        this.idPost2 = findViewById(R.id.paramPost2)
        this.idPost3 = findViewById(R.id.paramPost3)
        this.idPost4 = findViewById(R.id.paramPost4)
        this.idUser = findViewById(R.id.idUser)
        this.sendButton = findViewById(R.id.send)
        this.actionSpinner = findViewById(R.id.spinner)
        respond = findViewById(R.id.respond)
        sendButton!!.setOnClickListener {
            // new CallBackGenerator().setUrl("https://api.coopuniverse.fr").setAction(actionSpinner.getSelectedItem().toString()).setIdUser(idUser.getText().toString()).generateCallBack();
            var s = actionSpinner!!.selectedItem.toString()
            CallBackGenerator(callback = this, url ="https://api.coopuniverse.fr", action =s).execute()
        }
    }

    override fun display(rep: Reponse)
    {
        respond!!.text = rep.toString()
    }



}
