package com.ltdung.fossilsofchallenge.data.local.db;

import com.ltdung.fossilsofchallenge.data.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper{

    private final AppDatabase appDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    @Override
    public Observable<List<User>> getBookMarkedUsers() {
        return Observable.fromCallable(() -> appDatabase.uesrDao().getAllBookmarkedUsers());
    }

    @Override
    public Observable<Boolean> insertBookMarkedUser(User user) {
        return Observable.fromCallable(() -> {
            appDatabase.uesrDao().insert(user);
            return true;
        });
    }

    @Override
    public Observable<Boolean> removeBookMarkedUser(User user) {
        return Observable.fromCallable(() ->{
            appDatabase.uesrDao().remove(user);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateBookMarkedUser(User user) {
        return Observable.fromCallable(() ->{
            appDatabase.uesrDao().update(user);
            return true;
        });
    }

    @Override
    public Observable<Integer> getBookmarkedStatus(User user) {
        return Observable.fromCallable(() -> appDatabase.uesrDao().isBookmarkedUser(user.id()));
    }
}
