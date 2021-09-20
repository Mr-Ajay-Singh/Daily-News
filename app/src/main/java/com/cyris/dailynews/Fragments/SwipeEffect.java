package com.cyris.dailynews.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyris.dailynews.AsyncTasks.DataFetching;
import com.cyris.dailynews.Database.ConstantsDatabaseName;
import com.cyris.dailynews.Database.DataBase;
import com.cyris.dailynews.Database.TaskEntity;
import com.cyris.dailynews.Links;
import com.cyris.dailynews.R;
import com.cyris.dailynews.Adapters.SwipeAdapter;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SwipeEffect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeEffect extends Fragment {
    String type="swipe";
    HttpURLConnection urlConnection;
    ProgressDialog progressDialog;
    List<TaskEntity> newsList;
    androidx.recyclerview.widget.RecyclerView swipeEffectRecyclerView;
    SwipeAdapter swipeAdapter;
    View view;
    SnapHelper snapHelper;
    DataBase dataBase;
    Dialog sorryDialog;
    DataFetching fetching;
    boolean isdone = false;
    ActionBar actionBar;



    public SwipeEffect() {
        // Required empty public constructor
    }


    public static SwipeEffect newInstance(String param1, String param2) {
        SwipeEffect fragment = new SwipeEffect();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("iamdone","done");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        newsList = new ArrayList<>();
        view =inflater.inflate(R.layout.fragment_swipe_effect, container, false);
        actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();

        dataBase = DataBase.getInstance(view.getContext());
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Loading Data");
        progressDialog.setMessage("News is Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        swipeEffectRecyclerView = view.findViewById(R.id.swipeEffectRecyclerView);

        swipeEffectRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fetching = new DataFetching(view,swipeEffectRecyclerView, ConstantsDatabaseName.Swipe);


        fetching.execute(Links.ALL);


        return view;
    }




}
