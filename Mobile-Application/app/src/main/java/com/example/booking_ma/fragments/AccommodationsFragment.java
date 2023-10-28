package com.example.booking_ma.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_ma.R;
import com.example.booking_ma.adapters.AccommodationAdapter;
import com.example.booking_ma.model.Accommodation;
import com.example.booking_ma.model.Comment;
import com.example.booking_ma.model.Guest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        allAccommodations = getAllAccommodations();
        adapter = new AccommodationAdapter(getActivity(), allAccommodations);
        recyclerViewAccommodations.setAdapter(adapter);

        return view;
    }

    private List<Accommodation> getAllAccommodations() {
        List<Accommodation> accommodationList = new ArrayList<>();

        Accommodation accommodation1 = new Accommodation(
                "Accommodation 1",
                R.drawable.item,
                "This is a sample accommodation description.",
                "Accommodation 1 address",
                "12.3456",
                "78.9012",
                new ArrayList<LocalDate>(),
                new ArrayList<LocalDate>(),
                new ArrayList<String>(),
                100.0f,
                4.5f
        );

        Accommodation accommodation2 = new Accommodation(
                "Accommodation 2",
                R.drawable.item,
                "This is a sample accommodation description.",
                "Accommodation 2 address",
                "12.3456",
                "78.9012",
                new ArrayList<LocalDate>(),
                new ArrayList<LocalDate>(),
                new ArrayList<String>(),
                55.0f,
                3.2f
        );

        Accommodation accommodation3 = new Accommodation(
                "Accommodation 3",
                R.drawable.item,
                "This is a sample accommodation description.",
                "Accommodation 3 address",
                "12.3456",
                "78.9012",
                new ArrayList<LocalDate>(),
                new ArrayList<LocalDate>(),
                new ArrayList<String>(),
                55.0f,
                1.8f
        );

        List<Comment> accomodation1Comments = new ArrayList<>();
        accomodation1Comments.add(new Comment(accommodation1, new Guest(), "Accommodation1, Great place to stay!", 4.0f));
        accomodation1Comments.add(new Comment(accommodation1, new Guest(), "Accommodation1, Awesome accommodation!", 5.0f));

        List<Comment> accomodation2Comments = new ArrayList<>();
        accomodation2Comments.add(new Comment(accommodation2, new Guest(), "Accommodation2, Great place to stay!", 4.0f));
        accomodation2Comments.add(new Comment(accommodation2, new Guest(), "Accommodation2, Awesome accommodation!", 5.0f));
        accomodation2Comments.add(new Comment(accommodation2, new Guest(), "Accommodation2, O my God!", 3.5f));

        List<Comment> accomodation3Comments = new ArrayList<>();
        accomodation3Comments.add(new Comment(accommodation3, new Guest(), "Accommodation3, Great place to stay!", 4.0f));

        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        accommodationList.add(accommodation3);

        return accommodationList;
    }
}



