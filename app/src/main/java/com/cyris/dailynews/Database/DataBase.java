package com.cyris.dailynews.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TaskEntity.class},version = 1,exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    private static final String DatabaseName = "NewsDatabase";
    private static  DataBase sInstance;

    public static  DataBase getInstance(Context context)
    {

        if(sInstance==null)
        {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),DataBase.class,DataBase.DatabaseName).build();
        }
        return sInstance;
    }
    public abstract DaoInterface daoInterface();




}
