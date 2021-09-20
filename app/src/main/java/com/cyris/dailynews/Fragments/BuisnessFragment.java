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
 * Use the {@link BuisnessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuisnessFragment extends Fragment {

    HttpURLConnection urlConnection;
    String type="flip";
    SwipeRefreshLayout swipeRefreshLayout;
    List<TaskEntity> newsList;
    View view;
    RecyclerView buisnessRecyclerView;
    Dialog dialog;
    SwipeAdapter buisnessAdapter;
    private DataFetching fetching;
    ActionBar actionBar;
    boolean isdone = false;


    public BuisnessFragment() {
        // Required empty public constructor
    }

      public static BuisnessFragment newInstance(String param1, String param2) {
        BuisnessFragment fragment = new BuisnessFragment();
        Bundle args = new Bundle();

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
        fetching.execute(Links.BUISNESS);
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
        view = inflater.inflate(R.layout.fragment_buisness, container, false);
        buisnessRecyclerView = view.findViewById(R.id.buisnessRecyclerView);
        buisnessRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fetching = new DataFetching(view,buisnessRecyclerView, ConstantsDatabaseName.Buisness);
        OnRefresh();




        return view;
    }

    public void OnRefresh()
    {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshBuisness);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    Log.i("swiping","done");
                    fetching = new DataFetching(view,buisnessRecyclerView, ConstantsDatabaseName.Buisness);
                    fetching.refreshLoading(swipeRefreshLayout);
                    fetching.execute("http://192.168.43.111/call/Operations/responsePostBuisness.php");



            }


        });
    }

}
