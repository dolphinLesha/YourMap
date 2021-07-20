package com.fogdestination.yourmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void get_start(View view)
    {
        Intent myIntent = new Intent(view.getContext(), MapActivity.class);
        startActivity(myIntent);
//        startActivityForResult(myIntent, 0);
    }
}