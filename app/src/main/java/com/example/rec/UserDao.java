package com.example.rec;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

@Dao
public interface UserDao {
    @Insert
    void insert(User u);

    @Delete
    void delete(User u);
}
