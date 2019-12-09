package com.ltdung.fossilsofchallenge.di.module;

import android.app.Application;
import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.ltdung.fossilsofchallenge.AutoValueGsonFactory;
import com.ltdung.fossilsofchallenge.BuildConfig;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.data.AppDataManager;
import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.local.db.AppDatabase;
import com.ltdung.fossilsofchallenge.data.local.db.AppDbHelper;
import com.ltdung.fossilsofchallenge.data.local.db.DbHelper;
import com.ltdung.fossilsofchallenge.data.local.pref.AppPreferencesHelper;
import com.ltdung.fossilsofchallenge.data.local.pref.PreferencesHelper;
import com.ltdung.fossilsofchallenge.data.remote.ApiHelper;
import com.ltdung.fossilsofchallenge.di.DatabaseInfo;
import com.ltdung.fossilsofchallenge.di.NetworkInfo;
import com.ltdung.fossilsofchallenge.utils.AppConstants;
import com.ltdung.fossilsofchallenge.utils.NetworkUtils;
import com.ltdung.fossilsofchallenge.utils.rx.AppSchedulerProvider;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application){
        return application;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper){
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper){
        return appDbHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager){
        return appDataManager;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider(){
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig(){
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName(){
        return AppConstants.DATABASE_NAME;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .build();
    }

    // Network

    @Provides
    @NetworkInfo
    String provideBaseUrl(){
        return AppConstants.BASE_URL;
    }

    @Provides
    @Singleton
    CookieManager provideCookieManager(){
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        return cookieManager;
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG) httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    CookieJar provideCookieJar(Context context){
        return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(CookieJar cookieJar,
                                     HttpLoggingInterceptor httpLoggingInterceptor,
                                     Cache cache,
                                     Interceptor offlineInterceptor){


        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(offlineInterceptor)
                .cookieJar(cookieJar)
                .cache(cache)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Interceptor provideOffLineInterceptor(Context context){
        return chain -> {
            Request request = chain.request();
            if(!NetworkUtils.isNetworkAvailable(context)){
                int maxStale = 60 * 60 * 24 * 30;
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return chain.proceed(request);
        };
    }


    @Provides
    @Singleton
    Gson provideGson(TypeAdapterFactory typeAdapterFactory){
        return new GsonBuilder().registerTypeAdapterFactory(typeAdapterFactory).create();
    }

    @Provides
    @Singleton
    Cache provideCache(Context context){
        final int cacheSize = 5 * 1024 * 1024;
        File cacheDir = context.getCacheDir();
        return new Cache(cacheDir, cacheSize);
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory(SchedulerProvider schedulerProvider){
        return RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io());
    }

    @Provides
    @Singleton
    TypeAdapterFactory provideTypeAdapterFactory(){
        return AutoValueGsonFactory.create();
    }

    @Provides
    @Singleton
    ApiHelper provideApiHeader(OkHttpClient okHttpClient,
                               @NetworkInfo String baseUrl,
                               RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                               Gson gson){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build().create(ApiHelper.class);
    }

}

