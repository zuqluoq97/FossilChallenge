package com.ltdung.fossilsofchallenge.ui.main.bookmarklist;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Dung Luong on 05/12/2019
 */
@Module
public abstract class SOFUsersListBookMarkFragmentProvider {

    @ContributesAndroidInjector(modules = {SOFUsersListBookMarkFragmentModule.class})
    abstract SOFUsersListBookMarkFragment provideSOFUserListBookMarkFragmentFactory();
}
