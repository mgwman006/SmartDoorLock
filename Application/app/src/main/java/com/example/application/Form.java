package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Form extends AppCompatActivity
{

    private EditText email, passWord;
    public FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //System.out.println(currentUser.getEmail());
        updateUI(currentUser);
    }



    public void updateUI (FirebaseUser currentUser)
    {
        if (currentUser == null)
        {

        }

        else
        {
            // signOut();
            Intent intent = new Intent( Form.this, MainActivity.class);
            startActivity(intent);
        }
    }


    public void submitForm(View view)
    {

        email = (EditText) findViewById(R.id.userEmail);
        passWord = (EditText) findViewById(R.id.auth_password);
        String emailValue = email.getText().toString();
        String passWordValue = passWord.getText().toString();
       // Authentication authentication = new Authentication(emailValue, passWordValue);
        createAccount(emailValue, passWordValue);
    }


    private boolean validateForm()
    {
        return true;
    }


    void createAccount(final String email, final String password)
    {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //sigIn(email, password);
                            Intent intent = new Intent( Form.this, MainActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Form.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);

                        }
                    }
                });
        // [END create_user_with_email]
    }


    private void signOut()
    {
        mAuth.signOut();
        // updateUI(null);
    }


}