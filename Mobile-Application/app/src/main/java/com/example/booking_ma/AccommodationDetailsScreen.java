package com.example.booking_ma;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.adapters.AccommodationAmentiteAdapter;
import com.example.booking_ma.adapters.AccommodationRatingCommentAdapter;
import com.example.booking_ma.fragments.MapFragment;
import com.example.booking_ma.model.RatingComment;
import com.example.booking_ma.tools.FragmentTransition;

import java.util.ArrayList;

public class AccommodationDetailsScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager supportFragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransition = supportFragmentManager.beginTransaction();
    private MapFragment mapFragment;
    private String aName, aImg, aDescription, aLocationName, aLocationLat, aLocationLong, aReservedDates, aFreeDates, aAmentities;
    private ArrayList<RatingComment> aComments;
    private double aPrice, aStars;
    private ImageView imageViewAccommodationPic;
    private TextView textViewAccommodationName, textViewAccommodationDesc, textViewAccommodationPrice, textViewAccommodationAmenities;
    private RatingBar ratingBarAccommodationStars;
    private Button buttonReserveAccommodation;
    private RecyclerView recyclerViewAccommodationComments, recyclerViewAccommodationAmentites;
    private LinearLayoutManager layoutManager;
    private AccommodationRatingCommentAdapter commentAdapter;
    private AccommodationAmentiteAdapter amentiteAdapter;

    private String myRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        myRole = sharedPreferences.getString("pref_role", "");

        setContentView(R.layout.activity_accommodation_details_screen);
        setToolbar();
        setMapFragment();
        getPageParts();
        getExtras(intent);
        setPageParts();
    }

    @Override
    protected void onResume() {

        super.onResume();
    
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
            startActivity(new Intent(this, CommentsApprovalScreen.class));
                return true;
            }

            if (itemId == R.id.itemAdminAccommodationsApprovalScreen) {
            startActivity(new Intent(this, AccommodationsApprovalScreen.class));
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

    public void setCoordinate(View view){
        Geocoder geocoder = new Geocoder(this);
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");
    }

    public void setMapFragment(){

        mapFragment = MapFragment.newInstance();
        FragmentTransition.to(mapFragment, this, false);
        fragmentTransition.commit();
    }


    private void getPageParts(){

        imageViewAccommodationPic = findViewById(R.id.imageViewAccommodationPic);
        textViewAccommodationName = findViewById(R.id.textViewAccommodationName);
        textViewAccommodationDesc = findViewById(R.id.textViewAccommodationDesc);
        textViewAccommodationPrice = findViewById(R.id.textViewAccommodationPrice);
        ratingBarAccommodationStars = findViewById(R.id.ratingBarAccommodationStars);
        buttonReserveAccommodation = findViewById(R.id.buttonReserveAccomodation);
        recyclerViewAccommodationComments = findViewById(R.id.recyclerViewAccommodationComments);
        recyclerViewAccommodationAmentites = findViewById(R.id.recyclerViewAccommodationComments);
    }

    public void getExtras(Intent intent){

        aName = intent.getStringExtra("a_name");
        aImg = intent.getStringExtra("a_img");
        aDescription = intent.getStringExtra("a_description");
        aPrice = intent.getFloatExtra("a_price", 0);
        aStars = intent.getFloatExtra("a_stars", 0);
        aComments = (ArrayList<RatingComment>) intent.getSerializableExtra("a_comments");
        aLocationName = intent.getStringExtra("a_location_name");
        aLocationLat = intent.getStringExtra("a_location_lat");
        aLocationLong = intent.getStringExtra("a_location_long");
        aReservedDates = intent.getStringExtra("a_reserved_dates");
        aFreeDates = intent.getStringExtra("a_free_dates");
        aAmentities = intent.getStringExtra("a_amentites");
    }

    public void setPageParts(){

        textViewAccommodationName.setText(aName);
        textViewAccommodationDesc.setText(aDescription);
        textViewAccommodationPrice.setText(String.valueOf(aPrice));
        ratingBarAccommodationStars.setRating(Float.valueOf(String.valueOf(aStars)));

        setAccommodationRatingCommentAdapter();
    }

    public void setAccommodationRatingCommentAdapter(){

        recyclerViewAccommodationComments = findViewById(R.id.recyclerViewAccommodationComments);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAccommodationComments.setLayoutManager(layoutManager);

        commentAdapter = new AccommodationRatingCommentAdapter(this, aComments);
        recyclerViewAccommodationComments.setAdapter(commentAdapter);
    }

    private void showReservationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_search_accommodation);
/*
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

        dialog.show();*/
    }

}
