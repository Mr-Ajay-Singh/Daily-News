package com.cyris.dailynews.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskLoadSuggested {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;



    @ColumnInfo(name = "link")
    public String link;

    @ColumnInfo(name = "image")
    public String image;



}
