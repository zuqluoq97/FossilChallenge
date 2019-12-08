package com.ltdung.fossilsofchallenge.ui.main.list;

import android.widget.LinearLayout;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dung Luong on 05/12/2019
 */
@Module
public class SOFUsersListFragmentModule {

    @Provides
    LinearLayout provideLinearLayout(SOFUsersListFragment sofUsersListFragment){
        return new LinearLayout(sofUsersListFragment.getActivity());
    }
}
