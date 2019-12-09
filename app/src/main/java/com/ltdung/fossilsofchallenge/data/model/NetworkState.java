package com.ltdung.fossilsofchallenge.data.model;

import com.google.auto.value.AutoValue;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

/**
 * Created by Dung Luong on 08/12/2019
 */
@AutoValue
public abstract class NetworkState {

    public abstract int status();

    @Nullable
    public abstract String message();

    public static Builder builder(){
        return new AutoValue_NetworkState.Builder().status(Status.LOADING);
    }

    @AutoValue.Builder
    public static abstract class Builder{

        public abstract Builder status(int status);

        public abstract Builder message(String message);

        public abstract NetworkState build();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Status.LOADING, Status.SUCCESS, Status.ERROR})
    public @interface Status{
        int LOADING = 0;
        int SUCCESS = 1;
        int ERROR = 2;
    }
}
