package com.ltdung.fossilsofchallenge.di.component;

import android.app.Application;

import com.ltdung.fossilsofchallenge.FossilSOFChallengeApp;
import com.ltdung.fossilsofchallenge.di.builder.ActivityBuilder;
import com.ltdung.fossilsofchallenge.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {
    void inject(FossilSOFChallengeApp app);

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}
