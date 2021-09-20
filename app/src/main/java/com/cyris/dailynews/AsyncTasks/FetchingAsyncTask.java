package com.cyris.dailynews.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.appcompat.app.ActionBar;

import com.cyris.dailynews.Adapters.FlipAdapter;
import com.cyris.dailynews.Database.ConstantsDatabaseName;
import com.cyris.dailynews.Database.DataBase;
import com.cyris.dailynews.Database.TaskEntity;
import com.cyris.dailynews.Adapters.SwipeAdapter;
import com.cyris.dailynews.R;

import java.util.ArrayList;
import java.util.List;

public class FetchingAsyncTask extends AsyncTask<Void,Void,Void> {
    SwipeAdapter swipeAdapter;
    ActionBar actionBar;
    FlipAdapter flipAdapter;
    RecyclerView recyclerView;
    View view;
    String type;
    Context context;
    List<TaskEntity> newsList;
    DataBase dataBase;
    String databaseName;
    int count;
    String extraData;
    LinearLayout noBookmark;

    public FetchingAsyncTask(SwipeAdapter adapter1,RecyclerView recyclerView1,View view1,String databaseName1,String type1,ActionBar actionBar1)
    {
        this.actionBar = actionBar1;
        this.type = type1;
        this.databaseName = databaseName1;
        this.recyclerView = recyclerView1;
        this.swipeAdapter = adapter1;
        this.view = view1;
        context = view.getContext();

    }
    public FetchingAsyncTask(RecyclerView recyclerView1,View view1,String databaseName1,String type1)
    {

        this.type = type1;
        this.databaseName = databaseName1;
        this.recyclerView = recyclerView1;
        this.view = view1;
        context = view.getContext();
    }




    @Override
    protected Void doInBackground(Void... voids) {

       if(newsList==null)
            newsList = new ArrayList<>();


        dataBase = Room.databaseBuilder(context, DataBase.class,databaseName).build();
        Log.i("databaseRun",databaseName+" is running");
        if(dataBase.daoInterface().size()>120)
        {
            dataBase.daoInterface().deleteMany();
        }


        for(int i=0;i<dataBase.daoInterface().size();i++)
        {

            newsList.add(dataBase.daoInterface().getAll().get(i));

        }

        count = dataBase.daoInterface().size();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i("count2216",String.valueOf(count));
        if(type.equals("flip"))
        {
            flipAdapter = new FlipAdapter(newsList,view.getContext(),view);
            recyclerView.setAdapter(flipAdapter);
            flipAdapter.notifyDataSetChanged();

        }
        else
        {
            swipeAdapter = new SwipeAdapter(newsList,view.getContext());
            recyclerView.setAdapter(swipeAdapter);
            swipeAdapter.notifyDataSetChanged();
        }
        if(databaseName == ConstantsDatabaseName.Extra)
            fetchingExtra();
        if(databaseName == ConstantsDatabaseName.BookMark)
        {
            if(count==0)
                HideNoBookMark();
        }


        Log.i("databaseClose",databaseName+" is cloased");
        dataBase.close();
    }

    public void refreshData()
    {
        flipAdapter.notifyDataSetChanged();
    }

    public void ExtraData(String extraData1)
    {
        extraData = extraData1;
    }

    public void fetchingExtra()
    {
        int i=0;
        for(i=0;i<newsList.size();i++)
        {
            Log.i("log221","working");
            if(extraData.equals(newsList.get(i).title))
            {
                Log.i("log221","working221");
                break;
            }

        }
        if(newsList.size() != i)
            recyclerView.getLayoutManager().scrollToPosition(i);
    }


    public void getNoBookmark(LinearLayout imageView)
    {
        noBookmark = imageView;
    }
    public void HideNoBookMark()
    {
        noBookmark.setVisibility(View.VISIBLE);
        ((Activity)view.getContext()).findViewById(R.id.loadingImage).setVisibility(View.INVISIBLE);

    }

}
