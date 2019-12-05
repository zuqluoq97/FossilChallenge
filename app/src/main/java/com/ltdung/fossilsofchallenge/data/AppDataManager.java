package com.ltdung.fossilsofchallenge.data;

import com.ltdung.fossilsofchallenge.data.local.db.DbHelper;
import com.ltdung.fossilsofchallenge.data.local.pref.PreferencesHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDataManager implements DataManager {

    private final PreferencesHelper mPreferencesHelper;

    private final DbHelper mDbHelper;

    @Inject
    public AppDataManager(PreferencesHelper preferencesHelper,
                         DbHelper dbHelper){
        this.mPreferencesHelper = preferencesHelper;
        this.mDbHelper = dbHelper;
    }
}
