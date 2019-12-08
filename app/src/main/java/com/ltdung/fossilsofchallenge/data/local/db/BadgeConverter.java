package com.ltdung.fossilsofchallenge.data.local.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ltdung.fossilsofchallenge.data.model.Badge;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;

/**
 * Created by Dung Luong on 06/12/2019
 */
public class BadgeConverter {

    @TypeConverter
    public static String fromBagde(Badge badge){
        Gson gson = new Gson();
        Type type = new TypeToken<Badge>(){}.getType();
        return gson.toJson(badge, type);
    }

//    @TypeConverter
//    public Badge toBadge(String badge){
//        Gson gson = new Gson();
//        Type type = new TypeToken<Badge>(){}.getType();
//    }


}
