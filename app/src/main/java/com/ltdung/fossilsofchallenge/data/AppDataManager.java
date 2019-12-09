package com.ltdung.fossilsofchallenge.data;

import com.ltdung.fossilsofchallenge.data.local.db.DbHelper;
import com.ltdung.fossilsofchallenge.data.local.pref.PreferencesHelper;
import com.ltdung.fossilsofchallenge.data.model.Tags;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.data.model.Users;
import com.ltdung.fossilsofchallenge.data.remote.ApiHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
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
    public Single<Users> getSOFUsers(int page) {
        return mApiHelper.getSOFUsers(page);
    }

    @Override
    public Single<Tags> getSOFUserTags(int userId) {
        return mApiHelper.getSOFUserTags(userId);
    }

    @Override
    public Single<List<User>> getCompleteSOFUsers(int page) {
        return mApiHelper.getSOFUsers(page)
                .flatMap(users ->
                        Observable.fromIterable(users.users())
                                .concatMap(user -> {
                                    AtomicInteger tagDelay = new AtomicInteger();
                                    return Observable.just(
                                            getSOFUserTags(user.id())
                                                    .map(tags -> {
                                                        tagDelay.set(tags.backoff());
                                                        return user.withTags(tags);
                                                    }).blockingGet())
                                            .delay(tagDelay.get(), TimeUnit.SECONDS);
                                }).toList());
    }

    @Override
    public Observable<List<User>> getBookMarkedUsers() {
        return mDbHelper.getBookMarkedUsers();
    }

    @Override
    public Observable<Boolean> insertBookMarkedUser(User user) {
        return mDbHelper.insertBookMarkedUser(user);
    }

    @Override
    public Observable<Boolean> removeBookMarkedUser(User user) {
        return mDbHelper.removeBookMarkedUser(user);
    }
}
