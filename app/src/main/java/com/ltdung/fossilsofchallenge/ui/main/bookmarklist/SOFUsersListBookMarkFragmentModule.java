package com.ltdung.fossilsofchallenge.ui.main.bookmarklist;

import android.widget.LinearLayout;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dung Luong on 05/12/2019
 */
@Module
public class SOFUsersListBookMarkFragmentModule {

    @Provides
    LinearLayout provideLinearLayout(SOFUsersListBookMarkFragment sofUsersListBookMarkFragment){
        return new LinearLayout(sofUsersListBookMarkFragment.getActivity());
    }

}
