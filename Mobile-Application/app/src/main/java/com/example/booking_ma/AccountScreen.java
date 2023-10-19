package com.example.booking_ma;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AccountScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private Button buttonEditPassword;
    private Dialog dialogEditPassword;
    private EditText editTextCurrentPassword, editTextNewPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_screen);
        setSupportActionBar(toolbar);

        toolbar = findViewById(R.id.toolbar);
        buttonEditPassword = findViewById(R.id.buttonEditPassword);

    }

    @Override
    protected void onResume() {
        super.onResume();

        buttonEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordEditDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_account_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void showPasswordEditDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_change_password);

        editTextCurrentPassword = dialog.findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = dialog.findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = dialog.findViewById(R.id.editTextConfirmPassword);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        Button buttonSave = dialog.findViewById(R.id.buttonSave);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the password change here
                String currentPassword = editTextCurrentPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                // Perform password change validation and processing
                // ...

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
