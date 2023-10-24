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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.adapters.AccommodationAdapter;
import com.example.booking_ma.model.Accommodation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GuestMainScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerViewAccommodations;
    private LinearLayoutManager layoutManager;
    private AccommodationAdapter adapter;
    private List<Accommodation> allAccomodations;
    private ImageView filterIcon;
    private Button buttonCancel, buttonConfirm;
    private Button buttonSearchDialogCancel, buttonSearchDialogSearch;
    private RadioButton group1Radio1, group1Radio2, group1Radio3, group2Radio1, group2Radio2, group2Radio3, group3Radio1, group3Radio2, group3Radio3;
    private TextView textViewSearchBar;
    private FrameLayout frameLayoutAccomodation;
    private ImageView imageViewCheckIn, imageViewCheckOut;
    private EditText editTextLocation, editTextGuests, editTextCheckIn, editTextCheckOut;
    private TextView textViewSearchError;
    private LocalDate checkInDate, checkOutDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guest_main_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        recyclerViewAccommodations = findViewById(R.id.recyclerViewAccommodations);
        filterIcon = findViewById(R.id.filterIcon);
        textViewSearchBar = findViewById(R.id.textViewSearchBar);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewAccommodations.setLayoutManager(layoutManager);
        allAccomodations = getAllAccommodations();
        adapter = new AccommodationAdapter(this, allAccomodations);
        recyclerViewAccommodations.setAdapter(adapter);
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
        inflater.inflate(R.menu.menu_guest_main_screen, menu);
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
/*            deletePreferences();
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);*/
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

        group1Radio1 = dialog.findViewById(R.id.group1Radio1);
        group1Radio2 = dialog.findViewById(R.id.group1Radio2);
        group1Radio3 = dialog.findViewById(R.id.group1Radio3);
        group2Radio1 = dialog.findViewById(R.id.group2Radio1);
        group2Radio2 = dialog.findViewById(R.id.group2Radio2);
        group2Radio3 = dialog.findViewById(R.id.group2Radio3);
        group3Radio1 = dialog.findViewById(R.id.group3Radio1);
        group3Radio2 = dialog.findViewById(R.id.group3Radio2);
        group3Radio3 = dialog.findViewById(R.id.group3Radio3);
        buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonConfirm = dialog.findViewById(R.id.buttonConfirm);

        group1Radio1.setChecked(true);
        group2Radio1.setChecked(true);
        group3Radio1.setChecked(true);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Confirmed:", "TRUE");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void deletePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.clear().commit();
    }

    public List<Accommodation> getAllAccommodations() {
        List<Accommodation> accommodationList = new ArrayList<>();
        accommodationList.add(new Accommodation(R.drawable.item, "Accommodation 1", "$100 per night", 4.5f));
        accommodationList.add(new Accommodation(R.drawable.item, "Accommodation 1", "$100 per night", 4.5f));
        accommodationList.add(new Accommodation(R.drawable.item, "Accommodation 1", "$100 per night", 4.5f));
        return accommodationList;
    }
}
