package com.example.booking_ma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.booking_ma.DTO.NewUserDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.model.enums.Role;
import com.example.booking_ma.service.ServiceUtils;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextName, editTextSurname, editTextAddress, editTextPhoneNumber;
    private Spinner spinnerRole;
    private TextView textViewError;
    private Button buttonRegister, buttonLogin;

    private String token;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        spinnerRole = findViewById(R.id.spinnerRole);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerRole.setAdapter(adapter);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedRole = parentView.getItemAtPosition(position).toString();
                role = selectedRole;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("Error", "Role empty");
            }
        });
        textViewError = findViewById(R.id.textViewError);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("pref_accessToken", "");

    }

    @Override
    protected void onResume() {
        super.onResume();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewError.setText("");

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                String name = editTextName.getText().toString();
                String surname = editTextSurname.getText().toString();
                String address = editTextAddress.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();

                if (email.equals("")){
                    Log.i("Error", "Email empty");
                    textViewError.setText("Email is required!");
                } else if (password.equals("")) {
                    Log.i("Error", "Password empty");
                    textViewError.setText("Password is required!");
                } else if (confirmPassword.equals("")) {
                    Log.i("Error", "Confirm password empty");
                    textViewError.setText("Confirming password is required!");
                } else if (!password.equals(confirmPassword)) {
                    Log.i("Error", "Passwords aren't matching");
                    textViewError.setText("Passwords aren't matching!");
                } else if(name.equals("")) {
                    Log.i("Error", "Name empty");
                    textViewError.setText("Name is required!");
                } else if(surname.equals("")) {
                    Log.i("Error", "Surname empty");
                    textViewError.setText("Surname is required!");
                } else if(address.equals("")) {
                    Log.i("Error", "Address empty");
                    textViewError.setText("Address is required!");
                } else if(phoneNumber.equals("")) {
                    Log.i("Error", "Phone number empty");
                    textViewError.setText("Phone number is required!");
                } else {
                    Log.i("ROLE", role);
                    NewUserDTO newUser;
                    if (role.equals("Host")) {
                        newUser = new NewUserDTO(email, password, name, surname, address, phoneNumber, Role.OWNER);
                    } else {
                        newUser = new NewUserDTO(email, password, name, surname, address, phoneNumber, Role.GUEST);
                    }
                    Call<ResponseMessage> call = ServiceUtils.userService(token).registration(newUser);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if(response.isSuccessful()) {
                                Log.i("Success", response.body().getMessage());
                                Toast.makeText(RegisterScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                                startActivity(intent);
                            } else {
                                try {
                                    String errorMessage = response.errorBody().string();
                                    Log.e("Error", "Registration failed:" + errorMessage);
                                    Toast.makeText(RegisterScreen.this, errorMessage, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.e("Error", "Registration failed.");
                                    Toast.makeText(RegisterScreen.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                            Log.i("Fail", t.getMessage());
                        }
                    });
                }

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }
}
