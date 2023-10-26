package com.example.booking_ma;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.booking_ma.fragments.AccommodationsFragment;
import com.example.booking_ma.fragments.MapFragment;
import com.example.booking_ma.tools.FragmentTransition;

public class AccommodationDetailsScreen extends AppCompatActivity {

    private FragmentManager supportFragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransition = supportFragmentManager.beginTransaction();
    private MapFragment mapFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accommodation_details_screen);

        mapFragment = MapFragment.newInstance();
        FragmentTransition.to(mapFragment, this, false);

        fragmentTransition.commit();
    }
}
