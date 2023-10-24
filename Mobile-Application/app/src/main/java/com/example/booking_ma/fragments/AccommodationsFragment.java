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
        accommodationList.add(new Accommodation(1L, R.drawable.item, "Accommodation 1", "$100 per night", 4.5f));
        accommodationList.add(new Accommodation(2L, R.drawable.item, "Accommodation 2", "$120 per night", 4.8f));
        accommodationList.add(new Accommodation(3L, R.drawable.item, "Accommodation 3", "$150 per night", 5.0f));
        return accommodationList;
    }
}



