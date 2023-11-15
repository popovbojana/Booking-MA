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

import com.example.booking_ma.DTO.LoginDTO;
import com.example.booking_ma.DTO.TokenDTO;
import com.example.booking_ma.service.ServiceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    LoginDTO loginDTO = new LoginDTO(email, password);
                    Call<TokenDTO> call = ServiceUtils.userService.login(loginDTO);
                    call.enqueue(new Callback<TokenDTO>() {
                        @Override
                        public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                            if(!response.isSuccessful()){
                                Log.i("Error", response.message());
                                Toast.makeText(LoginScreen.this, response.message(), Toast.LENGTH_SHORT).show();
                            };
                            Log.i("Success", "Token:" + response.body().getAccessToken());
                            Toast.makeText(LoginScreen.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreen.this, GuestMainScreen.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<TokenDTO> call, Throwable t) {
                            Log.i("Fail", t.getMessage());
                        }
                    });
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
