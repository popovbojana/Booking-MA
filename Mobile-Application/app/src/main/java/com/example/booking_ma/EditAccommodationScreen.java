package com.example.booking_ma;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.booking_ma.DTO.AccommodationChangesDTO;
import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccommodationScreen extends AppCompatActivity {

    private Toolbar toolbar;

    private Button buttonEditName, buttonEditDescription, buttonEditAmenities, buttonEditMinGuests, buttonEditMaxGuests, buttonEditType, buttonEditCancellation, buttonEditStandardPrice, buttonEditPriceType;
    private Button buttonSaveName, buttonSaveDescription, buttonSaveAmenities, buttonSaveMinGuests, buttonSaveMaxGuests, buttonSaveType, buttonSaveCancellation, buttonSaveStandardPrice, buttonSavePriceType;
    private EditText editTextName, editTextDescription, editTextAmenities, editTextType;
    private TextInputEditText editTextMinGuests, editTextMaxGuests, editTextCancellation, editTextStandardPrice;
    private Spinner spinnerPriceType;

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
        buttonEditType = findViewById(R.id.buttonEditType);
        buttonEditCancellation = findViewById(R.id.buttonEditCancellation);
        buttonEditStandardPrice = findViewById(R.id.buttonEditStandardPrice);
        buttonEditPriceType = findViewById(R.id.buttonEditPriceType);

        buttonSaveName = findViewById(R.id.buttonSaveName);
        buttonSaveDescription = findViewById(R.id.buttonSaveDescription);
        buttonSaveAmenities = findViewById(R.id.buttonSaveAmenities);
        buttonSaveMinGuests = findViewById(R.id.buttonSaveMinGuests);
        buttonSaveMaxGuests = findViewById(R.id.buttonSaveMaxGuests);
        buttonSaveType = findViewById(R.id.buttonSaveType);
        buttonSaveCancellation = findViewById(R.id.buttonSaveCancellation);
        buttonSaveStandardPrice = findViewById(R.id.buttonSaveStandardPrice);
        buttonSavePriceType = findViewById(R.id.buttonSavePriceType);

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextAmenities = findViewById(R.id.editTextAmenities);
        editTextType = findViewById(R.id.editTextType);

        editTextMinGuests = findViewById(R.id.editTextMinGuests);
        editTextMaxGuests = findViewById(R.id.editTextMaxGuests);
        editTextCancellation = findViewById(R.id.editTextCancellation);
        editTextStandardPrice = findViewById(R.id.editTextStandardPrice);

        spinnerPriceType = findViewById(R.id.spinnerPriceType);
        ArrayAdapter<CharSequence> priceTypeAdapter = ArrayAdapter.createFromResource(this, R.array.price_types, android.R.layout.simple_spinner_item);
        priceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriceType.setAdapter(priceTypeAdapter);

        spinnerPriceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                priceType = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

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

        buttonEditType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextType.setEnabled(true);
            }
        });

        buttonEditCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextCancellation.setEnabled(true);
            }
        });

        buttonEditStandardPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextStandardPrice.setEnabled(true);
            }
        });

        buttonEditPriceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerPriceType.setEnabled(true);
            }
        });

        buttonSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setEnabled(false);
                String name = editTextName.getText().toString();
                AccommodationChangesDTO changes = new AccommodationChangesDTO(name, "", "", -1, -1, "", null, -1, -1);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDescription.setEnabled(false);
                String description = editTextDescription.getText().toString();
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", description, "", -1, -1, "",null, -1, -1);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveAmenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAmenities.setEnabled(false);
                String amenities = editTextAmenities.getText().toString();
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", amenities, -1, -1, "", null, -1, -1);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveMinGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextMinGuests.setEnabled(false);
                String minGuests = editTextMinGuests.getText().toString();
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", Integer.parseInt(minGuests), -1, "", null, -1, -1);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveMaxGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextMaxGuests.setEnabled(false);
                String maxGuests = editTextMaxGuests.getText().toString();
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", -1, Integer.parseInt(maxGuests), "", null, -1, -1);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextType.setEnabled(false);
                String type = editTextType.getText().toString();
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", -1, -1, type,null, -1, -1);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextCancellation.setEnabled(false);
                String cancellation = editTextCancellation.getText().toString();
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", -1, -1, "", null, Integer.parseInt(cancellation), -1);
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSaveStandardPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextStandardPrice.setEnabled(false);
                String standardPrice = editTextStandardPrice.getText().toString();
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", -1, -1, "", null, -1, Double.parseDouble(standardPrice));
                updateAccommodation(token, accommodationId, changes);
            }
        });

        buttonSavePriceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerPriceType.setEnabled(false);
                PriceType price;
                priceType = spinnerPriceType.getSelectedItem().toString();
                if (priceType.equals("Per guest")){
                    price = PriceType.PER_GUEST;
                } else {
                    price = PriceType.PER_UNIT;
                }
                AccommodationChangesDTO changes = new AccommodationChangesDTO("", "", "", -1, -1, "", price, -1, -1);
                updateAccommodation(token, accommodationId, changes);
            }
        });
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
        editTextType.setText(accommodationDisplay.getType());
        editTextMinGuests.setText(String.valueOf(accommodationDisplay.getMinGuests()));
        editTextMaxGuests.setText(String.valueOf(accommodationDisplay.getMaxGuests()));
        editTextCancellation.setText(String.valueOf(accommodationDisplay.getCancellationDeadlineInDays()));
        editTextStandardPrice.setText(String.valueOf(accommodationDisplay.getStandardPrice()));
        setSpinnerSelectionBasedOnPriceType(accommodationDisplay.getPriceType());
    }

    private void setSpinnerSelectionBasedOnPriceType(PriceType priceType) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.price_types, android.R.layout.simple_spinner_item);
        for (int i = 0; i < adapter.getCount(); i++) {
            String spinnerItem = adapter.getItem(i).toString();
            if (priceType == PriceType.PER_GUEST && spinnerItem.equals("PER_GUEST")) {
                spinnerPriceType.setSelection(i);
                return;
            } else if (priceType == PriceType.PER_UNIT && spinnerItem.equals("PER_UNIT")) {
                spinnerPriceType.setSelection(i);
                return;
            }
        }
        spinnerPriceType.setSelection(0);
    }

    private void setEditTextsToFalse(){
        editTextName.setEnabled(false);
        editTextDescription.setEnabled(false);
        editTextAmenities.setEnabled(false);
        editTextType.setEnabled(false);
        editTextMinGuests.setEnabled(false);
        editTextMaxGuests.setEnabled(false);
        editTextCancellation.setEnabled(false);
        editTextStandardPrice.setEnabled(false);
        spinnerPriceType.setEnabled(false);
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
