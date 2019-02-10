package fr.coopuniverse.api.pokeapi.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import org.json.JSONException

import fr.coopuniverse.api.pokeapi.R
import fr.coopuniverse.api.pokeapi.activity.callback.CallBackDisplay

import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.httpRequestManager.CallBackGenerator
import fr.coopuniverse.api.pokeapi.activity.data.Reponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CallBackDisplay {


    private var callbackManager: CallbackManager? = null
    private var account = Account()
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        this.callbackManager = CallbackManager.Factory.create()
        sign_in_google.setOnClickListener {
            this.googleSignIn()
        }
        sign_in_simple!!.setOnClickListener {
            this.simpleSignIn()
        }
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
                                facebookSignIn(`object`.get("id").toString(), Account(name = `object`.get("name").toString(), id = `object`.get("id").toString(), connectWith = "Facebook"))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        this.callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun googleSignIn() {
        val signInIntent = this.mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, 1)
    }

    private fun simpleSignIn() {
        sign_in_simple.isEnabled = false
        this.account = Account(name = login_field?.text.toString(), connectWith = this.getString(R.string.simple))
        CallBackGenerator(callback = this, action = "Connect", isActivateCallBack = true, login = login_field?.text.toString(), pass = pass_field?.text.toString(), url = this.getString(R.string.url)).execute()
    }

    private fun facebookSignIn(id: String, account: Account) {
        this.account = account
        CallBackGenerator(callback = this, action = "ConnectFacebook", isActivateCallBack = true, key = id, url = this.getString(R.string.url)).execute()
    }

    private fun googleSignIn(id: String) {
        CallBackGenerator(callback = this, action = "ConnectGoogle", isActivateCallBack = true, key = id, url = this.getString(R.string.url)).execute()
    }


    override fun display(rep: Reponse, action: String) {
        sign_in_simple.isEnabled = true
        if (!rep.connect) {
            when (action) {
                "ConnectFacebook" -> {
                    CallBackGenerator(callback = this, action = "RegisterFacebook", isActivateCallBack = true, key = rep.id, url = this.getString(R.string.url)).execute()
                }
                "ConnectGoogle" -> {
                    CallBackGenerator(callback = this, action = "RegisterGoogle", isActivateCallBack = true, key = rep.id, url = this.getString(R.string.url)).execute()
                }
                "RegisterGoogle" -> {
                    CallBackGenerator(callback = this, action = "ConnectGoogle", isActivateCallBack = true, key = rep.id, url = this.getString(R.string.url)).execute()
                }
                "RegisterFacebook" -> {
                    CallBackGenerator(callback = this, action = "ConnectFacebook", isActivateCallBack = true, key = rep.id, url = this.getString(R.string.url)).execute()
                }
                "Connect" -> {
                    errorView.text = "Mauvais login ou mot de passe"
                }
            }
        } else {
            account.id = rep.user?.IdUser!!
            account.money = rep.user?.Money
            changeActivity(account)
        }
    }

    private fun changeActivity(ud: Account?) {
        val inventoryIntent = Intent(this, HomeActivity::class.java)
        CallBackGenerator(MainActivity(), false)
        inventoryIntent.putExtra("connectWith", ud?.connectWith)
        inventoryIntent.putExtra("name", ud?.name)
        inventoryIntent.putExtra("lastname", ud?.surname)
        inventoryIntent.putExtra("id", ud?.id)
        inventoryIntent.putExtra("url", ud?.urlPicture.toString())
        inventoryIntent.putExtra("money", ud?.money)
        startActivityForResult(inventoryIntent, 1)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = completedTask.getResult<ApiException>(ApiException::class.java)
            if (acct != null) {
                this.account = Account(name = acct.displayName, surname = acct.familyName, id = acct.id!!, urlPicture = acct.photoUrl, connectWith = this.getString(R.string.google))
                this.googleSignIn(acct.id!!)
            }
        } catch (e: ApiException) {
        }
    }
}
