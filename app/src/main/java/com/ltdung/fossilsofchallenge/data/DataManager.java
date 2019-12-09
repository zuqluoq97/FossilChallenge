package com.ltdung.fossilsofchallenge.data;

import com.ltdung.fossilsofchallenge.data.local.db.DbHelper;
import com.ltdung.fossilsofchallenge.data.local.pref.PreferencesHelper;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.data.remote.ApiHelper;

import java.util.List;

import io.reactivex.Single;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

    Single<List<User>> getCompleteSOFUsers(int page);
}
