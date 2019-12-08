package com.ltdung.fossilsofchallenge.data.remote;

import com.ltdung.fossilsofchallenge.data.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHelper {

    @GET("/users")
    Single<List<User>> getSOFUsers(@Query("page") int page,
                                   @Query("pagesize") int pageSize,
                                   @Query("site") String site,
                                   @Query("filter") String filter);
}
