package com.siddydevelops.aldo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import ir.alirezabdn.wp7progress.WP10ProgressBar;


//Activity for Splash screen with In-Built Google Auth, retrive user data.


public class SplashScreenActivity extends AppCompatActivity {

    ImageView logo;
    LottieAnimationView lottieAnimationView;
    LottieAnimationView lottie_dev;
    TextView logoTxt;
    LinearLayout loginAccess;

    WP10ProgressBar progressBar;

    Button googleLogo;

    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 65;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View overlay = findViewById(R.id.layout_splashScreen);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.appTheme));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FBEAEB");
        window.setStatusBarColor(colorCodeDark);

        getSupportActionBar().hide();

        logo = findViewById(R.id.logo);
        logoTxt = findViewById(R.id.appName);
        lottieAnimationView = findViewById(R.id.lottie);
        lottie_dev = findViewById(R.id.lottie_dev);

        loginAccess = findViewById(R.id.loginLinearLayout);

        googleLogo = findViewById(R.id.googelLogo);

        logo.animate().translationY(-2500).setDuration(1000).setStartDelay(3000);
        lottieAnimationView.animate().translationY(2500).setDuration(1000).setStartDelay(3000);
        logoTxt.animate().alpha(1).setDuration(2000).setStartDelay(3500);
        //loginAccess.animate().alpha(1).setDuration(2000).setStartDelay(3500);

        loginAccess.animate().translationY(-900).setDuration(1000).setStartDelay(4000);

        lottie_dev.animate().alpha(1).setDuration(2000).setStartDelay(4100);

        progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        googleLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogin();
                googleLogo.setVisibility(View.INVISIBLE);
                progressBar.showProgressBar();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            if(user !=null)
                            {
                                String userName = user.getDisplayName();
                                String userUid = user.getUid();
                                String userEmail = user.getEmail();
                                String userImage = user.getPhotoUrl().toString();

                                DocumentReference documentReference = firebaseFirestore.collection("USERS")
                                        .document(userUid).collection("UserDetails").document();
                                Map<String, Object> userDetails = new HashMap<>();
                                userDetails.put("UserName",userName);
                                userDetails.put("UserEmail",userEmail);
                                userDetails.put("UserImage",userImage);

                                documentReference.set(userDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SplashScreenActivity.this, "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                                        Toast.makeText(SplashScreenActivity.this, "Welcome! " + userName, Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(SplashScreenActivity.this, "User Registration Failed! Try Again.", Toast.LENGTH_SHORT).show();
                                        Log.i("ERROR",e.getMessage());
                                    }
                                });

                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SplashScreenActivity.this, "SignIn Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser!=null)
                {
                    Toast.makeText(SplashScreenActivity.this, "Signing In. Please Wait", Toast.LENGTH_SHORT).show();
                    googleLogo.setVisibility(View.INVISIBLE);
                    progressBar.showProgressBar();
                }
            }
        }, 3000);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser!=null)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                    Toast.makeText(SplashScreenActivity.this, "Welcome! " + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                }
            }
        }, 7000);
    }

    public void googleLogin()
    {
        Toast.makeText(this, "Google Login Initiated!", Toast.LENGTH_LONG).show();
        signIn();
    }
}