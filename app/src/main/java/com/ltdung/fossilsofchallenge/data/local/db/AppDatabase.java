package com.ltdung.fossilsofchallenge.data.local.db;

import com.ltdung.fossilsofchallenge.data.local.db.dao.UserDao;
import com.ltdung.fossilsofchallenge.data.model.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//@TypeConverters({TagsConverter.class})
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao uesrDao();
}
