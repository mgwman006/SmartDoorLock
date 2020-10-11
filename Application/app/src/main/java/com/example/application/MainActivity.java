package com.example.application;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity
{

    private AppBarConfiguration mAppBarConfiguration;
    public FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

// ...



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);




        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
       // mAuth.signOut();

    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //System.out.println(currentUser.getEmail());
        updateUI(currentUser);
    }



    public void updateUI (FirebaseUser currentUser)
    {
        if (currentUser == null)
        {
            Intent intent = new Intent( this, Form.class);
            startActivity(intent);
        }

        else
        {
            // signOut();
        }
    }

    private void signOut()
    {
        mAuth.signOut();
       // updateUI(null);
    }


}
