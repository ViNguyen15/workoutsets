package com.example.workoutsets.ApiConnection;

import com.example.workoutsets.entity.WorkoutSet;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API_POST {

    private static final String URL_STRING = "http://localhost:8080/addset"; // Your API URL


    public void postData(WorkoutSet myData) {
        new Thread(() -> {
            try {
                URL url = new URL(URL_STRING);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // Create JSON object
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("name", myData.getName());
                jsonInput.put("date", myData.getDate());
                jsonInput.put("time", myData.getTime());
                jsonInput.put("weight", myData.getWeight());
                jsonInput.put("reps", myData.getReps());
                jsonInput.put("stage", myData.getStage());


                // Write request body
                try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
                    os.writeBytes(jsonInput.toString());
                    os.flush();
                }

                // Read response
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Response: " + response.toString());
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
