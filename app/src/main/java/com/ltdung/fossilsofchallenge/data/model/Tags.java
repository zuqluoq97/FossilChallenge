package com.ltdung.fossilsofchallenge.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.ltdung.fossilsofchallenge.data.local.db.TagsConverter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.room.TypeConverters;

/**
 * Created by Dung Luong on 08/12/2019
 */
@AutoValue
public abstract class Tags implements Parcelable {

    @Nullable
    @AutoValue.CopyAnnotations
    @TypeConverters(TagsConverter.class)
    @SerializedName("items")
    public abstract List<Tag> tags();

    @SerializedName("backoff")
    public abstract int backoff();

    public static Tags create(List<Tag> tags, int backoff){
        return new AutoValue_Tags(tags, backoff);
    }

    public static TypeAdapter<Tags> typeAdapter(Gson gson){
        return new AutoValue_Tags.GsonTypeAdapter(gson);
    }
}
