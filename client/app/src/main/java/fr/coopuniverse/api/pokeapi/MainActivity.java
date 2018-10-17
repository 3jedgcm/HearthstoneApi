package fr.coopuniverse.api.pokeapi;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;
    public static AccessToken accessToken;
    TextView id;
    TextView name;
    TextView mail;
    boolean isLoggedIn;
    Button disconnect;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Google connexion
        signInButton = findViewById(R.id.sign_in_button);
        this.id = findViewById(R.id.id);
        this.mail= findViewById(R.id.mail);
        this.name = findViewById(R.id.name);
        this.imageView = findViewById(R.id.imageView);
        this.disconnect = findViewById(R.id.button2);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        disconnect.setEnabled(false);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signIn();
            }
        });
        disconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Debug-Chaton","Bien deco");
                                mail.setText("Mail : ");
                                name.setText("Name : ");
                                id.setText("Id : ");
                                imageView.setVisibility(View.INVISIBLE);
                                disconnect.setEnabled(false);
                                signInButton.setEnabled(true);
                            }
                        });
            }
        });


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
        //Facebook connexion
        this.callbackManager = CallbackManager.Factory.create();
        this.loginButton = (LoginButton) findViewById(R.id.login_button);
        this.loginButton.setReadPermissions("email");

        this.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getInfo();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });



        }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.



            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void getInfo()
    {
        GraphRequest request = GraphRequest.newMeRequest(MainActivity.accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if(object != null)
                        Log.d("Debug-chaton",object.toString());

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);



    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                disconnect.setEnabled(true);
                imageView.setVisibility(View.VISIBLE);
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                this.mail.setText("Mail : " + personEmail);
                this.name.setText("Name : " + personName + " - " + personGivenName + " - " +  personFamilyName + " -");
                this.id.setText("Id : " + personId);
                this.signInButton.setEnabled(false);
                if(personPhoto != null) {
                    Picasso.get().load(personPhoto).into(imageView);
                }
            }

        } catch (ApiException e) { }
    }

}
