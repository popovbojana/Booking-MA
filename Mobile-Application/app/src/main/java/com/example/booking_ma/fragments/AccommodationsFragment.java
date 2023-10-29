package com.example.booking_ma.fragments;

import android.os.Bundle;
import android.util.Log;
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
        accomodation1Comments.add(new Comment(accommodation1, new Guest("Name 1", "Surname 1"), "Accommodation1, Great place to stay!", 4.0f));
        accomodation1Comments.add(new Comment(accommodation1, new Guest("Name 2", "Surname 2"), "Accommodation1, Awesome accommodation!", 5.0f));

        List<Comment> accomodation2Comments = new ArrayList<>();
        accomodation2Comments.add(new Comment(accommodation2, new Guest("Name 1", "Surname 1"), "Accommodation2, Great place to stay!", 4.0f));
        accomodation2Comments.add(new Comment(accommodation2, new Guest("Name 2", "Surname 2"), "Accommodation2, Awesome accommodation!", 5.0f));
        accomodation2Comments.add(new Comment(accommodation2, new Guest("Name 3", "Surname 3"), "Accommodation2, O my God!", 3.5f));

        List<Comment> accomodation3Comments = new ArrayList<>();
        accomodation3Comments.add(new Comment(accommodation3, new Guest("Name 1", "Surname 1"), "Accommodation3, Great place to stay!", 4.0f));

        accommodation1.setComments(accomodation1Comments);
        accommodation2.setComments(accomodation2Comments);
        accommodation3.setComments(accomodation3Comments);

        List<String> accommodation1Amentites = new ArrayList<>();
        accommodation1Amentites.add("Ovo je amentitie 1");

        List<String> accommodation2Amentites = new ArrayList<>();
        accommodation2Amentites.add("Ovo je amentite 1");
        accommodation2Amentites.add("Ovo je amentite 2");

        List<String> accommodation3Amentites = new ArrayList<>();
        accommodation3Amentites.add("Ovo je amentite 1");
        accommodation3Amentites.add("Ovo je amentite 2");
        accommodation3Amentites.add("Ovo je amentite 3");

        accommodation1.setAmentites(accommodation1Amentites);
        accommodation2.setAmentites(accommodation2Amentites);
        accommodation3.setAmentites(accommodation3Amentites);

        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        accommodationList.add(accommodation3);

        for(Accommodation a : accommodationList){
            Log.i("Accommodation name: ", a.getName());

            for(Comment c : a.getComments()){
                Log.i("User:", c.getUser().getName() + ", comment: " + c.getMessage() + " stars: " +  c.getStars());
            }

            for(String s: a.getAmentites()){
                Log.i("Amentite:", s);
            }
        }

        return accommodationList;
    }
}



