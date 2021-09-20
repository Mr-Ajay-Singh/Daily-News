package com.cyris.dailynews.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cyris.dailynews.Adapters.SwipeAdapter;
import com.cyris.dailynews.AsyncTasks.DataFetching;
import com.cyris.dailynews.Database.ConstantsDatabaseName;
import com.cyris.dailynews.Database.DataBase;
import com.cyris.dailynews.Database.TaskEntity;
import com.cyris.dailynews.Links;
import com.cyris.dailynews.R;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlipEffect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlipEffect extends Fragment {

    String type="flip";
    List<TaskEntity> newsList;
    androidx.recyclerview.widget.RecyclerView flipEffectRecyclerView;
    SwipeAdapter flipAdapter;
    View view;
    ActionBar actionBar;
    DataBase dataBase;
    DataFetching fetching;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isdone = false;
    private GlideDrawableImageViewTarget imageViewTarget;


    public FlipEffect() {
        // Required empty public constructor
    }

    public static FlipEffect newInstance(String param1, String param2) {
        FlipEffect fragment = new FlipEffect();
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
        if(fetching ==null)
        {
            fetching = new DataFetching(view,flipEffectRecyclerView, ConstantsDatabaseName.Flip);
            Log.i("Check","FlipEffectWorking");
            fetching.execute(Links.ALL);
        }
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

        newsList = new ArrayList<>();
        view =inflater.inflate(R.layout.fragment_flip_effect, container, false);
        actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();

        flipAdapter = new SwipeAdapter(newsList,view.getContext());
        dataBase = DataBase.getInstance(view.getContext());
        flipEffectRecyclerView = view.findViewById(R.id.flipEffectRecyclerView);
        flipEffectRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        OnRefresh();

        return view;
    }

    public void OnRefresh()
    {
        swipeRefreshLayout = view.findViewById(R.id.flipEffectSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.i("swiping","done");
                fetching = new DataFetching(view,flipEffectRecyclerView, ConstantsDatabaseName.Flip);
                fetching.refreshLoading(swipeRefreshLayout);
                fetching.execute(Links.ALL);



            }


        });
    }



}
