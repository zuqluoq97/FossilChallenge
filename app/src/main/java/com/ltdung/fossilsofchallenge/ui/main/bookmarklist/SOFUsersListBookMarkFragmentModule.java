package com.ltdung.fossilsofchallenge.ui.main.bookmarklist;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Dung Luong on 05/12/2019
 */
@Module
public class SOFUsersListBookMarkFragmentModule {

    @Provides
    LinearLayoutManager provideLinearLayout(SOFUsersListBookMarkFragment sofUsersListBookMarkFragment){
        return new LinearLayoutManager(sofUsersListBookMarkFragment.getActivity(),
                LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    BookMarkUsersAdapter provideBookMarkUsersAdapter(DataManager dataManager, SchedulerProvider schedulerProvider){
        return new BookMarkUsersAdapter(dataManager, schedulerProvider);
    }

}
