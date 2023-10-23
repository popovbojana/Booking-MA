package com.example.booking_ma;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.adapters.AccommodationAdapter;
import com.example.booking_ma.model.Accommodation;

import java.util.ArrayList;
import java.util.List;

public class GuestMainScreen extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guest_main_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        // Initialize your RecyclerView and layout manager
        RecyclerView recyclerView = findViewById(R.id.accommodationsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Create a list of AccommodationItem objects
        List<Accommodation> accommodationList = new ArrayList<>();
        accommodationList.add(new Accommodation(R.drawable.item, "Accommodation 1", "$100 per night", 4.5f));
        accommodationList.add(new Accommodation(R.drawable.item, "Accommodation 1", "$100 per night", 4.5f));
        accommodationList.add(new Accommodation(R.drawable.item, "Accommodation 1", "$100 per night", 4.5f));

        // Add more items to the list as needed

        // Create and set the adapter for the RecyclerView
        AccommodationAdapter adapter = new AccommodationAdapter(this, accommodationList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_account_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
