package com.cyris.dailynews.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.cyris.dailynews.Adapters.SuggestedTopicAdapter;
import com.cyris.dailynews.Database.TaskLoadSuggested;
import com.cyris.dailynews.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SuggestedFetching extends AsyncTask<String,Void,String> {

    View view;
    RecyclerView recyclerView;
    private HttpURLConnection urlConnection;
    private String data;
    private List<TaskLoadSuggested> newsList;
    private SuggestedTopicAdapter suggestedAdapter;
    ImageView loadingImage;

    public SuggestedFetching(View view1, RecyclerView recyclerView1)
    {
        this.view = view1;
        this.recyclerView = recyclerView1;
        loadingImage = ((Activity)view.getContext()).findViewById(R.id.loadingImage);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A");
            if (scan.hasNext())
                return scan.next();
            else
                return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }



        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        data = s;
        if(s!=null)
        {
            try {
                JsonDataMaipulation();
                loadingImage.setElevation(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            TabLayout tabLayout = ((Activity)view.getContext()).findViewById(R.id.tabLayoutInMain);
            tabLayout.setVisibility(View.INVISIBLE);

            loadingImage.setElevation(0);



        }




    }

    public void JsonDataMaipulation()throws JSONException
    {
        JSONArray jsonArray= new JSONArray(data);
        newsList = new ArrayList<>();

        //    Execute1 execute1 = new Execute1();
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);

            TaskLoadSuggested entity = new TaskLoadSuggested();
            entity.link = object.getString("url");
            entity.image = object.getString("urlToImage");
            entity.title = object.getString("title");
            Log.i("helloadfgh",entity.title);
            newsList.add(entity);
        }


        suggestedAdapter = new SuggestedTopicAdapter(view.getContext(),newsList);
        recyclerView.setAdapter(suggestedAdapter);
        suggestedAdapter.notifyDataSetChanged();
    }
}
