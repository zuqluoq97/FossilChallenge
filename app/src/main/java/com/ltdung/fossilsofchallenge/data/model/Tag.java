package com.ltdung.fossilsofchallenge.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dung Luong on 08/12/2019
 */
@AutoValue
public abstract class Tag {

    @AutoValue.CopyAnnotations
    @SerializedName("count")
    public abstract int count();

    @AutoValue.CopyAnnotations
    @SerializedName("name")
    public abstract String name();

    public static Tag create(int count, String name){
        return new AutoValue_Tag(count, name);
    }

    public static TypeAdapter<Tag> typeAdapter(Gson gson){
        return new AutoValue_Tag.GsonTypeAdapter(gson);
    }
}
