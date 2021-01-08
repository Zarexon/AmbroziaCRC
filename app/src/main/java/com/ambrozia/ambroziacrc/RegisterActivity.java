package com.ambrozia.ambroziacrc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText userFirstName, userLastName, userAge, userEmail, userPassword;
    Button toLogInBtn, RegisterBtn, GuestBtn;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerInfo();

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uEmail = userEmail.getText().toString().trim();
                String uPass = userPassword.getText().toString().trim();
                String uLastName = userLastName.getText().toString().trim();
                String uFirstName = userFirstName.getText().toString().trim();
                String uAge = userAge.getText().toString().trim();

                if(uFirstName.isEmpty()){
                    userFirstName.setError("First name required!");
                    return;
                }


                if(uLastName.isEmpty()){
                    userLastName.setError("Last name required!");
                    return;
                }

                if(uAge.isEmpty()){
                    userAge.setError("Age required!");
                    return;
                }

                if(uEmail.isEmpty()){
                    userEmail.setError("Email required!");
                    return;
                }

                if(uPass.isEmpty()){
                    userPassword.setError("Password required!");
                    return;
                }

                if(uPass.length() < 6){
                    userPassword.setError("Password must be minimum 6 characters!");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(uEmail,uPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userID = firebaseAuth.getCurrentUser().getUid();
                            Log.d("TAG", "this uid: "+ userID);
                            Toast.makeText(RegisterActivity.this, "User account created successfully!", Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("FirstName", uFirstName);
                            user.put("LastName", uLastName);
                            user.put("Age", uAge);
                            user.put("eMail", uEmail);
                            user.put("BasicUser", true);
                            user.put("PowerUser", false);
                            documentReference.set(user);
                            startActivity(new Intent(getApplicationContext(), navTest.class));
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }



        });

        toLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        GuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this, MapsForGuest.class));
            }
        });
    }

    private void registerInfo(){
        userFirstName = (EditText)findViewById(R.id.userFirstName);
        userLastName = (EditText)findViewById(R.id.userLastName);
        userAge = (EditText)findViewById(R.id.userAge);
        userEmail = (EditText)findViewById(R.id.userEmailRegistration);
        userPassword = (EditText)findViewById(R.id.userPasswordRegistration);
        toLogInBtn = (Button)findViewById(R.id.registerViewLogInButton);
        RegisterBtn = (Button)findViewById(R.id.registerViewRegisterButton);
        GuestBtn = (Button)findViewById(R.id.registerViewGuestButton);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

//        if (firebaseAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), navTest.class));
//            finish();
//        }

    }
}