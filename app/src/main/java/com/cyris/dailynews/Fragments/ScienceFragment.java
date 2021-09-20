package com.cyris.dailynews.Fragments;

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

import com.cyris.dailynews.AsyncTasks.DataFetching;
import com.cyris.dailynews.Database.ConstantsDatabaseName;
import com.cyris.dailynews.Database.TaskEntity;
import com.cyris.dailynews.Links;
import com.cyris.dailynews.R;
import com.cyris.dailynews.Adapters.SwipeAdapter;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScienceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScienceFragment extends Fragment {

    HttpURLConnection urlConnection;
    String type="flip";
    SwipeRefreshLayout swipeRefreshLayout;
    ActionBar actionBar;
    List<TaskEntity> newsList;
    View view;
    RecyclerView scienceRecyclerView;
    SwipeAdapter scienceAdapter;
    private DataFetching fetching;
    boolean isdone = false;


    public ScienceFragment() {
        // Required empty public constructor
    }


    public static ScienceFragment newInstance(String param1, String param2) {
        ScienceFragment fragment = new ScienceFragment();
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
        fetching.execute("http://192.168.43.111/call/Operations/responsePostScience.php");
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

        view = inflater.inflate(R.layout.fragment_science, container, false);
        scienceRecyclerView = view.findViewById(R.id.scienceRecyclerView);
        scienceRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fetching = new DataFetching(view,scienceRecyclerView, ConstantsDatabaseName.Science);
        OnRefresh();
        return view;
         }

    public void OnRefresh()
    {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshScience);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.i("swiping","done");
                fetching = new DataFetching(view,scienceRecyclerView, ConstantsDatabaseName.Science);
                fetching.refreshLoading(swipeRefreshLayout);
                fetching.execute(Links.SCIENCE);



            }


        });
    }
}
