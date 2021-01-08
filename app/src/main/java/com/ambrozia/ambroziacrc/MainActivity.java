package com.ambrozia.ambroziacrc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

public class MainActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button LogInBtn, toRegisterBtn, GuestBtn;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginInfo();

        if(firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), navTest.class));
        }

        LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uEmail = userEmail.getText().toString().trim();
                String uPass = userPassword.getText().toString().trim();

                if(uEmail.isEmpty()){
                    userEmail.setError("Email required! ");
                    return;
                }

                if(uPass.isEmpty()){
                    userPassword.setError("Password required! ");
                    return;
                }

                if(uPass.length() < 6){
                    userPassword.setError("Password is minimum 6 characters! ");
                    return;
                }



                firebaseAuth.signInWithEmailAndPassword(uEmail, uPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Logged in successfully! ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), navTest.class));
                        }
                        else{
                            Toast.makeText(MainActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        GuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsForGuest.class));
            }
        });
    }

    private void loginInfo(){
        userEmail = (EditText)findViewById(R.id.userEmailLogin);
        userPassword = (EditText)findViewById(R.id.userPasswordLogin);
        LogInBtn = (Button)findViewById(R.id.loginViewLogInButton);
        toRegisterBtn = (Button)findViewById(R.id.loginViewSignUpButton);
        GuestBtn = (Button)findViewById(R.id.loginViewGuestButton);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

}
