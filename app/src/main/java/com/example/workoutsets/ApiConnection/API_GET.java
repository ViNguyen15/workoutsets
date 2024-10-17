package com.example.workoutsets.ApiConnection;

import com.example.workoutsets.Security.Secure;
import com.example.workoutsets.entity.WorkoutSet;
import com.example.workoutsets.fragments.Page2Fragment;
import com.example.workoutsets.helper.FileIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.Context;
import android.os.Handler;

public class API_GET {

    private Context context;

    public API_GET(Context context){
        this.context = context;
    }

    public List<WorkoutSet> getDataFromApi() {
        String address = Secure.urlGet + FileIO.readFromFile(context).get(0);
        System.out.println("Address = "+address);
        List<WorkoutSet> myDataList = new ArrayList<>();

        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", Secure.token);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            // Check the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    WorkoutSet myData = new WorkoutSet();
                    myData.setId(jsonObject.getInt("id"));
                    myData.setName(jsonObject.getString("name"));
                    myData.setWeight(jsonObject.getInt("weight"));
                    myData.setReps(jsonObject.getInt("reps"));
                    myData.setDate(jsonObject.getString("date"));
                    myData.setTime(jsonObject.getString("time"));
                    myData.setStage(jsonObject.getString("stage"));

                    myDataList.add(myData);
                }

                // Process the response (for example, print it)
                System.out.println("Response: " + response.toString());
                for (WorkoutSet set : myDataList) {
                    System.out.println(set);
                }
            } else {
                System.out.println("GET request not worked: " + responseCode);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return myDataList; // Return the list
    }


    public void GetApi() {
        String address = Secure.urlGet;
        Scanner in = null;

        try {
            URL url = new URL(address);
            in = new Scanner(url.openStream());

            String allText = in.next();
            System.out.println(allText);

        }catch (Exception e){
            System.out.println(e);
            System.out.println("doesnt work");
        }
    }

}
