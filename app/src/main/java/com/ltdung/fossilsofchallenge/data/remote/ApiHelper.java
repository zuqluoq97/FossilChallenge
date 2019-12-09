package com.ltdung.fossilsofchallenge.data.remote;

import com.ltdung.fossilsofchallenge.data.model.Tags;
import com.ltdung.fossilsofchallenge.data.model.Users;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHelper {

    @GET("/2.2/users?pagesize=10&site=stackoverflow&filter=&key=WbnaD2anhC6oamVZckZrLQ((")
    Single<Users> getSOFUsers(@Query("page") int page);

    @GET("/2.2/users/{userid}/tags?pagesize=3&site=stackoverflow&key=WbnaD2anhC6oamVZckZrLQ((")
    Single<Tags> getSOFUserTags(@Path("userid") int userId);
}
