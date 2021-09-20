package com.cyris.dailynews.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyris.dailynews.AsyncTasks.DataFetching;
import com.cyris.dailynews.Database.ConstantsDatabaseName;
import com.cyris.dailynews.Database.TaskEntity;
import com.cyris.dailynews.Links;
import com.cyris.dailynews.R;
import com.cyris.dailynews.Adapters.SwipeAdapter;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SportsFrgment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportsFrgment extends Fragment {

    HttpURLConnection urlConnection;
    String type="flip";
    List<TaskEntity> newsList;
    View view;
    RecyclerView sportsRecyclerView;
    SwipeAdapter sportsAdapter;
    private DataFetching fetching;
    boolean isdone = false;
    ActionBar actionBar;
    SwipeRefreshLayout swipeRefreshLayout;


    public SportsFrgment() {
        // Required empty public constructor
    }


    public static SportsFrgment newInstance(String param1, String param2) {
        SportsFrgment fragment = new SportsFrgment();
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

        if(!isdone)
        fetching.execute("http://192.168.43.111/call/Operations/responsePostSports.php");
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
        actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();

        view = inflater.inflate(R.layout.fragment_sports_frgment, container, false);
        sportsRecyclerView = view.findViewById(R.id.sportsRecyclerView);
        sportsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fetching = new DataFetching(view,sportsRecyclerView, ConstantsDatabaseName.Sports);
        OnRefresh();



        return view;
       }
    public void OnRefresh()
    {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshSports);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.i("swiping","done");
                fetching = new DataFetching(view,sportsRecyclerView, ConstantsDatabaseName.Sports);
                fetching.refreshLoading(swipeRefreshLayout);
                fetching.execute(Links.SPORTS);



            }


        });
    }
}
