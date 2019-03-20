package fr.coopuniverse.api.pokeapi.activity.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer

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
import fr.coopuniverse.api.pokeapi.activity.data.Account
import fr.coopuniverse.api.pokeapi.activity.view.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var callbackManager: CallbackManager? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var isConnected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        this.callbackManager = CallbackManager.Factory.create()
        sign_in_google.setOnClickListener {
            this.googleSignIn()
        }
        sign_in_simple.setOnClickListener {
            sign_in_simple.isEnabled = false
            Account.name = login_field?.text.toString()
            Account.password = pass_field?.text.toString()
            Account.connectWith = this.getString(R.string.simple)
            this.submit()
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
                            try
                            {
                                Account.name = `object`.get("name").toString()
                                Account.connectWith = "Facebook"
                                Account.key = `object`.get("id").toString()
                                submit()
                            }
                            catch (e: JSONException)
                            {
                                Log.e("FacebookSignIn",e.toString())
                            }
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,link")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel()
                    {}
                    override fun onError(e: FacebookException)
                    {
                        Log.e("FacebookSignIn",e.toString())
                    }
                })


        MainActivityViewModel.simpleSignInStateButton.observe(this, Observer { sign_in_simple.isEnabled = it })
        MainActivityViewModel.infoError.observe(this, Observer { errorView.text = it })
        MainActivityViewModel.changeActivity.observe(this, Observer { if(it)this.changeActivity()})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        this.callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && !isConnected) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun googleSignIn() {
        startActivityForResult(this.mGoogleSignInClient?.signInIntent, 1)
    }

    private fun submit() {
        MainActivityViewModel.signIn()
    }

    private fun changeActivity() {
        startActivityForResult(Intent(this, HomeActivity::class.java), 1)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = completedTask.getResult<ApiException>(ApiException::class.java)
            if (acct != null) {
                isConnected = true
                Account.name = acct.displayName.toString()
                Account.surname = acct.familyName.toString()
                Account.id = acct.id.toString()
                Account.urlPicture = acct.photoUrl
                Account.connectWith = this.resources.getText(R.string.google).toString()
                this.submit()
            }
        }
        catch (e: ApiException) {
            Log.e("GoogleSignIn",e.toString())
        }
    }
}
