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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.booking_ma.DTO.NewAccommodationDTO;
import com.example.booking_ma.DTO.NewAvailabilityPriceDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.fragments.HostAccommodationsFragment;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationsScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnAddNew, btnGenerateReport;

    private EditText editTextName, editTextDescription, editTextAmenities, editTextMinGuests, editTextMaxGuests, editTextType, editTextStandardPrice, editTextCancellationDeadline, editTextAddress, editTextLatitude, editTextLongitude;
    private TextView textViewSearchError;
    private Button buttonCancel, buttonAdd;
    private Spinner spinnerPriceType;

    private String token;
    private Long myId;

    private String priceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accommodations_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");

        btnAddNew = findViewById(R.id.btnAddNew);
        btnGenerateReport = findViewById(R.id.btnGenerateReport);

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("pref_accessToken", "");
        myId = sharedPreferences.getLong("pref_id", 0L);

        if (savedInstanceState == null) {
            HostAccommodationsFragment fragment = new HostAccommodationsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewDialog();
            }
        });

        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
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

    private void showAddNewDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_add_new_accommodation);
        dialog.show();

        spinnerPriceType = dialog.findViewById(R.id.spinnerPriceType);
        ArrayAdapter<CharSequence> priceTypeAdapter = ArrayAdapter.createFromResource(this, R.array.price_types, android.R.layout.simple_spinner_item);
        priceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriceType.setAdapter(priceTypeAdapter);

        editTextName = dialog.findViewById(R.id.editTextName);
        editTextDescription = dialog.findViewById(R.id.editTextDescription);
        editTextAmenities = dialog.findViewById(R.id.editTextAmenities);
        editTextMinGuests = dialog.findViewById(R.id.editTextMinGuests);
        editTextMaxGuests = dialog.findViewById(R.id.editTextMaxGuests);
        editTextType = dialog.findViewById(R.id.editTextType);
        editTextStandardPrice = dialog.findViewById(R.id.editTextStandardPrice);
        editTextCancellationDeadline = dialog.findViewById(R.id.editTextCancellationDeadline);
        editTextAddress = dialog.findViewById(R.id.editTextAddress);
        editTextLatitude = dialog.findViewById(R.id.editTextLatitude);
        editTextLongitude = dialog.findViewById(R.id.editTextLongitude);
        textViewSearchError = dialog.findViewById(R.id.textViewSearchError);

        buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonAdd = dialog.findViewById(R.id.buttonAdd);

        spinnerPriceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                priceType = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewSearchError.setText("");

                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();
                String amenities = editTextAmenities.getText().toString();
                String minGuests = editTextMinGuests.getText().toString();
                String maxGuests = editTextMaxGuests.getText().toString();
                String type = editTextType.getText().toString();
                String standardPrice = editTextStandardPrice.getText().toString();
                String cancellation = editTextCancellationDeadline.getText().toString();
                String address = editTextAddress.getText().toString();
                String latitude = editTextLatitude.getText().toString();
                String longitude = editTextLongitude.getText().toString();

                if (name.equals("")){
                    Log.i("Error", "Name empty");
                    textViewSearchError.setText("Name is required!");
                } else if (description.equals("")) {
                    Log.i("Error", "Description empty");
                    textViewSearchError.setText("Description is required!");
                } else if (amenities.equals("")) {
                    Log.i("Error", "Amenities empty");
                    textViewSearchError.setText("Amenities are required!");
                } else if (minGuests.equals("")) {
                    Log.i("Error", "Min. guests is required!");
                    textViewSearchError.setText("Min. guests is required!");
                } else if(maxGuests.equals("")) {
                    Log.i("Error", "Max. guests is required");
                    textViewSearchError.setText("Max. guests is required!");
                } else if (Integer.parseInt(minGuests) > Integer.parseInt(maxGuests)) {
                    Log.i("Error", "Min. guests larger than max guests");
                    textViewSearchError.setText("Min. guests can't be larger than max guests!");
                } else if(type.equals("")) {
                    Log.i("Error", "Type empty");
                    textViewSearchError.setText("Type is required!");
                } else if(standardPrice.equals("")) {
                    Log.i("Error", "Standard price empty");
                    textViewSearchError.setText("Standard price is required!");
                } else if(cancellation.equals("")) {
                    Log.i("Error", "Cancellation deadline empty");
                    textViewSearchError.setText("Cancellation deadline is required!");
                }
//                else if(address.equals("")) {
//                    Log.i("Error", "Address empty");
//                    textViewSearchError.setText("Address is required!");
//                }
//                else if(latitude.equals("")) {
//                    Log.i("Error", "Latitude empty");
//                    textViewSearchError.setText("Latitude is required!");
//                } else if(longitude.equals("")) {
//                    Log.i("Error", "Longitude empty");
//                    textViewSearchError.setText("Longitude is required!");
//                }
                else {
                    NewAccommodationDTO newAccommodation;
                    if (priceType.equals("Per guest")) {
                        newAccommodation = new NewAccommodationDTO(name, description, amenities, Integer.parseInt(minGuests), Integer.parseInt(maxGuests), type, PriceType.PER_GUEST, new ArrayList<NewAvailabilityPriceDTO>(), Integer.parseInt(cancellation), "ADRESA", 45.2, 25.2, 0.0, Double.parseDouble(standardPrice));
//                        newAccommodation = new NewAccommodationDTO(name, description, amenities, Integer.parseInt(minGuests), Integer.parseInt(maxGuests), type, PriceType.PER_GUEST, new ArrayList<NewAvailabilityPriceDTO>(), Integer.parseInt(cancellation), address, Double.parseDouble(latitude), Double.parseDouble(longitude), 0.0, Double.parseDouble(standardPrice));
                    } else {
                        newAccommodation = new NewAccommodationDTO(name, description, amenities, Integer.parseInt(minGuests), Integer.parseInt(maxGuests), type, PriceType.PER_UNIT, new ArrayList<NewAvailabilityPriceDTO>(), Integer.parseInt(cancellation), "ADRESA", 45.2, 25.2, 0.0, Double.parseDouble(standardPrice));
//                        newAccommodation = new NewAccommodationDTO(name, description, amenities, Integer.parseInt(minGuests), Integer.parseInt(maxGuests), type, PriceType.PER_UNIT, new ArrayList<NewAvailabilityPriceDTO>(), Integer.parseInt(cancellation), address, Double.parseDouble(latitude), Double.parseDouble(longitude), 0.0, Double.parseDouble(standardPrice));
                    }

                    Call<ResponseMessage> call = ServiceUtils.accommodationService(token).addNewAccommodation(myId, newAccommodation);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if (response.isSuccessful()) {
                                Log.i("Success", response.body().getMessage());
                                Toast.makeText(AccommodationsScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
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

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
