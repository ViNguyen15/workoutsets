package com.example.workoutsets;

import android.graphics.Matrix;
import android.os.Bundle;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutsets.ApiConnection.API_GET;
import com.example.workoutsets.ApiConnection.API_POST;
import com.example.workoutsets.entity.WorkoutSet;
import com.example.workoutsets.fragments.Page1Fragment;
import com.example.workoutsets.fragments.Page2Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    API_POST apiPost = new API_POST();
    API_GET apiGet = new API_GET();
    WorkoutSet mySet;
    String name = "Jimmy";
    TextView num1;
    TextView num2;
    TextView num3;
    TextView reps;
    String stage = "workout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button submitBtn = findViewById(R.id.submitBtn);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        reps = findViewById(R.id.repsNum);
        RadioGroup stageGroup = findViewById(R.id.stageGroup);

        // Set a listener on the RadioGroup
        stageGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Get the selected RadioButton
            RadioButton selectedRadioButton = findViewById(checkedId);

            // Show a message or perform an action based on the selection
            stage = selectedRadioButton.getText().toString();
            Toast.makeText(this, stage, Toast.LENGTH_SHORT).show();
        });



        submitBtn.setOnClickListener(v -> {

            System.out.println("Hello Mochi! " + num1.getText());
            String[] dateTime = timeStamp().split(" ");
            String date = dateTime[0];
            String time = dateTime[1];

            System.out.println("Date: " + date);
            System.out.println("Time: " + time);
            System.out.println("Reps done: " + repsDone());
            int weight = trueWeight();
            int reps = repsDone();

            mySet = new WorkoutSet(name,date,time,weight,reps,stage);
            System.out.println(mySet);
            Toast.makeText(this, mySet.toString(), Toast.LENGTH_SHORT).show();
            apiPost.postData(mySet);

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
    }

    public int trueWeight(){
        String num = "" + num3.getText() + num2.getText() + num1.getText();
        System.out.println("Weight is: " + num);
        return Integer.parseInt(num);
    }

    public void upPressed(TextView tv){
        int num = Integer.parseInt((String) tv.getText());
        System.out.println(num);
        num++;
        if(num >= 10){
            num = 0;
        }
        tv.setText(Integer.toString(num));
    }

    public void repPressed(TextView tv, String update){
        int num = Integer.parseInt((String) tv.getText());
        System.out.println(num);
        if(update.equals("increase")){
            num++;
        }else{
            num--;
            if (num < 0){
                num=0;
            }
        }

        tv.setText(Integer.toString(num));
    }

    public void downPressed(TextView tv){
        int num = Integer.parseInt((String) tv.getText());
        System.out.println(num);
        num--;
        if(num < 0){
            num = 9;
        }
        tv.setText(Integer.toString(num));
    }

    public void byFive(TextView tv){
        int num = Integer.parseInt((String) tv.getText());
        if(num != 0) {
            num = 0;
        }else{
            num = 5;
        }
        tv.setText(Integer.toString(num));
    }


    // all up arrows 3 is left
    public void upArrow3Press(View v){
        upPressed(num3);
    }

    public void upArrow2Press(View v){
        upPressed(num2);
    }

    public void upArrow1Press(View v){
        byFive(num1);
    }

    // all down arrows 3 is left
    public void downArrow3Press(View v){
        downPressed(num3);
    }

    public void downArrow2Press(View v){
        downPressed(num2);
    }

    public void downArrow1Press(View v){
        byFive(num1);
    }

    public void onDecreaseRepPress(View v){
        repPressed(reps,"decrease");
    }

    public void onIncreaseRepPress(View v){
        repPressed(reps,"increase");
    }

    public String timeStamp(){
        // Get the current date and time
        Date currentDate = new Date();

        // Format the date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(currentDate);

        System.out.println("current time is " + formattedDateTime);
        return formattedDateTime;
    }

    public int repsDone(){
        return Integer.parseInt(reps.getText() + "");
    }



    // Method called when the menu button is clicked
    public void showPopupMenu(View view) {
        // Create a PopupMenu
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

        // Set up menu item click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String title = item.getTitle().toString(); // Get the title of the clicked item
                switch (title) {
                    case "Open Page 1":
                        replaceFragment(new Page1Fragment());
                        return true;
                    case "Open Page 2":
                        replaceFragment(new Page2Fragment());
                        return true;
                    default:
                        return false;
                }
            }
        });

        // Show the popup menu
        popup.show();
    }

    // Helper method to replace the fragment
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}