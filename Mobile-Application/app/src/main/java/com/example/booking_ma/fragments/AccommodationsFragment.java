package com.example.booking_ma.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.DTO.AccommodationDisplayDTO;
import com.example.booking_ma.R;
import com.example.booking_ma.adapters.AccommodationAdapter;
import com.example.booking_ma.model.Accommodation;
import com.example.booking_ma.model.AccommodationChange;
import com.example.booking_ma.model.AvailabilityPrice;
import com.example.booking_ma.model.Comment;
import com.example.booking_ma.model.Guest;
import com.example.booking_ma.model.Owner;
import com.example.booking_ma.model.RatingComment;
import com.example.booking_ma.model.enums.PriceType;
import com.example.booking_ma.model.enums.RatingCommentType;
import com.example.booking_ma.service.IAccommodationService;
import com.example.booking_ma.service.ServiceUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationsFragment extends Fragment {

    private RecyclerView recyclerViewAccommodations;
    private LinearLayoutManager layoutManager;
    private AccommodationAdapter adapter;
    private List<Accommodation> allAccommodations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodations, container, false);

        recyclerViewAccommodations = view.findViewById(R.id.recyclerViewAccommodations);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAccommodations.setLayoutManager(layoutManager);

//        allAccommodations = getAllDummyAccommodations();
//        allAccommodations =
//        adapter = new AccommodationAdapter(getActivity(), allAccommodations);
        getAllAccommodations();
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private void getAllAccommodations() {

        Call<List<AccommodationDisplayDTO>> call = ServiceUtils.accommodationService.getAllAccommodations();

        call.enqueue(new Callback<List<AccommodationDisplayDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationDisplayDTO>> call, Response<List<AccommodationDisplayDTO>> response) {
                if (response != null) {
                    List<AccommodationDisplayDTO> accommodations = response.body();

                    AccommodationAdapter adapter = new AccommodationAdapter(getContext(), accommodations);
                    recyclerViewAccommodations.setAdapter(adapter);
                } else {
                    Log.e("API Error", "Failed to fetch accommodations: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationDisplayDTO>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch accommodations", t);
            }
        });
    }
    private List<Accommodation> getAllDummyAccommodations() {
        List<Accommodation> accommodationList = new ArrayList<>();

        Guest guest1 =  new Guest("Marko", "Markovic");
        Guest guest2 = new Guest("Nikola", "Nikoliic");
        Guest guest3 = new Guest("Aleksandar", "Aleksandrovic");
        Owner owner1 = new Owner("Milica", "Milicevic");
        Owner owner2 = new Owner("Iva", "Ivic");
        Owner owner3 = new Owner("Jovana", "Jovic");

        Accommodation accommodation1 = new Accommodation(
                new Owner(),
                "Accommodation 1",
                "Description 1",
                "Amentites 1",
                1,
                3,
                "Type 1",
                PriceType.PER_GUEST,
                new ArrayList<AvailabilityPrice>(),
                3,
                true,
                false,
                new AccommodationChange(),
                new ArrayList<RatingComment>(),
                "Accommodation 1 address",
                12.3456,
                78.9012,
                3.0);

        Accommodation accommodation2 = new Accommodation(
                new Owner(),
                "Accommodation 2",
                "Description 2",
                "Amentites 2",
                2,
                3,
                "Type 2",
                PriceType.PER_GUEST,
                new ArrayList<AvailabilityPrice>(),
                5,
                true,
                false,
                new AccommodationChange(),
                new ArrayList<RatingComment>(),
                "Accommodation 2 address",
                12.3456,
                78.9012,
                4.2);

        Accommodation accommodation3 = new Accommodation(
                new Owner(),
                "Accommodation 3",
                "Description 3",
                "Amentites 3",
                3,
                5,
                "Type 3",
                PriceType.PER_GUEST,
                new ArrayList<AvailabilityPrice>(),
                2,
                true,
                false,
                new AccommodationChange(),
                new ArrayList<RatingComment>(),
                "Accommodation 3 address",
                12.3456,
                78.9012,
                5.0);;

        List<RatingComment> accomodation1Comments = new ArrayList<>();
        accomodation1Comments.add(new RatingComment(1L, RatingCommentType.FOR_ACCOMMODATION, guest1, owner1, accommodation1, 3, "Accommodation1, Great place to stay", LocalDateTime.now(), true, false));
        accomodation1Comments.add(new RatingComment(2L, RatingCommentType.FOR_ACCOMMODATION, guest2, owner1, accommodation1, 2, "Accommodation1, Awful place to stay", LocalDateTime.now(), true, false));

        List<RatingComment> accomodation2Comments = new ArrayList<>();
        accomodation1Comments.add(new RatingComment(3L, RatingCommentType.FOR_ACCOMMODATION, guest1, owner2, accommodation1, 5, "Accommodation2, O my Godnes", LocalDateTime.now(), true, false));
        accomodation1Comments.add(new RatingComment(4L, RatingCommentType.FOR_ACCOMMODATION, guest2, owner2, accommodation1, 3, "Accommodation2, Jesus Christ", LocalDateTime.now(), true, false));
        accomodation1Comments.add(new RatingComment(5L, RatingCommentType.FOR_ACCOMMODATION, guest3, owner2, accommodation1, 4, "Accommodation2, What the f", LocalDateTime.now(), true, false));

        List<RatingComment> accomodation3Comments = new ArrayList<>();
        accomodation1Comments.add(new RatingComment(6L, RatingCommentType.FOR_ACCOMMODATION, guest1, owner3, accommodation1, 1, "Accommodation3, GREAT", LocalDateTime.now(), true, false));

        accommodation1.setRatingComments(accomodation1Comments);
        accommodation2.setRatingComments(accomodation2Comments);
        accommodation3.setRatingComments(accomodation3Comments);

        AccommodationChange accommodationChange1 = new AccommodationChange(1L, accommodation1, "Change Accommodation 1", "Change description 1", "Change amentites 1", 1, 3, "Change TYPE", PriceType.PER_GUEST, new ArrayList<AvailabilityPrice>(), 5, 10.0);
        AccommodationChange accommodationChange2 = new AccommodationChange(2L, accommodation1, "Change Accommodation 1", "Change description 1", "Change amentites 1", 1, 3, "Change TYPE", PriceType.PER_GUEST, new ArrayList<AvailabilityPrice>(), 5, 15.0);
        AccommodationChange accommodationChange3 = new AccommodationChange(3L, accommodation1, "Change Accommodation 1", "Change description 1", "Change amentites 1", 1, 3, "Change TYPE", PriceType.PER_GUEST, new ArrayList<AvailabilityPrice>(), 5, 18.0);

        accommodation1.setAccommodationChange(accommodationChange1);
        accommodation2.setAccommodationChange(accommodationChange2);
        accommodation3.setAccommodationChange(accommodationChange3);

        List<AvailabilityPrice> availabilityPrices1 = new ArrayList<>();
        availabilityPrices1.add(new AvailabilityPrice(1L, accommodation1, accommodationChange1, 1000.00, LocalDateTime.now(), LocalDateTime.now().plusDays(5)));

        List<AvailabilityPrice> availabilityPrices2 = new ArrayList<>();
        availabilityPrices2.add(new AvailabilityPrice(2L, accommodation2, accommodationChange2, 2000.00, LocalDateTime.now(), LocalDateTime.now().plusDays(6)));

        List<AvailabilityPrice> availabilityPrices3 = new ArrayList<>();
        availabilityPrices1.add(new AvailabilityPrice(3L, accommodation3, accommodationChange3, 3000.00, LocalDateTime.now(), LocalDateTime.now().plusDays(7)));

        accommodation1.setAvailabilities(availabilityPrices1);
        accommodation2.setAvailabilities(availabilityPrices2);
        accommodation3.setAvailabilities(availabilityPrices3);

        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        accommodationList.add(accommodation3);

        for(Accommodation a : accommodationList){
            Log.i("Accommodation name: ", a.getName());

            for(RatingComment c : a.getRatingComments()){
                Log.i("User:", c.getGuest().getName() + ", comment: " + c.getComment() + " stars: " +  c.getRating());
            }

            for(AvailabilityPrice p : a.getAvailabilities()){
                Log.i("Id:", p.getId()+ ", cost: " + p.getAmount());
            }
        }

        return accommodationList;
    }
}



