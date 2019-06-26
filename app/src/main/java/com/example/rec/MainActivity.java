package com.example.rec;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import android.widget.Button;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import android.support.v7.widget.Toolbar;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    int settype;
    TextView txtitype;
    EditText password;

    private LinearLayout Prof_sanction;
    int RC_SIGN_IN = 0;
    private static final String EMAIL = "email";
    private static final String FullName = "FullName";
    private static final String DOB = "DOB";
    ImageView image;
    private LoginButton loginButton;

    private CallbackManager callbackManager;
    GoogleSignIn mGoogleSignInClient;
    GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private SignInButton GsignIn;
    private FirebaseAuth firebaseAuth;
    private static final int PERMISSION_REQUEST_CODE = 1;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestPermission();

        PasswordEyeView();

        FirebaseApp.initializeApp(this);
        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this actaq                                                                                                                                  ivity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), homePg.class));
        }
        progressDialog = new ProgressDialog(this);


    }

    public void createAccountBtn(View v) {
        Intent i1 = new Intent(this, createAccountPg.class);
        this.startActivity(i1);
    }

    //firebase user login authentication
    public void homeBtn(View v) {

    EditText edt1 = (EditText) this.findViewById(R.id.username);
    EditText edt2 = (EditText) this.findViewById(R.id.password);

    String username = edt1.getText().toString();
    String password = edt2.getText().toString();

    //checking if email and passwords are empty
    if(TextUtils.isEmpty(username)){
        Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
        return;
    }

    if(TextUtils.isEmpty(password)){
        Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
        return;
    }

    //if the email and password are not empty
    //displaying a progress dialog

    progressDialog.setMessage("Signing In Please Wait...");
    progressDialog.show();

    //logging in the user
    firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    //if the task is successfull
                    if(task.isSuccessful()){
                        //start the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), homePg.class));
                        return;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(MainActivity.this,"no user found, Please create account",Toast.LENGTH_LONG).show();
        }
    });


    }

    void PasswordEyeView()
    {
        password = (EditText) findViewById(R.id.password);
        settype=0;
        txtitype = (TextView) findViewById(R.id.titype);
        txtitype.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(settype==1){
                    settype=0;
                    password.setTransformationMethod(null);
                    if(password.getText().length()>0)
                    {
                        password.setSelection(password.getText().length());
                    }
                    txtitype.setBackgroundResource(R.drawable.red_eye);
                }
                else {
                    settype=1;
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    if(password.getText().length()>0)
                    {
                        password.setSelection(password.getText().length());
                    }
                    txtitype.setBackgroundResource(R.drawable.eyeview);
                }
            }
        });
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }
/*
    void google_login(){
        GsignIn = (SignInButton) findViewById(R.id.signWithgoogle);
        GsignIn.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

    }



    void facebook_login() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }*/


}
