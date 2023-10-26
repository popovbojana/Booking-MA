package com.example.booking_ma;

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

public class RegisterScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextName, editTextSurname, editTextAddress, editTextPhoneNumber;
    private Spinner spinnerRole;
    private TextView textViewError;
    private Button buttonRegister, buttonLogin;

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
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        spinnerRole = findViewById(R.id.spinnerRole);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerRole.setAdapter(adapter);

        textViewError = findViewById(R.id.textViewError);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
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
                String role = "";

//                spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                        String selectedRole = parentView.getItemAtPosition(position).toString();
////                        Toast.makeText(RegisterScreen.this, "Role: " + selectedRole, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parentView) {
//                        Log.i("Error", "Role empty");
//                        textViewError.setText("Role is required!");
//                    }
//                });

                if (email.equals("")){
                    Log.i("Error", "Email empty");
                    textViewError.setText("Email is required!");
                } else if (password.equals("")) {
                    Log.i("Error", "Password empty");
                    textViewError.setText("Password is required!");
                } else if (confirmPassword.equals("")) {
                    Log.i("Error", "Confirm password empty");
                    textViewError.setText("Confirming password is required!");
                } else if (password.equals(confirmPassword)) {
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
                    //TODO: povezati sa serverom
                    Log.i("Success", "Successfully registered!");
//                    Toast.makeText(RegisterScreen.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: prebaciti na LoginScreen
            }
        });
    }
}
