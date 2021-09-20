package com.cyris.dailynews.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "title",unique = true)})
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "urlToImage")
    public String urlToImage;

    @ColumnInfo(name = "publishedAt")
    public String publishedAt;

    @ColumnInfo(name = "Source")
    public String source;
}
