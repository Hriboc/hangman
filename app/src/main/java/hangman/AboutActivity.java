package ith.android.bignerdranch.com.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Displays info about the creator
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Display action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_logo);
    }
}
