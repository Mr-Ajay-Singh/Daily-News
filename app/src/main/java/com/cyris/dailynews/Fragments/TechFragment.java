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
 * Use the {@link TechFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TechFragment extends Fragment {

    HttpURLConnection urlConnection;
    String type="flip";
    List<TaskEntity> newsList;
    View view;
    RecyclerView techRecyclerView;
    SwipeAdapter techAdapter;
    private DataFetching fetching;
    boolean isdone = false;
    ActionBar actionBar;
    SwipeRefreshLayout swipeRefreshLayout;
   
    public TechFragment() {
        // Required empty public constructor
    }

   public static TechFragment newInstance(String param1, String param2) {
        TechFragment fragment = new TechFragment();
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
        fetching.execute();
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
        view = inflater.inflate(R.layout.fragment_tech, container, false);
        techRecyclerView = view.findViewById(R.id.techRecyclerView);
        actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();

        techRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fetching = new DataFetching(view,techRecyclerView, ConstantsDatabaseName.Tech);
        OnRefresh();
        return view;
    }

    public void OnRefresh()
    {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshTech);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.i("swiping","done");
                fetching = new DataFetching(view,techRecyclerView, ConstantsDatabaseName.Tech);
                fetching.refreshLoading(swipeRefreshLayout);
                fetching.execute(Links.TECH);



            }


        });
    }
}
