package com.example.workoutsets.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workoutsets.R;
import com.example.workoutsets.helper.FileIO;

public class Page1Fragment extends Fragment {

    private EditText nameField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, container, false);

        nameField = view.findViewById(R.id.nameField);
        nameField.setText(FileIO.readFromFile(getContext()).get(0));

        Button saveBtn = view.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(v -> {
            String userInput = nameField.getText().toString();
            FileIO.writeToFile(getContext(),userInput);
            Toast.makeText(getActivity(), "Name Saved", Toast.LENGTH_SHORT).show();
        });



        return view;
    }
}