package com.example.booking_ma;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.booking_ma.DTO.AccommodationChangesDTO;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.AvailabilityDisplayDTO;
import com.example.booking_ma.DTO.NewAccommodationDTO;
import com.example.booking_ma.DTO.NewAvailabilityPriceDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccommodationScreen extends AppCompatActivity {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");


    private Toolbar toolbar;

    private Button buttonEditName, buttonEditDescription, buttonEditAmenities, buttonEditMinGuests, buttonEditMaxGuests, buttonEditLocation, buttonEditAutoApprove;
    private Button buttonSaveName, buttonSaveDescription, buttonSaveAmenities, buttonSaveMinGuests, buttonSaveMaxGuests, buttonSaveLocation, buttonSaveAutoApprove;
    private Button buttonAvailabilityPrice;
    private EditText editTextName, editTextDescription, editTextAmenities, editTextLocation, editTextAutoApprove;
    private TextInputEditText editTextMinGuests, editTextMaxGuests;

    private String token;
    private SharedPreferences sharedPreferences;
    private Long myId;

    private String priceType;
    private Long accommodationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_accommodation_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        myId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        accommodationId = getIntent().getLongExtra("accommodationId", -1);

        buttonEditName = findViewById(R.id.buttonEditName);
        buttonEditDescription = findViewById(R.id.buttonEditDescription);
        buttonEditAmenities = findViewById(R.id.buttonEditAmenities);
        buttonEditMinGuests = findViewById(R.id.buttonEditMinGuests);
        buttonEditMaxGuests = findViewById(R.id.buttonEditMaxGuests);
        buttonEditLocation = findViewById(R.id.buttonEditLocation);
        buttonEditAutoApprove = findViewById(R.id.buttonEditAutoApprove);

        buttonSaveName = findViewById(R.id.buttonSaveName);
        buttonSaveDescription = findViewById(R.id.buttonSaveDescription);
        buttonSaveAmenities = findViewById(R.id.buttonSaveAmenities);
        buttonSaveMinGuests = findViewById(R.id.buttonSaveMinGuests);
        buttonSaveMaxGuests = findViewById(R.id.buttonSaveMaxGuests);
        buttonSaveLocation = findViewById(R.id.buttonSaveLocation);
        buttonSaveAutoApprove = findViewById(R.id.buttonSaveAutoApprove);

        buttonAvailabilityPrice = findViewById(R.id.buttonEditAvailabilityAndPrice);

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextAmenities = findViewById(R.id.editTextAmenities);
        editTextMinGuests = findViewById(R.id.editTextMinGuests);
        editTextMaxGuests = findViewById(R.id.editTextMaxGuests);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextAutoApprove = findViewById(R.id.editTextAutoApprove);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadAccommodation(token);
        setEditTextsToFalse();

        buttonEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setEnabled(true);
            }
        });

        buttonEditDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDescription.setEnabled(true);
            }
        });

        buttonEditAmenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAmenities.setEnabled(true);
            }
        });

        buttonEditMinGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextMinGuests.setEnabled(true);
            }
        });

        buttonEditMaxGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextMaxGuests.setEnabled(true);
            }
        });

        buttonEditLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextLocation.setEnabled(true);
            }
        });

        buttonEditAutoApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAutoApprove.setEnabled(true);
            }
        });


        buttonSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setEnabled(false);
                String name = editTextName.getText().toString();
                String autoApproved = editTextAutoApprove.getText().toString();
                boolean autoApprovedBool = false;
                if (autoApproved.equals("on")){
                    autoApprovedBool = true;
                }
                AccommodationChangesDTO changes = new AccommodationChangesDTO(name, "", "", -1, -1, "", null, -1, -1, autoApprovedBool);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDescription.setEnabled(false);
                String description = editTextDescription.getText().toString();
                String autoApproved = editTextAutoApprove.getText().toString();
                boolean autoApprovedBool = false;
                if (autoApproved.equals("on")){
                    autoApprovedBool = true;
                }
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", description, "", -1, -1, "",null, -1, -1, autoApprovedBool);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveAmenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAmenities.setEnabled(false);
                String amenities = editTextAmenities.getText().toString();
                String autoApproved = editTextAutoApprove.getText().toString();
                boolean autoApprovedBool = false;
                if (autoApproved.equals("on")){
                    autoApprovedBool = true;
                }
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", amenities, -1, -1, "", null, -1, -1, autoApprovedBool);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveMinGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextMinGuests.setEnabled(false);
                String minGuests = editTextMinGuests.getText().toString();
                String autoApproved = editTextAutoApprove.getText().toString();
                boolean autoApprovedBool = false;
                if (autoApproved.equals("on")){
                    autoApprovedBool = true;
                }
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", Integer.parseInt(minGuests), -1, "", null, -1, -1, autoApprovedBool);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveMaxGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextMaxGuests.setEnabled(false);
                String maxGuests = editTextMaxGuests.getText().toString();
                String autoApproved = editTextAutoApprove.getText().toString();
                boolean autoApprovedBool = false;
                if (autoApproved.equals("on")){
                    autoApprovedBool = true;
                }
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", -1, Integer.parseInt(maxGuests), "", null, -1, -1, autoApprovedBool);
                updateAccommodation(token, accommodationId, changes);
            }
        });
//TODO location!!!!!!!
        buttonSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextLocation.setEnabled(false);
                String location = editTextLocation.getText().toString();
                String autoApproved = editTextAutoApprove.getText().toString();
                boolean autoApprovedBool = false;
                if (autoApproved.equals("on")){
                    autoApprovedBool = true;
                }
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", -1, -1, "", null, -1, -1, autoApprovedBool);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonAvailabilityPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        buttonSaveAutoApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAutoApprove.setEnabled(false);
                String autoApproved = editTextAutoApprove.getText().toString();
                boolean autoApprovedBool = false;
                if (autoApproved.equals("on")){
                    autoApprovedBool = true;
                }
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", -1, -1, "", null, -1, -1, autoApprovedBool);
                updateAccommodation(token, accommodationId, changes);
            }
        });

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_availability_price);
        dialog.show();

        EditText editTextPrice = dialog.findViewById(R.id.editTextPrice);
        EditText editTextDateFrom = dialog.findViewById(R.id.editTextDateFrom);
        EditText editTextDateTo = dialog.findViewById(R.id.editTextDateTo);

        Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

        TextView textViewSearchError = dialog.findViewById(R.id.textViewSearchError);

        editTextDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextDateFrom);
            }
        });

        editTextDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextDateTo);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewSearchError.setText("");

                String price = editTextPrice.getText().toString();
                String dateFrom = editTextDateFrom.getText().toString();
                String dateUntil = editTextDateTo.getText().toString();

                if (price.equals("")){
                    Log.i("Error", "Price empty");
                    textViewSearchError.setText("Price is required!");
                } else if (dateFrom.equals("")) {
                    Log.i("Error", "Date from empty");
                    textViewSearchError.setText("Date from required!");
                } else if (dateUntil.equals("")) {
                    Log.i("Error", "Date until empty");
                    textViewSearchError.setText("Date until is required!");
                }
                else {
                    LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom + " 00:00", dateTimeFormatter);
                    LocalDateTime dateTimeTo = LocalDateTime.parse(dateUntil + " 00:00", dateTimeFormatter);

                    if (dateTimeFrom.isBefore(LocalDateTime.now().minusDays(1))) {
                        Log.i("Error", "Date from is before today");
                        textViewSearchError.setText("Date from must be today or later!");
                    } else if (dateTimeTo.isBefore(LocalDateTime.now().minusDays(1))) {
                        Log.i("Error", "Date until is before today");
                        textViewSearchError.setText("Date until must be today or later!");
                    } else if (dateTimeFrom.isAfter(dateTimeTo)) {
                        Log.i("Error", "Date from is after date until");
                        textViewSearchError.setText("Date from must be before Date until!");
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                        String formattedDateTimeFrom = dateTimeFrom.format(formatter);
                        String formattedDateTimeTo = dateTimeTo.format(formatter);

                        String autoApproved = editTextAutoApprove.getText().toString();
                        boolean autoApprovedBool = false;
                        if (autoApproved.equals("on")){
                            autoApprovedBool = true;
                        }

                        AccommodationChangesDTO changes = new AccommodationChangesDTO(formattedDateTimeFrom, formattedDateTimeTo, Double.parseDouble(price), autoApprovedBool);
                        updateAccommodation(token, accommodationId, changes);
                        dialog.dismiss();
                    }
                }
            }

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String selectedDate = day + "/" + (month + 1) + "/" + year;
                        editText.setText(selectedDate);
                    }
                },
                year,
                month,
                day);

        datePickerDialog.show();
    }

    private void loadAccommodation(String jwtToken) {
        Call<AccommodationDisplayDTO> call = ServiceUtils.accommodationService(jwtToken).getAccommodationById(accommodationId);

        call.enqueue(new Callback<AccommodationDisplayDTO>() {
            @Override
            public void onResponse(Call<AccommodationDisplayDTO> call, Response<AccommodationDisplayDTO> response) {
                if (response.isSuccessful()) {
                    Log.i("Success", response.message());

                    setDataToEditText(response.body());
                } else {
                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<AccommodationDisplayDTO> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    private void updateAccommodation(String jwtToken, Long id, AccommodationChangesDTO changes){
        Call<ResponseMessage> call = ServiceUtils.accommodationService(jwtToken).changeAccommodation(id, changes);
        Log.e("change", String.valueOf(id));
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (!response.isSuccessful()) {
                    Log.i("Error", response.message());
                };
                Toast.makeText(EditAccommodationScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Success", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    private void setDataToEditText(AccommodationDisplayDTO accommodationDisplay) {
        editTextName.setText(accommodationDisplay.getName());
        editTextDescription.setText(accommodationDisplay.getDescription());
        editTextAmenities.setText(accommodationDisplay.getAmenities());
        editTextMinGuests.setText(String.valueOf(accommodationDisplay.getMinGuests()));
        editTextMaxGuests.setText(String.valueOf(accommodationDisplay.getMaxGuests()));
        editTextLocation.setText(accommodationDisplay.getAddress());
        String autoApprove = "";
        if (accommodationDisplay.isAutoApproved()){
            autoApprove = "on";
        } else {
            autoApprove = "off";
        }
        editTextAutoApprove.setText(autoApprove);
    }


    private void setEditTextsToFalse(){
        editTextName.setEnabled(false);
        editTextDescription.setEnabled(false);
        editTextAmenities.setEnabled(false);
        editTextMinGuests.setEnabled(false);
        editTextMaxGuests.setEnabled(false);
        editTextLocation.setEnabled(false);
        editTextAutoApprove.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_host, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

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

        if (itemId == R.id.itemHostCommentsScreen) {
            startActivity(new Intent(this, HostCommentsActivity.class));
            return true;
        }

        if (itemId == R.id.itemLogOut) {
            deletePreferences();
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void deletePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.clear().commit();
    }
}
