package com.cyris.dailynews.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyris.dailynews.AsyncTasks.SuggestedFetching;
import com.cyris.dailynews.R;
import com.cyris.dailynews.ViewPager.SuggestedAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuggestedTopic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestedTopic extends Fragment {

    View view;
    RecyclerView recyclerView;
    SuggestedFetching fetching;

    public SuggestedTopic() {
        // Required empty public constructor
    }


    public static SuggestedTopic newInstance(String param1, String param2) {
        SuggestedTopic fragment = new SuggestedTopic();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_suggested_topic, container, false);
        recyclerView = view.findViewById(R.id.suggestedTopicRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),3));
        fetching = new SuggestedFetching(view,recyclerView);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/zoika.ttf");
        TextView suggestedTopText = view.findViewById(R.id.suggestedTopText);
        suggestedTopText.setTypeface(typeface);




        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetching = new SuggestedFetching(view,recyclerView);
        fetching.execute("http://192.168.43.111/call/Operations/responseSuggested.php");
    }
}
