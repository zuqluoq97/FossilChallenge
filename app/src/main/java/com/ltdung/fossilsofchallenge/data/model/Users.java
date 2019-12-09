package com.ltdung.fossilsofchallenge.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dung Luong on 08/12/2019
 */
@AutoValue
public abstract class Users {

    @AutoValue.CopyAnnotations
    @SerializedName("items")
    public abstract List<User> users();

    public static Users create(List<User> users){
        return new AutoValue_Users(users);
    }

    public static TypeAdapter<Users> typeAdapter(Gson gson){
        return new AutoValue_Users.GsonTypeAdapter(gson);
    }
}
