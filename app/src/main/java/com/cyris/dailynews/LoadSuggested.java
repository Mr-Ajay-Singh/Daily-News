package com.cyris.dailynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyris.dailynews.Adapters.FlipAdapter;
import com.cyris.dailynews.AsyncTasks.DataFetching;
import com.cyris.dailynews.Database.ConstantsDatabaseName;

public class LoadSuggested extends AppCompatActivity {

    RecyclerView recyclerView;
    FlipAdapter adapter;
    DataFetching fetching;
    String data,pointedData;
    String databaseName;
    ImageView loadingImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_load_suggested);
        LayoutInflater inflater = LayoutInflater.from(this);
        recyclerView = findViewById(R.id.recyclerViewLoadSuggested);
        loadingImage = findViewById(R.id.loadingImage);
        Intent intent = getIntent();
        View view =inflater.inflate(R.layout.fragment_buisness, (ViewGroup) recyclerView.getRootView(), false);
        data = intent.getStringExtra("link");
        pointedData = intent.getStringExtra("titleData");

        final AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.loading_vector_animated);
        loadingImage.setImageDrawable(animationDrawable);
        animationDrawable.start();


        recyclerView = findViewById(R.id.recyclerViewLoadSuggested);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        if(pointedData!=null)
        {
            fetching = new DataFetching(view,recyclerView,ConstantsDatabaseName.Extra);
            fetching.setExtraLink(pointedData);
            fetching.execute(data);
        }
        else
        {
            fetching = new DataFetching(view,recyclerView,ConstantsDatabaseName.Suggested);
            fetching.execute(data);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(pointedData!=null)
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        this.finish();
    }
}
