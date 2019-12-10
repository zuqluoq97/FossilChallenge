package com.ltdung.fossilsofchallenge.di.builder;

import com.ltdung.fossilsofchallenge.ui.detail.UserDetailsActivity;
import com.ltdung.fossilsofchallenge.ui.detail.UserDetailsModule;
import com.ltdung.fossilsofchallenge.ui.main.MainActivity;
import com.ltdung.fossilsofchallenge.ui.main.MainActivityModule;
import com.ltdung.fossilsofchallenge.ui.main.bookmarklist.SOFUsersListBookMarkFragmentProvider;
import com.ltdung.fossilsofchallenge.ui.main.list.SOFUsersListFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            SOFUsersListBookMarkFragmentProvider.class,
            SOFUsersListFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {
            UserDetailsModule.class})
    abstract UserDetailsActivity bindUserDetailsActivity();

}
