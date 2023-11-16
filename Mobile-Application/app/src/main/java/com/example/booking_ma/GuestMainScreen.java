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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.booking_ma.fragments.AccommodationsFragment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class GuestMainScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView filterIcon;
    private Button buttonCancel, buttonConfirm;
    private Button buttonSearchDialogCancel, buttonSearchDialogSearch;
    private Spinner spinnerBenefits, spinnerTypes;
    private EditText editTextMinPrice, editTextMaxPrice;
    private TextView textViewSearchBar;
    private ImageView imageViewCheckIn, imageViewCheckOut;
    private EditText editTextLocation, editTextGuests, editTextCheckIn, editTextCheckOut;
    private TextView textViewSearchError;
    private LocalDate checkInDate, checkOutDate;
    private String selectedBenefit, selectedType;
    private TextView textViewFilterError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guest_main_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        filterIcon = findViewById(R.id.filterIcon);
        textViewSearchBar = findViewById(R.id.textViewSearchBar);

        if (savedInstanceState == null) {
            AccommodationsFragment fragment = new AccommodationsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        textViewSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });

        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_guest, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.itemGuestMainScreen) {
            Intent intent = new Intent(this, GuestMainScreen.class);
            startActivity(intent);
            return true;
        }

        if (itemId == R.id.itemGuestAccountScreen) {
            Intent intent = new Intent(this, AccountScreen.class);
            startActivity(intent);
            return true;
        }

        if (itemId == R.id.itemGuestReservationsScreen) {
/*            Intent intent = new Intent(this, GuestReservationsScreen.class);
            startActivity(intent);*/
            return true;
        }

        if (itemId == R.id.itemGuestNotificationsScreen) {
/*            Intent intent = new Intent(this, GuestNotificationsScreen.class);
            startActivity(intent);*/
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

    private void search(String search) {
        Log.i("Search size", String.valueOf(search.length()));
        if(search.length() != 0){
            Log.i("Searching for:", search);
            Intent intent = new Intent(GuestMainScreen.this, AccountScreen.class);
            startActivity(intent);
        }
    }

    private void showSearchDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_search_accommodation);

        imageViewCheckIn = dialog.findViewById(R.id.imageViewCheckIn);
        imageViewCheckOut = dialog.findViewById(R.id.imageViewCheckOut);
        editTextLocation = dialog.findViewById(R.id.editTextSearchLocation);
        editTextGuests = dialog.findViewById(R.id.editTextSearchGuests);
        editTextCheckIn = dialog.findViewById(R.id.editTextSearchCheckIn);
        editTextCheckOut = dialog.findViewById(R.id.editTextSearchCheckOut);
        buttonSearchDialogCancel = dialog.findViewById(R.id.buttonSearchDialogCancel);
        buttonSearchDialogSearch = dialog.findViewById(R.id.buttonSearchDialogSearch);
        textViewSearchError = dialog.findViewById(R.id.textViewSearchError);

        imageViewCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckInDatePickerDialog(dialog);
            }
        });

        imageViewCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckOutDatePickerDialog(dialog);
            }
        });

        buttonSearchDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonSearchDialogSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextLocation.getText().toString().equals("")){
                    Log.e("Invalid:", "Location input");
                    textViewSearchError.setText("Invalid location input");
                    return;
                }
                else if(editTextGuests.getText().toString().equals("") || editTextGuests.getText().toString().equals("0")){
                    Log.e("Invalid:", "Guests input");
                    textViewSearchError.setText("Invalid guests input");
                    return;
                }
                else if(editTextCheckIn.getText().toString().equals("")){
                    Log.e("Invalid:", "Check in input");
                    textViewSearchError.setText("Invalid check in input");
                    return;
                }
                else if(editTextCheckOut.getText().toString().equals("")){
                    Log.e("Invalid:", "Check out input");
                    textViewSearchError.setText("Invalid check out input");
                    return;
                }
                else{
                    textViewSearchBar.setText(editTextLocation.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void showCheckInDatePickerDialog(Dialog dialog) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, day);

                checkInDate = LocalDate.of(year, month, day);
                if (selectedDate.compareTo(Calendar.getInstance()) < 0) {
                    Log.e("DatePicker", "Selected date is in the past.");
                    textViewSearchError = dialog.findViewById(R.id.textViewSearchError);
                    textViewSearchError.setText("Incorrect date");
                    return;
                }

                String formattedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(selectedDate.getTime());

                editTextCheckIn = dialog.findViewById(R.id.editTextSearchCheckIn);
                if (editTextCheckIn != null) {
                    textViewSearchError.setText("");
                    editTextCheckIn.setText(formattedDate);
                }
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showCheckOutDatePickerDialog(Dialog dialog) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, day);

                checkOutDate = LocalDate.of(year, month, day);
                if (checkInDate == null) {
                    Log.e("DatePicker", "First select the check in date");
                    textViewSearchError.setText("First select the check in date");
                    return;
                }

                if (checkOutDate.isBefore(checkInDate.plusDays(1))) {
                    Log.e("DatePicker", "Selected date is before check in");
                    textViewSearchError = dialog.findViewById(R.id.textViewSearchError);
                    textViewSearchError.setText("Selected date is before check in");
                    return;
                }

                String formattedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(selectedDate.getTime());
                editTextCheckOut = dialog.findViewById(R.id.editTextSearchCheckOut);
                if (editTextCheckOut != null) {
                    editTextCheckOut.setText(formattedDate);
                }
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showFilterDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_filter_by);

        buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonConfirm = dialog.findViewById(R.id.buttonConfirm);
        spinnerBenefits = dialog.findViewById(R.id.spinnerBenefits);
        spinnerTypes = dialog.findViewById(R.id.spinnerTypes);
        editTextMinPrice = dialog.findViewById(R.id.editTextMinPrice);
        editTextMaxPrice = dialog.findViewById(R.id.editTextMaxPrice);
        textViewFilterError = dialog.findViewById(R.id.textViewFilterError);

        String[] benefitsOptions = {"-","Option 1", "Option 2", "Option 3"};
        ArrayAdapter<String> adapterBenefits = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, benefitsOptions);
        adapterBenefits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBenefits.setAdapter(adapterBenefits);

        String[] typesOptions = {"-", "Option 1", "Option 2", "Option 3"};
        ArrayAdapter<String> adapterTypes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typesOptions);
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(adapterBenefits);

        spinnerBenefits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedBenefit = (String) spinnerBenefits.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedType = (String) spinnerTypes.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtering(dialog);
            }
        });

        dialog.show();
    }

    private void filtering(Dialog dialog)
    {
        String minPriceText = editTextMinPrice.getText().toString();
        String maxPriceText = editTextMaxPrice.getText().toString();

        if (!minPriceText.isEmpty() && !maxPriceText.isEmpty() && selectedBenefit != "-" && selectedType != "-") {
            int minPrice = Integer.parseInt(minPriceText);
            int maxPrice = Integer.parseInt(maxPriceText);

            if (maxPrice >= minPrice) {
                Log.i("Selected benefit:", selectedBenefit);
                Log.i("Selected type:", selectedType);
                Log.i("Min cost:", minPriceText);
                Log.i("Max cost:", maxPriceText);
                dialog.dismiss();
            } else {
                Log.e("Error:", "Max and min");
                textViewFilterError.setText("Invalid max or min");
            }
        } else {
            Log.e("Error:", "Fill out the filter");
            textViewFilterError.setText("Fill you the filter");
        }
    }

    private void deletePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.clear().commit();
    }

}
