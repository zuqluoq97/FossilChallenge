package com.ltdung.fossilsofchallenge.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Dung Luong on 06/12/2019
 */
@AutoValue
@AutoValue.CopyAnnotations
@Entity(tableName = "user")
public abstract class User {

    @AutoValue.CopyAnnotations
    @SerializedName("user_id")
    @PrimaryKey
    public abstract int id();

    @AutoValue.CopyAnnotations
    @Embedded
    @SerializedName("badge_counts")
    public abstract Badge badge();

    @SerializedName("reputation")
    public abstract int reputation();

    @Nullable
    @AutoValue.CopyAnnotations
    @SerializedName("website_url")
    @ColumnInfo(name = "website_url")
    public abstract String websiteUrl();

    @AutoValue.CopyAnnotations
    @SerializedName("profile_image")
    @ColumnInfo(name = "image_link")
    public abstract String imageLink();

    @Nullable
    @AutoValue.CopyAnnotations
    @SerializedName("about_me")
    @ColumnInfo(name = "about_me")
    public abstract String aboutMe();

    @AutoValue.CopyAnnotations
    @SerializedName("display_name")
    @ColumnInfo(name = "display_name")
    public abstract String displayName();

    @Nullable
    @SerializedName("location")
    public abstract String location();

    @Nullable
    @AutoValue.CopyAnnotations
    @Embedded
    public abstract Tags tags();

    @AutoValue.CopyAnnotations
    @ColumnInfo(name = "is_bookmarked")
    public abstract boolean isBookMarked();

    public static User create(int id,
                              Badge badge,
                              int reputation,
                              String websiteUrl,
                              String imageLink,
                              String aboutMe,
                              String displayName,
                              String location,
                              Tags tags,
                              boolean isBookMarked){
        return new AutoValue_User(id, badge, reputation, websiteUrl,
                imageLink, aboutMe, displayName, location, tags, isBookMarked);
    }

    public abstract User withIsBookMarked(boolean isBookMarked);

    public abstract User withTags(Tags tags);

    public static TypeAdapter<User> typeAdapter(Gson gson){
        return new AutoValue_User.GsonTypeAdapter(gson);
    }

}
