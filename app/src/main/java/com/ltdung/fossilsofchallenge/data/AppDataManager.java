package com.ltdung.fossilsofchallenge.data;

import com.ltdung.fossilsofchallenge.data.local.db.DbHelper;
import com.ltdung.fossilsofchallenge.data.local.pref.PreferencesHelper;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.data.remote.ApiHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private final PreferencesHelper mPreferencesHelper;

    private final DbHelper mDbHelper;

    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(PreferencesHelper preferencesHelper,
                          DbHelper dbHelper,
                          ApiHelper apiHelper){
        this.mPreferencesHelper = preferencesHelper;
        this.mDbHelper = dbHelper;
        this.mApiHelper = apiHelper;
    }

    @Override
    public Single<List<User>> getSOFUsers(int page, int pageSize, String site, String filter) {
        return mApiHelper.getSOFUsers(page, pageSize, site, filter);
    }
}
