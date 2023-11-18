package com.example.booking_ma;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.auth0.android.jwt.JWT;
import com.example.booking_ma.DTO.LoginDTO;
import com.example.booking_ma.DTO.Token;
import com.example.booking_ma.DTO.TokenDTO;
import com.example.booking_ma.service.IUserService;
import com.example.booking_ma.service.ServiceUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private TextView textViewError;

    private SharedPreferences sharedPreferences;
    private String token;

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

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("pref_accessToken", "");
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
                    IUserService userService = ServiceUtils.userService(token);
                    Call<TokenDTO> call = userService.login(loginDTO);
                    call.enqueue(new Callback<TokenDTO>() {
                        @Override
                        public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                            if (response.isSuccessful()) {
                                TokenDTO tokenDTO = response.body();
                                if (tokenDTO != null) {
                                    String token = tokenDTO.getAccessToken();
                                    Log.i("Success", "Token: " + token);
                                    Toast.makeText(LoginScreen.this, "Login successful!", Toast.LENGTH_SHORT).show();

                                    JWT jwt = new JWT(token);

                                    Long id = jwt.getClaim("id").asLong();
                                    String email = jwt.getClaim("sub").asString();
                                    String role = jwt.getClaim("role").asString();

                                    setToken(tokenDTO);
                                    setPreferences(id, email, role, tokenDTO);
                                    setTokenPreference(tokenDTO.getAccessToken(), tokenDTO.getRefreshToken());

                                    if(role.equalsIgnoreCase("OWNER")){
                                        startActivity(new Intent(LoginScreen.this, HostMainScreen.class));
                                    }

                                    else if(role.equalsIgnoreCase("GUEST")) {
                                        startActivity(new Intent(LoginScreen.this, GuestMainScreen.class));

                                    } else if (role.equalsIgnoreCase("ADMIN")) {
//                                        startActivity(new Intent(LoginScreen.this, AdministratorMainScreen.class));
                                        startActivity(new Intent(LoginScreen.this, AccommodationsApprovalScreen.class));
                                    }
                                    
                                } else {
                                    Log.e("Error", "Login failed.");
                                    Toast.makeText(LoginScreen.this, "Login failed. Invalid server response.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                try {
                                    String errorMessage = response.errorBody().string();                                    Log.e("Error", "Login failed.");
                                    Log.e("Error", "Login failed:" + errorMessage);
                                    Toast.makeText(LoginScreen.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.e("Error", "Login failed.");
                                    Toast.makeText(LoginScreen.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
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

    private void setToken(TokenDTO loginResponse) {
        Token tokenDTO = Token.getInstance();
        tokenDTO.setAccessToken(loginResponse.getAccessToken());
        tokenDTO.setRefreshToken(loginResponse.getRefreshToken());
    }

    private void deleteTokenPreferences() {
        Token tokenDTO = Token.getInstance();
        tokenDTO.setAccessToken(null);
        tokenDTO.setRefreshToken(null);
        this.sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = this.sharedPreferences.edit();
        spEditor.clear().commit();
    }

    private void setTokenPreference(String token, String refreshToken) {
        this.sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = this.sharedPreferences.edit();
        spEditor.putString("pref_accessToken", token);
        spEditor.putString("pref_refreshToken", refreshToken);
        spEditor.apply();
    }

    private void setSharedPreferences(Long id, String email, String role){
        this.sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = this.sharedPreferences.edit();
        spEditor.putLong("pref_id", id);
        spEditor.putString("pref_email", email);
        spEditor.putString("pref_role", role);
        spEditor.apply();
    }

    private void setPreferences(Long id, String email, String role, TokenDTO loginResponse){
        setSharedPreferences(id, email, role);
        setTokenPreference(loginResponse.getAccessToken(), loginResponse.getRefreshToken());
    }

}
