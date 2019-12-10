package com.ltdung.fossilsofchallenge.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dung Luong on 06/12/2019
 */
@AutoValue
public abstract class Badge implements Parcelable {

    @SerializedName("bronze")
    public abstract int bronze();

    @SerializedName("silver")
    public abstract int silver();

    @SerializedName("gold")
    public abstract int gold();

    public static Badge create(int bronze, int silver, int gold){
        return new AutoValue_Badge(bronze, silver, gold);
    }

    public static TypeAdapter<Badge> typeAdapter(Gson gson){
        return new AutoValue_Badge.GsonTypeAdapter(gson);
    }
}


