package com.example.workoutsets.helper;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    private static final String FILE_NAME = "profile.txt";

    // Create the file and ensure it contains "unknown" if it's empty
    public static void createFileIfNotExists(Context context) {
        List<String> lines = readFromFile(context);
        if (lines.isEmpty()) {
            writeToFile(context, "unknown");
        }
    }

    // Create or overwrite the file with the provided content
    public static void writeToFile(Context context, String content) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            Log.e("FileIO", "Error: writeToFile failed", e);
        }
    }

    // Read content from the file
    public static List<String> readFromFile(Context context) {
        List<String> lines = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(FILE_NAME)) {
            int character;
            StringBuilder sb = new StringBuilder();
            while ((character = fis.read()) != -1) {
                sb.append((char) character);
            }
            lines.add(sb.toString());
        } catch (IOException e) {
            Log.e("FileIO", "Error: readFromFile failed", e);
        }
        return lines;
    }
}