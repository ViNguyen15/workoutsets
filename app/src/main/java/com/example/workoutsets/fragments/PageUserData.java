package com.example.workoutsets.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workoutsets.ApiConnection.API_DELETE;
import com.example.workoutsets.ApiConnection.API_GET;
import com.example.workoutsets.R;
import com.example.workoutsets.entity.WorkoutSet;

import java.util.List;


public class PageUserData extends Fragment {

    private LinearLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_user_data, container, false);

        // Find the LinearLayout in the inflated view
        layout = view.findViewById(R.id.myLinearLayout);

        // Fetch data from the API for the first time
        fetchDataFromApi();

        return view;
    }

    private void fetchDataFromApi() {
        // Fetch data from API in a new Thread
        new Thread(() -> {
            API_GET apiGet = new API_GET(getContext());
            List<WorkoutSet> sets = apiGet.getDataFromApi();

            // Update the UI on the main thread
            if (sets != null && getActivity() != null) {
                getActivity().runOnUiThread(() -> updateUI(sets));
            }
        }).start();
    }

    private void updateUI(List<WorkoutSet> sets) {
        // Clear previous views before updating
        layout.removeAllViews();

        // Dynamically add TextView and Button pairs
        for (WorkoutSet set : sets) {
            // Create a horizontal LinearLayout for each row
            LinearLayout horizontalLayout = new LinearLayout(getContext());
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Create the TextView
            TextView textView = new TextView(getContext());
            textView.setText(set.getName() + " weight: " + set.getWeight() +
                    " Reps: " + set.getReps() + " " + set.getDate());
            textView.setTextSize(20);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    0, // Width: take up remaining space
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f // Weight
            ));

            // Create the Delete Button
            Button deleteButton = new Button(getContext());
            deleteButton.setText("Delete");
            deleteButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Set the button click listener to delete the item
            deleteButton.setOnClickListener(v -> {
                deleteWorkoutSet(set.getId());
            });

            // Add the TextView and Delete Button to the horizontal LinearLayout
            horizontalLayout.addView(textView);
            horizontalLayout.addView(deleteButton);

            // Add the horizontal LinearLayout to the main layout
            layout.addView(horizontalLayout);
        }
    }

    private void deleteWorkoutSet(int id) {
        new Thread(() -> {
            API_DELETE apiDelete = new API_DELETE(getContext());
            boolean success = apiDelete.deleteDataFromApi(id); // Your delete method here

            // Update the UI on the main thread after deletion
            if (success && getActivity() != null) {
                getActivity().runOnUiThread(this::fetchDataFromApi); // Refresh data
            } else {
                // Handle failure (show a Toast or update UI accordingly)
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(), "Failed to delete item.", Toast.LENGTH_SHORT).show());
                }
            }
        }).start();
    }
}