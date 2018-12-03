package fr.coopuniverse.api.pokeapi.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.shobhitpuri.custombuttons.GoogleSignInButton

import org.json.JSONException

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator

class MainActivity : AppCompatActivity() {


    lateinit var callbackManager: CallbackManager
    lateinit var loginButton: LoginButton
    var accessToken: AccessToken? = null
    var id: TextView? = null
    var name: TextView? = null
    var mail: TextView? = null
    var isLoggedIn: Boolean = false
    var test: Button? = null
    var connectButton: Button? = null
    var login: EditText? = null
    var pass: EditText? = null
    lateinit var disconnect: Button
    lateinit var signInButton: GoogleSignInButton
    lateinit var mGoogleSignInClient: GoogleSignInClient
    var imageView: ImageView? = null


    //https://cdn.shopify.com/s/files/1/0441/9405/files/Pokemon_Moon_phone_wallpaper_by_Trinket_Geek_large.jpg?v=1481926584
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Google connexion
        signInButton = findViewById<View>(R.id.sign_in_button) as GoogleSignInButton
        disconnect = findViewById(R.id.button2)
        connectButton = findViewById(R.id.connect)
        loginButton = findViewById(R.id.login_button)
        login = findViewById(R.id.login)
        pass = findViewById(R.id.pass)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        disconnect.isEnabled = false
        signInButton.setOnClickListener { signIn() }

        connectButton!!.setOnClickListener {
            CallBackGenerator(callback = InventoryActivity(), login = login.text.toString())

        }

        disconnect.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener(this@MainActivity) {
                //mail.setText("Mail : ");
                //name.setText("Name : ");
                //id.setText("Id : ");
                //imageView.setVisibility(View.INVISIBLE);
                disconnect.isEnabled = false
                signInButton.isEnabled = true
                loginButton.isEnabled = true
            }
        }


        val account = GoogleSignIn.getLastSignedInAccount(this)
        //updateUI(account);
        //Facebook connexion
        this.callbackManager = CallbackManager.Factory.create()

        this.loginButton.setReadPermissions("email")
        this.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })


        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                            try {

                                changeActivity(UserData(`object`.get("id").toString(), `object`.get("name").toString(), ""))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        connectEnable()


                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,link")
                        request.parameters = parameters
                        request.executeAsync()

                    }

                    override fun onCancel() {
                        // App code
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.


            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)

        }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1)

    }


    private fun changeActivity(ud: UserData) {
        val inventoryIntent = Intent(this, InventoryActivity::class.java)
        Log.d("Chaton", ud.toString())
        CallBackGenerator(InventoryActivity(),)
        inventoryIntent.putExtra("PERSONNAL_INFORMATION", ud.toString())

        startActivityForResult(inventoryIntent, 1)
    }

    private fun connectEnable() {
        disconnect.isEnabled = true
        signInButton.isEnabled = false
        loginButton.isEnabled = false

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {

            val acct = completedTask.getResult<ApiException>(ApiException::class.java!!)
            if (acct != null) {
                connectEnable()
                //imageView.setVisibility(View.VISIBLE);
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto = acct.photoUrl
                //this.mail.setText("Mail : " + personEmail);
                //this.name.setText("Name : " + personName + " - " + personGivenName + " - " + personFamilyName + " -");
                //this.id.setText("Id : " + personId);

                /*  if (personPhoto != null) {
                    Picasso.get().load(personPhoto).into(imageView);
                }*/
                changeActivity(UserData(acct.id, acct.displayName, acct.familyName))

            }

        } catch (e: ApiException) {
        }

    }

}
