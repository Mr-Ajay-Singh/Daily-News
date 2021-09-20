package com.cyris.dailynews.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cyris.dailynews.AsyncTasks.FetchingAsyncTask;
import com.cyris.dailynews.Database.ConstantsDatabaseName;
import com.cyris.dailynews.R;


public class BookmarekShowPage extends Fragment {

    RecyclerView recyclerView;
    FetchingAsyncTask fetchingAsyncTask;
    Boolean istrue =false;
    LinearLayout noBookMark;


    public BookmarekShowPage() {
        // Required empty public constructor
    }

    public static BookmarekShowPage newInstance(String param1, String param2) {
        BookmarekShowPage fragment = new BookmarekShowPage();
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
        if(!istrue)
            fetchingAsyncTask.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarek_show_page, container, false);
        noBookMark = view.findViewById(R.id.loadingNoBookmark);
        recyclerView = view.findViewById(R.id.bookmarkRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        fetchingAsyncTask = new FetchingAsyncTask(recyclerView,view, ConstantsDatabaseName.BookMark,"flip");
        fetchingAsyncTask.getNoBookmark(noBookMark);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        istrue = true;
    }
}
