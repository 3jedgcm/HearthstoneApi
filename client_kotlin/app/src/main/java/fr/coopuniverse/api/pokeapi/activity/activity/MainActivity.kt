package fr.coopuniverse.api.pokeapi.activity.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
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

import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.Reponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CallBackDisplay  {


    var callbackManager: CallbackManager? = null
    var account: Account? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        sign_in_google.setOnClickListener {
            this.googleSignIn()
        }

        sign_in_simple!!.setOnClickListener {
            this.simpleSignIn()
        }
        this.callbackManager = CallbackManager.Factory.create()
        sign_in_facebook.setReadPermissions("email")
        sign_in_facebook.registerCallback(this.callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {}

            override fun onCancel() {}

            override fun onError(error: FacebookException?) {}
        })


        this.callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(this.callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                            try {
                                facebookSignIn(`object`.get("id").toString(),Account(name = `object`.get("name").toString(),id =`object`.get("id").toString(),connectWith = "Facebook"))
                            } catch (e: JSONException) {e.printStackTrace()}
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,link")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {}
                    override fun onError(exception: FacebookException) {}
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        this.callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun googleSignIn()
    {
        val signInIntent = this.mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 1)
    }

    private fun simpleSignIn()
    {
        this.account = Account(name = login_field?.text.toString(), connectWith = "simple account")
        CallBackGenerator(callback = this,action = "Connect",isActivateCallBack = true, login = login_field?.text.toString() ,pass = pass_field?.text.toString(), url = "https://api.coopuniverse.fr/").execute()
    }

    private fun facebookSignIn(id: String, account: Account)
    {
        this.account = account
        CallBackGenerator(callback = this,action = "ConnectFacebook",isActivateCallBack = true, key = id , url = "https://api.coopuniverse.fr/").execute()
    }

    private fun googleSignIn(id:String)
    {
        CallBackGenerator(callback = this,action = "ConnectGoogle",isActivateCallBack = true, key = id , url = "https://api.coopuniverse.fr/").execute()
    }



    override fun display(rep: Reponse,action: String)
    {
        if(!rep.connect)
        {
        when (action) {
            "ConnectFacebook" -> {
                Log.d("Chaton","No account - register with Facebook")
                CallBackGenerator(callback = this,action = "RegisterFacebook",isActivateCallBack = true, key = rep.id , url = "https://api.coopuniverse.fr/").execute()
            }
            "ConnectGoogle" -> {
                Log.d("Chaton","No account - Register with Google")
                CallBackGenerator(callback = this,action = "RegisterGoogle",isActivateCallBack = true, key = rep.id , url = "https://api.coopuniverse.fr/").execute()
            }
            "RegisterGoogle" -> {
                Log.d("Chaton","Connect with Google")
                CallBackGenerator(callback = this,action = "ConnectGoogle",isActivateCallBack = true, key = rep.id , url = "https://api.coopuniverse.fr/").execute()
            }
            "RegisterFacebook" -> {
                Log.d("Chaton","Connect with Facebook")
                CallBackGenerator(callback = this,action = "ConnectFacebook",isActivateCallBack = true, key = rep.id , url = "https://api.coopuniverse.fr/").execute()
            }
            "Connect" -> {
                errorView.text = "Mauvais login ou mot de passe"
            }
        }
        }
        else
        {
            account?.id = rep.user?.IdUser
            account?.money = rep.user?.Money
            changeActivity(account)
        }
    }

    private fun changeActivity(ud: Account?)
    {
        val inventoryIntent = Intent(this, HomeActivity::class.java)
        CallBackGenerator(MainActivity(),false)
        inventoryIntent.putExtra("connectWith", ud?.connectWith )
        inventoryIntent.putExtra("name", ud?.name )
        inventoryIntent.putExtra("lastname", ud?.surname )
        inventoryIntent.putExtra("id", ud?.id )
        inventoryIntent.putExtra("url", ud?.urlPicture.toString() )
        inventoryIntent.putExtra("money", ud?.money )
        startActivityForResult(inventoryIntent, 1)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>)
    {
        try
        {
            val acct = completedTask.getResult<ApiException>(ApiException::class.java!!)
            if (acct != null)
            {
                this.account = Account(name = acct.displayName, surname = acct.familyName,id = acct.id,urlPicture = acct.photoUrl,connectWith = "Google")
                this.googleSignIn(acct.id!!)
            }
        } catch (e: ApiException) {}
    }
}
