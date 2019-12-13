package com.ltdung.fossilsofchallenge;

import android.app.Activity;
import android.app.Application;

import com.ltdung.fossilsofchallenge.di.component.DaggerAppComponent;
import com.ltdung.fossilsofchallenge.utils.AppLogger;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

//import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.gsonparserfactory.GsonParserFactory;

public class FossilSOFChallengeApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);


        if(BuildConfig.DEBUG){
            AppLogger.init();
        }

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(mCalligraphyConfig))
                .build());
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}
