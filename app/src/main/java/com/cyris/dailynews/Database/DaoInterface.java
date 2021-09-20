package com.cyris.dailynews.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoInterface {

    @Query("SELECT * FROM TaskEntity ORDER BY publishedAt DESC")
    List<TaskEntity> getAll();

    //@Query("WITH  q AS (SELECT 30 FROM TaskEntity ORDER BY publishedAt ASC) DELETE FROM q")
  //  @Query("DELETE FROM TaskEntity LIMIT 30")
    //void deleteMany();

    @Query("DELETE FROM TaskEntity WHERE publishedAt NOT IN (SELECT publishedAt FROM TaskEntity ORDER BY publishedAt DESC LIMIT 90);")
    void deleteMany();

    @Query("SELECT COUNT(*) FROM TaskEntity")
    int size();

/*  @Update(onConflict = OnConflictStrategy.REPLACE)
   void update(TaskEntity taskEntity);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaskEntity> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskEntity user);

    @Delete
    void delete(TaskEntity user);

    @Query("DELETE FROM TaskEntity")
    void deleteAll();


}
