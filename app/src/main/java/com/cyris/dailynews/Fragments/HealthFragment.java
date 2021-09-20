package com.cyris.dailynews.Fragments;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cyris.dailynews.AsyncTasks.DataFetching;
import com.cyris.dailynews.Database.ConstantsDatabaseName;
import com.cyris.dailynews.Database.TaskEntity;
import com.cyris.dailynews.Links;
import com.cyris.dailynews.R;
import com.cyris.dailynews.Adapters.SwipeAdapter;
import java.net.HttpURLConnection;
import java.util.List;


public class HealthFragment extends Fragment {

    HttpURLConnection urlConnection;
    String type="flip";
    SwipeRefreshLayout swipeRefreshLayout;
    List<TaskEntity> newsList;
    Dialog dialog;
    View view;
    RecyclerView healthRecyclerView;
    SwipeAdapter healthAdapter;
    private DataFetching fetching;
    boolean isdone = false;
    GlideDrawableImageViewTarget imageViewTarget;
    ImageView imageView;
    ActionBar actionBar;

    public HealthFragment() {
        // Required empty public constructor
    }



    public static HealthFragment newInstance(String param1, String param2) {
        HealthFragment fragment = new HealthFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Check","HealthEffectWorking");
        if(!isdone)
            fetching.execute("http://192.168.43.111/call/Operations/responsePostHealth.php");
        else
            fetching.RefreshData();

    }



    @Override
    public void onPause() {
        super.onPause();
        isdone =true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_health, container, false);
        healthRecyclerView = view.findViewById(R.id.healthRecyclerView);
        healthRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fetching = new DataFetching(view,healthRecyclerView, ConstantsDatabaseName.Health);
        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_view);
        imageView = dialog.findViewById(R.id.dialogLoadingImage);

        imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        OnRefresh();
        //...now load that gif which we put inside the drawble folder here with the help of Glide


        //  View viewDialog = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_view, dialog.vie,true);
       // dialog.setContentView(R.layout.dialog_view);


        return view;
            }

    public void OnRefresh()
    {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshHealth);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.i("swiping","done");
                fetching = new DataFetching(view,healthRecyclerView, ConstantsDatabaseName.Health);
                fetching.refreshLoading(swipeRefreshLayout);
                fetching.execute(Links.HEALTH);



            }


        });
    }


}
