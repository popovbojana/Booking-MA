package com.example.booking_ma;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.DTO.AvailabilityDisplayDTO;
import com.example.booking_ma.DTO.FavoriteAccommodationDTO;
import com.example.booking_ma.DTO.RateCommentDTO;
import com.example.booking_ma.DTO.ReportedUserReasonDTO;
import com.example.booking_ma.DTO.ReservationDTO;
import com.example.booking_ma.DTO.ReservationDateStringDTO;
import com.example.booking_ma.DTO.ReservationDisplayDTO;
import com.example.booking_ma.DTO.ResponseMessage;
import com.example.booking_ma.adapters.AccommodationAmentiteAdapter;
import com.example.booking_ma.adapters.AccommodationRatingCommentAdapter;
import com.example.booking_ma.adapters.FavoriteAccommodationsAdapter;
import com.example.booking_ma.adapters.GuestPendingReservationsAdapter;
import com.example.booking_ma.fragments.MapFragment;
import com.example.booking_ma.model.RatingComment;
import com.example.booking_ma.service.ServiceUtils;
import com.example.booking_ma.tools.FragmentTransition;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailsScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager supportFragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransition = supportFragmentManager.beginTransaction();
    private MapFragment mapFragment;
    private String aName, aImg, aDescription, aLocationName, aLocationLat, aLocationLong, aReservedDates, aFreeDates, aAmentities;
    private ArrayList<RatingComment> aComments;
    private double aStars;
    private float aPrice;
    private Long aOwnerId, aAccommodationId;
    private ImageView imageViewAccommodationPic;
    private TextView textViewAccommodationName, textViewAccommodationDesc, textViewAccommodationPrice, textViewAccommodationAmenities;
    private RatingBar ratingBarAccommodationStars;
    private Button buttonReserveAccommodation;
    private RecyclerView recyclerViewAccommodationComments, recyclerViewAccommodationAmentites;
    private LinearLayoutManager layoutManager;
    private AccommodationRatingCommentAdapter commentAdapter;
    private AccommodationAmentiteAdapter amentiteAdapter;
    private String textError = "";

    private ImageView imageViewReserveCheckIn, imageViewReserveCheckOut;
    private EditText editTextReserveGuests, editTextReserveCheckIn, editTextReserveCheckOut;
    private Button buttonReservationDialogCancel, buttonReservationDialogReserve;
    private TextView textViewReservationError, textViewReservationAvailabilities;
    private LocalDate checkInDate, checkOutDate;
    private LocalDateTime checkInLocalDateTime, checkOutLocalDateTime;

    private String myRole;
    private String token;
    private Long myId;

    //bojana
    private Button buttonRateAccommodation, buttonRateOwner, buttonReportOwner, buttonAddToFavorites;


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
        buttonReserveAccommodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReservationDialog();
            }
        });

        buttonRateAccommodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentAccommodation();
            }
        });

        buttonRateOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentOwner();
            }
        });

        buttonReportOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogReportOwner();
            }
        });

        buttonAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteAccommodationDTO favoriteAccommodationDTO = new FavoriteAccommodationDTO(myId, aAccommodationId);
                Call<ResponseMessage> call = ServiceUtils.accommodationService(token).addToFavorites(favoriteAccommodationDTO);
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if(response.isSuccessful()) {
                            Log.i("Success", response.body().getMessage());
                            Toast.makeText(AccommodationDetailsScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                onFailure(call, new Throwable(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Log.i("Fail", t.getMessage());
                        Toast.makeText(AccommodationDetailsScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    
    }

    public void showDialogReportOwner(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_reason);

        EditText editTextReason = dialog.findViewById(R.id.editTextReason);
        ReportedUserReasonDTO reason = new ReportedUserReasonDTO(editTextReason.getText().toString());

        Button buttonSave = dialog.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseMessage> call = ServiceUtils.userService(token).reportOwner(aOwnerId, reason);
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if(response.isSuccessful()) {
                            Log.i("Success", response.body().getMessage());
                            Toast.makeText(AccommodationDetailsScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                onFailure(call, new Throwable(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Log.i("Fail", t.getMessage());
                        Toast.makeText(AccommodationDetailsScreen.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                dialog.dismiss();
            }
        });

        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //

    public void showCommentAccommodation(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_rate_comment);

        EditText editTextRating = dialog.findViewById(R.id.editTextRating);
        EditText editTextComment = dialog.findViewById(R.id.editTextComment);
        TextView textViewSearchError = dialog.findViewById(R.id.textViewSearchError);

        Button buttonSave = dialog.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewSearchError.setText("");

                String rating = editTextRating.getText().toString();
                String comment = editTextComment.getText().toString();

                if (rating.equals("")){
                    Log.i("Error", "Rating empty");
                    textViewSearchError.setText("Rating is required!");
                } else if (comment.equals("")) {
                    Log.i("Error", "Comment empty");
                    textViewSearchError.setText("Comment is required!");
                } else if (Float.parseFloat(rating) > 5.0 || Float.parseFloat(rating) < 0.0) {
                    Log.i("Error", "Rating not in range");
                    textViewSearchError.setText("Rating should be between 0 - 5!");
                } else {
                    RateCommentDTO rateCommentDTO = new RateCommentDTO(myId, "FOR_ACCOMMODATION", null, aAccommodationId, Float.parseFloat(rating), comment);

                    Call<ResponseMessage> call = ServiceUtils.ratingCommentService(token).rateComment(rateCommentDTO);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if(response.isSuccessful()) {
                                Log.i("Success", response.body().getMessage());
                                Toast.makeText(AccommodationDetailsScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    onFailure(call, new Throwable(response.errorBody().string()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                            Log.i("Fail", t.getMessage());
                            Toast.makeText(AccommodationDetailsScreen.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });

        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showCommentOwner(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_rate_comment);

        EditText editTextRating = dialog.findViewById(R.id.editTextRating);
        EditText editTextComment = dialog.findViewById(R.id.editTextComment);
        TextView textViewSearchError = dialog.findViewById(R.id.textViewSearchError);

        Button buttonSave = dialog.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewSearchError.setText("");

                String rating = editTextRating.getText().toString();
                String comment = editTextComment.getText().toString();

                if (rating.equals("")){
                    Log.i("Error", "Rating empty");
                    textViewSearchError.setText("Rating is required!");
                } else if (comment.equals("")) {
                    Log.i("Error", "Comment empty");
                    textViewSearchError.setText("Comment is required!");
                } else if (Float.parseFloat(rating) > 5.0 || Float.parseFloat(rating) < 0.0) {
                    Log.i("Error", "Rating not in range");
                    textViewSearchError.setText("Rating should be between 0 - 5!");
                } else {
                    RateCommentDTO rateCommentDTO = new RateCommentDTO(myId, "FOR_OWNER", aOwnerId, null, Float.parseFloat(rating), comment);

                    Call<ResponseMessage> call = ServiceUtils.ratingCommentService(token).rateComment(rateCommentDTO);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if(response.isSuccessful()) {
                                Log.i("Success", response.body().getMessage());
                                Toast.makeText(AccommodationDetailsScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    onFailure(call, new Throwable(response.errorBody().string()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                            Log.i("Fail", t.getMessage());
                            Toast.makeText(AccommodationDetailsScreen.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });

        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //

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

            if (itemId == R.id.itemGuestFavoriteAccommodations) {
                Intent intent = new Intent(this, FavoriteAccommodationsScreen.class);
                startActivity(intent);
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

        //bojana
        buttonRateAccommodation = findViewById(R.id.buttonRateAccommodation);
        buttonRateOwner = findViewById(R.id.buttonRateOwner);
        buttonReportOwner = findViewById(R.id.buttonReportOwner);
        buttonAddToFavorites = findViewById(R.id.buttonAddToFavorites);

    }

    public void getExtras(Intent intent){

        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
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
        aOwnerId = intent.getLongExtra("a_owner_id", 0L);
        aAccommodationId = intent.getLongExtra("a_accommodation_id", 0L);
        token = sharedPreferences.getString("pref_accessToken", "");
        myId = sharedPreferences.getLong("pref_id", 0L);


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
        dialog.setContentView(R.layout.popup_reserve_accommodation);

        imageViewReserveCheckIn = dialog.findViewById(R.id.imageViewReserveCheckIn);
        imageViewReserveCheckOut = dialog.findViewById(R.id.imageViewReserveCheckOut);
        editTextReserveGuests = dialog.findViewById(R.id.editTextReserveGuests);
        editTextReserveCheckIn = dialog.findViewById(R.id.editTextReserveCheckIn);
        editTextReserveCheckOut = dialog.findViewById(R.id.editTextReserveCheckOut);
        buttonReservationDialogCancel = dialog.findViewById(R.id.buttonReservationDialogCancel);
        buttonReservationDialogReserve = dialog.findViewById(R.id.buttonReservationDialogReserve);
        textViewReservationAvailabilities = dialog.findViewById(R.id.textViewReservationAvailabilities);
        textViewReservationError = dialog.findViewById(R.id.textViewReservationError);

        imageViewReserveCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckInDatePickerDialog(dialog);
            }
        });

        imageViewReserveCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckOutDatePickerDialog(dialog);
            }
        });

        buttonReservationDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Call<List<AvailabilityDisplayDTO>> call = ServiceUtils.accommodationService(token).getAvailabilitiesPricesBuAccommodatioId(aAccommodationId);

        call.enqueue(new Callback<List<AvailabilityDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<AvailabilityDisplayDTO>> call, Response<List<AvailabilityDisplayDTO>> response) {
                if (response.isSuccessful()) {
                    List<AvailabilityDisplayDTO> availabilitiesPrices = response.body();
                    String availabilityPricesString = "Availabilites: \n";
                    for(AvailabilityDisplayDTO a : availabilitiesPrices){
                        availabilityPricesString += ("Amount: " + String.valueOf(a.getAmount()) + "\n" +
                                "From: " + a.getDateFrom().toString() + "\n" +
                                "Until: "+ a.getDateUntil().toString() + "\n\n  ");
                    }
                    textViewReservationAvailabilities.setText(availabilityPricesString);

                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AvailabilityDisplayDTO>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });


       buttonReservationDialogReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseMessage> call = ServiceUtils.reservationService(token).createReservation(new ReservationDateStringDTO(myId, aOwnerId, aAccommodationId, editTextReserveCheckIn.getText().toString(), editTextReserveCheckOut.getText().toString(), Integer.parseInt(editTextReserveGuests.getText().toString()), aPrice*Integer.parseInt(editTextReserveGuests.getText().toString())));
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if (response.isSuccessful()) {
                            Log.i("Success", response.body().getMessage());
                            Toast.makeText(AccommodationDetailsScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Log.i("Fail", t.getMessage());
                    }
                });
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

                month = month + 1;


                checkInDate = LocalDate.of(year, month, day);
                if (selectedDate.compareTo(Calendar.getInstance()) < 0) {
                    Log.e("DatePicker", "Selected date is in the past.");
                    textViewReservationError = dialog.findViewById(R.id.textViewReservationError);
                    textViewReservationError.setText("Incorrect date");
                    editTextReserveCheckIn.setText("");
                    textError = "Incorrect date";
                    buttonReservationDialogReserve.setClickable(false);

                    return;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                String formattedDate = checkInDate.atStartOfDay().format(formatter);
//                String formattedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(selectedDate.getTime());


                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                checkInLocalDateTime = LocalDateTime.parse(formattedDate, inputFormatter);

                editTextReserveCheckIn = dialog.findViewById(R.id.editTextReserveCheckIn);
                if (editTextReserveCheckIn != null) {
                    textViewReservationError.setText("");
                    editTextReserveCheckIn.setText(formattedDate);
                    textError = "";
                    buttonReservationDialogReserve.setClickable(true);
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

                month = month + 1;


                checkOutDate = LocalDate.of(year, month, day);
                if (checkInDate == null) {
                    Log.e("DatePicker", "First select the check in date");
                    textViewReservationError.setText("First select the check in date");
                    editTextReserveCheckOut.setText("");
                    textError = "First select the check in date";
                    buttonReservationDialogReserve.setClickable(false);


                    return;
                }

                if (checkOutDate.isBefore(checkInDate.plusDays(1))) {
                    Log.e("DatePicker", "Selected date is before check in");
                    textViewReservationError = dialog.findViewById(R.id.textViewReservationError);
                    textViewReservationError.setText("Selected date is before check in");
                    editTextReserveCheckOut.setText("");
                    textError = "Selected date is before check in";
                    buttonReservationDialogReserve.setClickable(false);


                    return;
                }


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                String formattedDate = checkOutDate.atStartOfDay().format(formatter);

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                checkOutLocalDateTime = LocalDateTime.parse(formattedDate, inputFormatter);

//                String formattedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(selectedDate.getTime());
                editTextReserveCheckOut = dialog.findViewById(R.id.editTextReserveCheckOut);
                if (editTextReserveCheckOut != null) {
                    textViewReservationError.setText("");
                    editTextReserveCheckOut.setText(formattedDate);
                    textError = "";
                    buttonReservationDialogReserve.setClickable(true);
                }
            }
        }, year, month, day);

        datePickerDialog.show();
    }

}
