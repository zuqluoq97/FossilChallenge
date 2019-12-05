package com.ltdung.fossilsofchallenge.ui.main.list;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Dung Luong on 05/12/2019
 */
@Module
public abstract class SOFUsersListFragmentProvider {

    @ContributesAndroidInjector(modules = {SOFUsersListFragmentModule.class})
    abstract SOFUsersListFragment provideSOFUserListFragmentFactory();
}
