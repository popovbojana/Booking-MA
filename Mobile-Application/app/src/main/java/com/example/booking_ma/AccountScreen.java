package com.example.booking_ma;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.booking_ma.DTO.ChangePasswordDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.DTO.UserDisplayDTO;
import com.example.booking_ma.DTO.UserPasswordDTO;
import com.example.booking_ma.DTO.UserUpdateDTO;
import com.example.booking_ma.service.IUserService;
import com.example.booking_ma.service.ServiceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private Button buttonCancel, buttonSave, buttonNo, buttonYes;
    private Button buttonEditName, buttonEditSurname, buttonEditPhoneNumber, buttonEditEmail, buttonEditPassword;
    private Button buttonSaveName, buttonSaveSurname, buttonSavePhoneNumber, buttonSaveEmail;
    private Button buttonLogOut;
    private Button buttonDeleteAccount;
    private EditText editTextCurrentPassword, editTextNewPassword, editTextConfirmPassword;
    private EditText editTextName, editTextSurname, editTextPhoneNumber, editTextEmail, editTextPassword;
    private TextView textViewError;
    private String token;
    private SharedPreferences sharedPreferences;
    private Long myId;
    private String myRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        myId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");
        myRole = sharedPreferences.getString("pref_role", "");

        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonEditName = findViewById(R.id.buttonEditName);
        buttonEditSurname = findViewById(R.id.buttonEditSurname);
        buttonEditPhoneNumber = findViewById(R.id.buttonEditPhoneNumber);
        buttonEditEmail = findViewById(R.id.buttonEditEmail);
        buttonEditPassword = findViewById(R.id.buttonEditPassword);

        buttonSaveName = findViewById(R.id.buttonSaveName);
        buttonSaveSurname = findViewById(R.id.buttonSaveSurname);
        buttonSavePhoneNumber = findViewById(R.id.buttonSavePhoneNumber);
        buttonSaveEmail = findViewById(R.id.buttonSaveEmail);

        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonDeleteAccount = findViewById(R.id.buttonDeleteAccount);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadUser(token);
        setEditTextsToFalse();

        buttonEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setEnabled(true);
            }
        });

        buttonEditSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSurname.setEnabled(true);
            }
        });

        buttonEditPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPhoneNumber.setEnabled(true);
            }
        });

        buttonEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmail.setEnabled(true);
            }
        });

        buttonEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordEditDialog();
            }
        });

        buttonSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setEnabled(false);
                String name = editTextName.getText().toString();
                UserUpdateDTO userUpdateDTO = new UserUpdateDTO(name, "", "", "");
                updateUser(token, myId, userUpdateDTO);

            }
        });

        buttonSaveSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSurname.setEnabled(false);
                String surname = editTextSurname.getText().toString();
                UserUpdateDTO userUpdateDTO = new UserUpdateDTO("", surname, "", "");
                updateUser(token, myId, userUpdateDTO);

            }
        });

        buttonSavePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPhoneNumber.setEnabled(false);
                String phoneNumber = editTextPhoneNumber.getText().toString();
                UserUpdateDTO userUpdateDTO = new UserUpdateDTO("", "", phoneNumber, "");
                updateUser(token, myId, userUpdateDTO);

            }
        });

        buttonSaveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmail.setEnabled(false);
                String email = editTextEmail.getText().toString();
                UserUpdateDTO userUpdateDTO = new UserUpdateDTO("", "", "", email);
                updateUser(token,myId, userUpdateDTO);

            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePreferences();
                Intent intent = new Intent(AccountScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAccountDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(myRole.equalsIgnoreCase("OWNER")){
            inflater.inflate(R.menu.menu_host, menu);
        } else if(myRole.equalsIgnoreCase("GUEST")) {
            inflater.inflate(R.menu.menu_guest, menu);
        } else if (myRole.equalsIgnoreCase("ADMIN")) {
            inflater.inflate(R.menu.menu_admin, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(myRole.equalsIgnoreCase("OWNER")){
            if (itemId == R.id.itemHostMainScreen) {
                startActivity(new Intent(this, HostMainScreen.class));
                return true;
            }

            if (itemId == R.id.itemHostAccountScreen) {
                startActivity(new Intent(this, AccountScreen.class));
                return true;
            }

            if (itemId == R.id.itemHostAccommodationsScreen) {
                startActivity(new Intent(this, AccommodationsScreen.class));
                return true;
            }

            if (itemId == R.id.itemHostReservationsScreen) {
//            startActivity(new Intent(this, ReservationsScreen.class));
                return true;
            }

            if (itemId == R.id.itemHostNotificationsScreen) {
//            startActivity(new Intent(this, HostNotificationsScreen.class));
                return true;
            }

            if (itemId == R.id.itemLogOut) {
                deletePreferences();
                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
                return true;

            }
        } else if(myRole.equalsIgnoreCase("GUEST")) {
            if (itemId == R.id.itemGuestMainScreen) {
                startActivity(new Intent(this, GuestMainScreen.class));
                return true;
            }

            if (itemId == R.id.itemGuestAccountScreen) {
                startActivity(new Intent(this, AccountScreen.class));
                return true;
            }

            if (itemId == R.id.itemGuestReservationsScreen) {
//                startActivity(new Intent(this, GuestReservationsScreen.class));
                return true;
            }

            if (itemId == R.id.itemGuestNotificationsScreen) {
//                startActivity(new Intent(this, GuestNotificationsScreen.class));
                return true;
            }

            if (itemId == R.id.itemLogOut) {
                deletePreferences();
                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
                return true;

            }
        } else if (myRole.equalsIgnoreCase("ADMIN")) {
            if (itemId == R.id.itemAdminMainScreen) {
//                startActivity(new Intent(this, AdminMainScreen.class));
                return true;
            }

            if (itemId == R.id.itemAdminAccountScreen) {
                startActivity(new Intent(this, AccountScreen.class));
                return true;
            }

            if (itemId == R.id.itemAdminReportedUsersScreen) {
//                startActivity(new Intent(this, ReportedUsersScreen.class));
                return true;
            }

            if (itemId == R.id.itemAdminReportedCommentsScreen) {
//            startActivity(new Intent(this, ReportedCommentsScreen.class));
                return true;
            }

            if (itemId == R.id.itemAdminAccommodationsApprovalScreen) {
//            startActivity(new Intent(this, AccommodationsApprovalScreen.class));
                return true;
            }

            if (itemId == R.id.itemLogOut) {
                deletePreferences();
                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void deletePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.clear().commit();
    }

    private void showPasswordEditDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_change_password);

        editTextCurrentPassword = dialog.findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = dialog.findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = dialog.findViewById(R.id.editTextConfirmPassword);
        buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonSave = dialog.findViewById(R.id.buttonSave);
        textViewError = dialog.findViewById(R.id.textViewError);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewError.setText("");

                String currentPassword = editTextCurrentPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                UserPasswordDTO userPasswordDTO = new UserPasswordDTO(currentPassword);

                Log.i("EditText password", editTextPassword.getText().toString());
                Log.i("Current password", currentPassword);

                Call<Boolean> call = ServiceUtils.userService(token).checkUserPassword(myId, userPasswordDTO);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()) {

                            if(!response.body().booleanValue()){
                                Log.i("Error", "Wrong password");
                                textViewError.setText("Wrong password");
                            }
                            else if(!newPassword.equals(confirmPassword)){
                                Log.i("Error", "Bad password confirm");
                                textViewError.setText("Bad password confirm");
                            }
                            else{
                                Log.i("Success", "Good password");

                                int numberOfAsterisks = newPassword.length();
                                StringBuilder passwordStars = new StringBuilder();
                                for (int i = 0; i < numberOfAsterisks; i++) {
                                    passwordStars.append("*");
                                }
                                editTextPassword.setText(passwordStars);
                                changePassword(token, myId, new ChangePasswordDTO(newPassword, currentPassword));
                                dialog.dismiss();
                            }

                        } else {
                            onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.d("Fail", t.getMessage());
                    }
                });

            }
        });

        dialog.show();
    }

    private void showDeleteAccountDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_are_you_sure);

        buttonNo = dialog.findViewById(R.id.buttonNo);
        buttonYes = dialog.findViewById(R.id.buttonYes);

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
                Log.i("Account:", "DELETED");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void loadUser(String jwtToken) {
        Call<UserDisplayDTO> call = ServiceUtils.userService(jwtToken).getUserDisplay(myId);

        call.enqueue(new Callback<UserDisplayDTO>() {
            @Override
            public void onResponse(Call<UserDisplayDTO> call, Response<UserDisplayDTO> response) {
                if (response.isSuccessful()) {
                    Log.i("Success", response.message());

                    setDataToEditText(response.body());
                } else {
                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<UserDisplayDTO> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }



    private void updateUser(String jwtToken, Long userId, UserUpdateDTO userUpdateDTO) {
        IUserService userService = ServiceUtils.userService(jwtToken);

        Call<ResponseMessage> call = userService.updateUser(userId, userUpdateDTO);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (!response.isSuccessful()) {
                    Log.i("Error", response.message());
                };
                Log.d("Success", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    private void changePassword(String jwtToken, Long userId, ChangePasswordDTO changePasswordDTO) {
        IUserService userService = ServiceUtils.userService(jwtToken);

        Call<ResponseMessage> call = userService.changePassword(userId, changePasswordDTO);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    Log.i("Success", response.message());
                }
                else{
                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    private void deleteAccount() {
        Call<ResponseMessage> call = ServiceUtils.userService(token).deleteUser(myId);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (!response.isSuccessful()) {
                    Log.i("Error", response.message());
                };
                Log.d("Success", response.body().getMessage());
                deletePreferences();
                Intent intent = new Intent(AccountScreen.this, LoginScreen.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    private void setDataToEditText(UserDisplayDTO userDisplay) {

        int numberOfAsterisks = userDisplay.getPasswordCharNumber();
        StringBuilder passwordStars = new StringBuilder();
        for (int i = 0; i < numberOfAsterisks; i++) {
            passwordStars.append("*");
        }

        editTextName.setText(userDisplay.getName());
        editTextSurname.setText(userDisplay.getSurname());
        editTextPhoneNumber.setText(userDisplay.getPhoneNumber());
        editTextEmail.setText(userDisplay.getEmail());
        editTextPassword.setText(passwordStars);
    }

    private void setEditTextsToFalse(){
        editTextName.setEnabled(false);
        editTextSurname.setEnabled(false);
        editTextPhoneNumber.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextPassword.setEnabled(false);
    }




}

