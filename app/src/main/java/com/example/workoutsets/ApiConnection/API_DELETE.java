package com.example.workoutsets.ApiConnection;

import android.content.Context;

import com.example.workoutsets.Security.Secure;

import java.net.HttpURLConnection;
import java.net.URL;

public class API_DELETE {
    private Context context;

    public API_DELETE(Context context) {
        this.context = context;
    }

    public boolean deleteDataFromApi(int id) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Secure.urlDelete + id);
            System.out.println("URL: " + url);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Authorization", Secure.token);

            int responseCode = urlConnection.getResponseCode();
            System.out.println(responseCode);
            return responseCode == 200; // Check if deletion was successful

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}