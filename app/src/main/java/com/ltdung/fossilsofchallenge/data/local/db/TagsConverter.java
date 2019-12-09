package com.ltdung.fossilsofchallenge.data.local.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ltdung.fossilsofchallenge.data.model.Badge;
import com.ltdung.fossilsofchallenge.data.model.Tags;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;

/**
 * Created by Dung Luong on 06/12/2019
 */
public class TagsConverter {

    @TypeConverter
    public static String fromTags(Tags tags){
        Gson gson = new Gson();
        Type type = new TypeToken<Tags>(){}.getType();
        return gson.toJson(tags, type);
    }

    @TypeConverter
    public Tags toTags(String tagsString){
        Gson gson = new Gson();
        Type type = new TypeToken<Tags>(){}.getType();
        return gson.fromJson(tagsString, type);
    }


}
