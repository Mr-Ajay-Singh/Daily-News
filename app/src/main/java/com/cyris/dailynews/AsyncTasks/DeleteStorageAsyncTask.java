package com.cyris.dailynews.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.room.Room;

import com.cyris.dailynews.Database.DataBase;
import com.cyris.dailynews.Database.TaskEntity;

import java.util.List;

public class DeleteStorageAsyncTask extends AsyncTask<List<TaskEntity>,Void,Void> {

    private View view;
    DataBase database;
    String databaseName;
    Context context;
    public DeleteStorageAsyncTask(View view1, String dataBaseName1)
    {
        this.databaseName = dataBaseName1;
        this.view = view1;
    }

    public DeleteStorageAsyncTask(Context ctx, String dataBaseName1)
    {
        this.databaseName = dataBaseName1;
        this.context = ctx;
    }
    @Override
    protected Void doInBackground(List<TaskEntity>... lists) {
        if(context==null)
            database = Room.databaseBuilder(view.getContext(), DataBase.class,databaseName).build();
        else
            database = Room.databaseBuilder(context, DataBase.class,databaseName).build();

            //database.daoInterface().deleteAll();
        for (int i=0;i<lists[0].size();i++)
        {

            database.daoInterface().delete(lists[i].get(i));
        }
        database.close();
        Log.i("databaseClose",databaseName+" is cloased");


        return null;
    }
}
