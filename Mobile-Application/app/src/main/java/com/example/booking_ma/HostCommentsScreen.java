package com.example.booking_ma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.booking_ma.fragments.HostCommentsFragment;

public class HostCommentsScreen extends AppCompatActivity {

    private Toolbar toolbar;

    private String token;
    private Long myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_host_comments);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("pref_accessToken", "");
        myId = sharedPreferences.getLong("pref_id", 0L);

        if (savedInstanceState == null) {
            HostCommentsFragment fragment = new HostCommentsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
        }
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
            startActivity(new Intent(this, HostCommentsScreen.class));
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
