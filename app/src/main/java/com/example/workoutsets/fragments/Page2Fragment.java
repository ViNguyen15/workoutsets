package com.example.workoutsets.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workoutsets.ApiConnection.API_GET;
import com.example.workoutsets.R;
import com.example.workoutsets.entity.WorkoutSet;
import com.example.workoutsets.helper.MyAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Page2Fragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<WorkoutSet> workoutSetList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page2, container, false);

        recyclerView = view.findViewById(R.id.recycler_view); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        workoutSetList = new ArrayList<>(); // Initialize the list

        System.out.println("Mochi is Round");

        fetchData(); // Fetch data from the API

        return view;
    }
    private void fetchData() {
        System.out.println("Fetching data now");
        new Thread(() -> {
            API_GET apiClient = new API_GET();
            List<WorkoutSet> data = apiClient.getDataFromApi(); // Get data without callback

            if (data != null && !data.isEmpty()) {
                workoutSetList.clear(); // Clear the list before adding new data
                workoutSetList.addAll(data); // Add the fetched data

                // Convert WorkoutSet objects to a string representation
                List<String> itemList = new ArrayList<>();
                for (WorkoutSet set : workoutSetList) {
                    itemList.add(set.toString()); // Modify toString() if necessary
                }

                // Update the RecyclerView on the UI thread
                getActivity().runOnUiThread(() -> {
                    myAdapter = new MyAdapter(itemList, getContext());
                    recyclerView.setAdapter(myAdapter);
                });
            } else {
                // Handle empty or null data
                System.out.println("No data received.");
            }
        }).start();
    }
}