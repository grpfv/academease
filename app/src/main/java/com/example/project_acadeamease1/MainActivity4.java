package com.example.project_acadeamease1;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        View coursesBtn = findViewById(R.id.sectionBox2);

        // Get the current Firebase user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // If the user is logged in, get the user's name from Firebase Authentication
            String userName = currentUser.getDisplayName(); // Retrieve the user's display name

            // Set "HELLO USER" text with the user's name
            TextView helloUserTextView = findViewById(R.id.User);
            helloUserTextView.setText(userName);

            // Get the current date
            Calendar calendar = Calendar.getInstance();
            CharSequence todayDate = DateFormat.format("EEEE, MMMM d", calendar);

            // Set "Today, Month Day" text with the current date
            TextView todayDateTextView = findViewById(R.id.day);
            todayDateTextView.setText(todayDate);
        }

        coursesBtn.setOnClickListener(v->startActivity(new Intent(MainActivity4.this, Courses.class)));
    }
}
