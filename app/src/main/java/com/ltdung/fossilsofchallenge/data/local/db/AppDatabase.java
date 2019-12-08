package com.ltdung.fossilsofchallenge.data.local.db;

import com.ltdung.fossilsofchallenge.data.model.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//@TypeConverters({BadgeConverter.class})
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

}
