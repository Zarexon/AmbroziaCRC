package com.ambrozia.ambroziacrc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class navTest extends AppCompatActivity {
    TextView uName, uEmail;
    String uUid;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.MapForUsers, R.id.logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_test, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void MapForUsers(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), MapsForGuest.class));
        finish();
    }

    public void deleteAcc(MenuItem item) {
        uUid = firebaseAuth.getCurrentUser().getUid();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm account deletion");
        builder.setMessage("Do you want to delete your account?");

        builder.setPositiveButton("YES", (dialog, which) -> {
            // Do nothing but close the dialog
            firebaseFirestore.collection("users").document(uUid).delete();
            firebaseAuth.getCurrentUser().delete();
            if(firebaseAuth.getCurrentUser() == null)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            dialog.dismiss();

        });

        builder.setNegativeButton("NO", (dialog, which) -> {

            // Do nothing
            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void currentUser(){
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//
//        //Current user data

//
//        uUid = firebaseAuth.getCurrentUser().getUid();
//        String Name = (String) uName.getText().toString();
//        Log.d("TAG", "this is the uid: " + uUid + Name);
//            DocumentReference documentReference = firebaseFirestore.collection("users").document(uUid);
//            uEmail.setText(firebaseAuth.getCurrentUser().getEmail());

    }
}

