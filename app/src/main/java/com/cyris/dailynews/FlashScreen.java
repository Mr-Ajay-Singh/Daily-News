package com.cyris.dailynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class FlashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        Intent intent = getIntent();
        final String extraLink = intent.getStringExtra("link");
        final String extraData = intent.getStringExtra("titleData");


        getSupportActionBar().hide();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(extraData!=null)
                {

                    Intent intent = new Intent(FlashScreen.this,LoadSuggested.class);
                    intent.putExtra("link",extraLink);
                    intent.putExtra("titleData",extraData);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(FlashScreen.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        };
        handler.postDelayed(runnable,500);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
