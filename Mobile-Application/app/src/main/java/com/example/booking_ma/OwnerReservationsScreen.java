package com.example.booking_ma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.booking_ma.fragments.GuestApprovedReservationsFragment;
import com.example.booking_ma.fragments.GuestDeniedReservationsFragment;
import com.example.booking_ma.fragments.GuestPendingReservationsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OwnerReservationsScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String token;
    private SharedPreferences sharedPreferences;
    private Long myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_reservations_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        myId = sharedPreferences.getLong("pref_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new com.example.booking_ma.OwnerReservationsScreen.ViewPagerAdapter(this));

        tabLayout = findViewById(R.id.tabLayout);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getTabTitle(position))
        ).attach();

        int selectedTab = getIntent().getIntExtra("selectedTab", 0);

        TabLayout.Tab tab = tabLayout.getTabAt(selectedTab);
        if (tab != null) {
            tab.select();
        }
    }

    private String getTabTitle(int position) {
        switch (position) {
            case 0:
                return "Pending reservations";
            case 1:
                return "Approved reservations";
            case 2:
                return "Denied reservations";
            default:
                return "";
        }
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull AppCompatActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new GuestPendingReservationsFragment();
                case 1:
                    return new GuestApprovedReservationsFragment();
                case 2:
                    return new GuestDeniedReservationsFragment();
                default:
                    throw new IllegalArgumentException("Invalid position");
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.itemAdminMainScreen) {
//            startActivity(new Intent(this, AdministratorMainScreen.class));
            return true;
        }

        if (itemId == R.id.itemAdminAccountScreen) {
            startActivity(new Intent(this, AccountScreen.class));
            return true;
        }

        if (itemId == R.id.itemAdminReportedCommentsScreen) {
            startActivity(new Intent(this, com.example.booking_ma.CommentsApprovalScreen.class));
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

        return super.onOptionsItemSelected(item);
    }

    private void deletePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.clear().commit();
    }
}
