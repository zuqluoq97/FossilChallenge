package com.ltdung.fossilsofchallenge;

import android.app.Activity;
import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.ltdung.fossilsofchallenge.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class FossilSOFChallengeApp extends Application implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> mDispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Inject
    OkHttpClient mOkHttpClient;

    @Inject
    GsonParserFactory mGsonParserFactory;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        if(BuildConfig.DEBUG){
            AndroidNetworking.enableLogging();
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);
        AndroidNetworking.initialize(getApplicationContext(), mOkHttpClient);
        AndroidNetworking.setParserFactory(mGsonParserFactory);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return mDispatchingAndroidInjector;
    }
}