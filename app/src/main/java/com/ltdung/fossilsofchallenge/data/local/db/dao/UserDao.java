package com.ltdung.fossilsofchallenge.data.local.db.dao;

import com.ltdung.fossilsofchallenge.data.model.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Dung Luong on 09/12/2019
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllBookmarkedUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void remove(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Query("SELECT COUNT(*) FROM user WHERE id LIKE :id")
    int isBookmarkedUser(int id);
}
