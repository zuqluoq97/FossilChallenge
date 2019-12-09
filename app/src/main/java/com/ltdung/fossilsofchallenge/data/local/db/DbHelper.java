package com.ltdung.fossilsofchallenge.data.local.db;

import com.ltdung.fossilsofchallenge.data.model.User;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {

    Observable<List<User>> getBookMarkedUsers();

    Observable<Boolean> insertBookMarkedUser(User user);

    Observable<Boolean> removeBookMarkedUser(User user);
}
