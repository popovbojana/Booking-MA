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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.booking_ma.fragments.AccommodationsFragment;
import com.example.booking_ma.fragments.MapFragment;
import com.example.booking_ma.tools.FragmentTransition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class AccommodationDetailsScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager supportFragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransition = supportFragmentManager.beginTransaction();
    private MapFragment mapFragment;
    private String aName, aImg, aDescription, aComments, aLocationName, aLocationLat, aLocationLong, aReservedDates, aFreeDates, aAmentities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accommodation_details_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");
        mapFragment = MapFragment.newInstance();
        FragmentTransition.to(mapFragment, this, false);

        fragmentTransition.commit();

        Intent intent = getIntent();
        getExtras(intent);
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

    public void getExtras(Intent intent){

        aName = intent.getStringExtra("a_name");
        aImg = intent.getStringExtra("a_img");
        aDescription = intent.getStringExtra("a_description");
        aComments = intent.getStringExtra("a_comments");
        aLocationName = intent.getStringExtra("a_location_name");
        aLocationLat = intent.getStringExtra("a_location_lat");
        aLocationLong = intent.getStringExtra("a_location_long");
        aReservedDates = intent.getStringExtra("a_reserved_dates");
        aFreeDates = intent.getStringExtra("a_free_dates");
        aAmentities = intent.getStringExtra("a_amentities");
    }

}
