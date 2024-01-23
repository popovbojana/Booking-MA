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

import com.example.booking_ma.fragments.FavoriteAccommodationsFragment;
import com.example.booking_ma.fragments.HostAccommodationsFragment;


public class FavoriteAccommodationsScreen  extends AppCompatActivity {
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
            FavoriteAccommodationsFragment fragment = new FavoriteAccommodationsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
        }
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

        if (itemId == R.id.itemGuestFavoriteAccommodations) {
            Intent intent = new Intent(this, FavoriteAccommodationsScreen.class);
            startActivity(intent);
            return true;
        }

        if (itemId == R.id.itemGuestNotificationsScreen) {
            Intent intent = new Intent(this, NotificationsScreen.class);
            startActivity(intent);
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
