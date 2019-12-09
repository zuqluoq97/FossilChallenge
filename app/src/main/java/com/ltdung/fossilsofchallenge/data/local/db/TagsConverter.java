package com.ltdung.fossilsofchallenge.data.local.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ltdung.fossilsofchallenge.AutoValueGsonFactory;
import com.ltdung.fossilsofchallenge.data.model.Tag;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

/**
 * Created by Dung Luong on 06/12/2019
 */
public class TagsConverter {

    @TypeConverter
    public String fromTags(List<Tag> tags){
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(AutoValueGsonFactory.create()).create();
        Type type = new TypeToken<List<Tag>>(){}.getType();
        return gson.toJson(tags, type);
    }

    @TypeConverter
    public List<Tag> toTags(String tagsJson){
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(AutoValueGsonFactory.create()).create();
        Type type = new TypeToken<List<Tag>>(){}.getType();
        return gson.fromJson(tagsJson, type);
    }
}
