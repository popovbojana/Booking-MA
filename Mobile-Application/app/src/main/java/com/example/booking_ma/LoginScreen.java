package com.example.booking_ma;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private TextView textViewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        textViewError = findViewById(R.id.textViewError);
    }

    @Override
    protected void onResume() {
        super.onResume();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewError.setText("");

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.equals("")){
                    Log.i("Error", "Empty email");
                    textViewError.setText("Email is required!");
                } else if (password.equals("")){
                    Log.i("Error", "Empty password");
                    textViewError.setText("Password is required!");
                } else {
                    //TODO: povezati sa serverom
                    Log.i("Success", "Logged in!");
                    Toast.makeText(LoginScreen.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }
}
