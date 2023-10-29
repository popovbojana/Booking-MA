package com.example.booking_ma;

import android.content.Intent;
import android.location.Address;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.adapters.AccommodationAdapter;
import com.example.booking_ma.adapters.AccommodationAmentiteAdapter;
import com.example.booking_ma.adapters.AccommodationCommentAdapter;
import com.example.booking_ma.fragments.AccommodationsFragment;
import com.example.booking_ma.fragments.MapFragment;
import com.example.booking_ma.model.Comment;
import com.example.booking_ma.tools.FragmentTransition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccommodationDetailsScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager supportFragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransition = supportFragmentManager.beginTransaction();
    private MapFragment mapFragment;
    private String aName, aImg, aDescription, aLocationName, aLocationLat, aLocationLong, aReservedDates, aFreeDates;
    private ArrayList<Comment> aComments;
    private ArrayList<String> aAmentities;
    private float aPrice, aStars;
    private ImageView imageViewAccommodationPic;
    private TextView textViewAccommodationName, textViewAccommodationDesc, textViewAccommodationPrice, textViewAccommodationAmenities;
    private RatingBar ratingBarAccommodationStars;
    private Button buttonReserveAccommodation;
    private RecyclerView recyclerViewAccommodationComments, recyclerViewAccommodationAmentites;
    private LinearLayoutManager layoutManager;
    private AccommodationCommentAdapter commentAdapter;
    private AccommodationAmentiteAdapter amentiteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

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
        inflater.inflate(R.menu.menu_accommodation_details_screen, menu);
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

    public void setCoordinate(View view){
        Geocoder geocoder = new Geocoder(this);
    }

    public void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");
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
        aComments = (ArrayList<Comment>) intent.getSerializableExtra("a_comments");
        aLocationName = intent.getStringExtra("a_location_name");
        aLocationLat = intent.getStringExtra("a_location_lat");
        aLocationLong = intent.getStringExtra("a_location_long");
        aReservedDates = intent.getStringExtra("a_reserved_dates");
        aFreeDates = intent.getStringExtra("a_free_dates");
        aAmentities = (ArrayList<String>) intent.getSerializableExtra("a_amentites");
    }

    public void setPageParts(){

        textViewAccommodationName.setText(aName);
        textViewAccommodationDesc.setText(aDescription);
        textViewAccommodationPrice.setText(String.valueOf(aPrice));
        ratingBarAccommodationStars.setRating(aStars);

        setAccommodationCommentAdapter();
        setAccommodationAmentiteAdapter();
    }

    public void setAccommodationCommentAdapter(){

        recyclerViewAccommodationComments = findViewById(R.id.recyclerViewAccommodationComments);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAccommodationComments.setLayoutManager(layoutManager);

        commentAdapter = new AccommodationCommentAdapter(this, aComments);
        recyclerViewAccommodationComments.setAdapter(commentAdapter);
    }

    public void setAccommodationAmentiteAdapter(){

        recyclerViewAccommodationAmentites= findViewById(R.id.recyclerViewAccommodationAmentites);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAccommodationAmentites.setLayoutManager(layoutManager);

        amentiteAdapter = new AccommodationAmentiteAdapter(this, aAmentities);
        recyclerViewAccommodationAmentites.setAdapter(amentiteAdapter);

    }
}
