package com.example.application;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Form extends AppCompatActivity
{

    private EditText email, passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


    }

    public void submitForm(View view)
    {

        email = (EditText) findViewById(R.id.userEmail);
        passWord = (EditText) findViewById(R.id.auth_password);
        String emailValue = email.getText().toString();
        String passWordValue = passWord.getText().toString();
        Authentication authentication = new Authentication(emailValue, passWordValue);
        authentication.createAccount(emailValue, passWordValue);
    }


    private boolean validateForm()
    {
        return true;
    }
}
