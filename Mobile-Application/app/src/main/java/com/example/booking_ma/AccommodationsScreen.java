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
import androidx.fragment.app.FragmentTransaction;

import com.example.booking_ma.DTO.AccommodationChangesDTO;
import com.example.booking_ma.DTO.NewAccommodationDTO;
import com.example.booking_ma.DTO.NewAvailabilityPriceDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.fragments.HostAccommodationsFragment;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.service.ServiceUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationsScreen extends AppCompatActivity {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

    private Toolbar toolbar;
    private Button btnAddNew, btnGenerateReport;

    private EditText editTextName, editTextDescription, editTextAmenities, editTextMinGuests, editTextMaxGuests, editTextType, editTextStandardPrice, editTextCancellationDeadline, editTextAddress, editTextLatitude, editTextLongitude;
    private TextView textViewSearchError, accommodationAvailabilities;
    private Button buttonCancel, buttonAdd, buttonAvailabilityPrice;
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
//        editTextLatitude = dialog.findViewById(R.id.editTextLatitude);
//        editTextLongitude = dialog.findViewById(R.id.editTextLongitude);
        accommodationAvailabilities = dialog.findViewById(R.id.accommodationAvailabilities);
        textViewSearchError = dialog.findViewById(R.id.textViewSearchError);

        buttonAvailabilityPrice = dialog.findViewById(R.id.buttonAvailabilityPrice);
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

        buttonAvailabilityPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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
                String availability = accommodationAvailabilities.getText().toString();
                String price = "";
                String dateFrom = "";
                String dateUntil = "";

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
                else if(address.equals("")) {
                    Log.i("Error", "Address empty");
                    textViewSearchError.setText("Address is required!");
                }
//                else if(latitude.equals("")) {
//                    Log.i("Error", "Latitude empty");
//                    textViewSearchError.setText("Latitude is required!");
//                } else if(longitude.equals("")) {
//                    Log.i("Error", "Longitude empty");
//                    textViewSearchError.setText("Longitude is required!");
//                }
                else if (availability.equals("")) {
                    Log.i("Error", "Availability empty");
                    textViewSearchError.setText("Availability is required!");
                }
                else {
                    String[] parts = availability.replace("Available: ", "").split(", price: ");

                    if (parts.length == 2) {
                        String dateRange = parts[0].trim(); // 20/01/2024 - 31/01/2024
                        price = parts[1].trim();     // 20

                        String[] dateParts = dateRange.split(" - ");
                        if (dateParts.length == 2) {
                            dateFrom = dateParts[0].trim(); // 20/01/2024
                            dateUntil = dateParts[1].trim(); // 31/01/2024

                        }

                    }

                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDateFrom = LocalDate.parse(dateFrom, inputFormatter);
                    LocalDate localDateUntil = LocalDate.parse(dateUntil, inputFormatter);

                    LocalDateTime localDateTimeFrom = localDateFrom.atStartOfDay();
                    LocalDateTime localDateTimeUntil = localDateUntil.atStartOfDay();

                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                    String formattedDateTimeFrom = localDateTimeFrom.format(outputFormatter);
                    String formattedDateTimeUntil = localDateTimeUntil.format(outputFormatter);

                    NewAvailabilityPriceDTO newAvailabilityPriceDTO = new NewAvailabilityPriceDTO(Double.parseDouble(price), formattedDateTimeFrom, formattedDateTimeUntil);
                    List<NewAvailabilityPriceDTO> availabilityPriceDTOList = new ArrayList<>();
                    availabilityPriceDTOList.add(newAvailabilityPriceDTO);
                    NewAccommodationDTO newAccommodation;
                    if (priceType.equals("Per guest")) {
                        newAccommodation = new NewAccommodationDTO(name, description, amenities, Integer.parseInt(minGuests), Integer.parseInt(maxGuests), type, PriceType.PER_GUEST, availabilityPriceDTOList, Integer.parseInt(cancellation), address, 45.2, 25.2, 0.0, Double.parseDouble(standardPrice));
//                        newAccommodation = new NewAccommodationDTO(name, description, amenities, Integer.parseInt(minGuests), Integer.parseInt(maxGuests), type, PriceType.PER_GUEST, new ArrayList<NewAvailabilityPriceDTO>(), Integer.parseInt(cancellation), address, Double.parseDouble(latitude), Double.parseDouble(longitude), 0.0, Double.parseDouble(standardPrice));
                    } else {
                        newAccommodation = new NewAccommodationDTO(name, description, amenities, Integer.parseInt(minGuests), Integer.parseInt(maxGuests), type, PriceType.PER_UNIT, availabilityPriceDTOList, Integer.parseInt(cancellation), address, 45.2, 25.2, 0.0, Double.parseDouble(standardPrice));
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
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDateTimeFrom = dateTimeFrom.format(formatter);
                        String formattedDateTimeTo = dateTimeTo.format(formatter);
                        accommodationAvailabilities.setText("Available: " + formattedDateTimeFrom + " - " + formattedDateTimeTo + ", price: " + price);
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
}
