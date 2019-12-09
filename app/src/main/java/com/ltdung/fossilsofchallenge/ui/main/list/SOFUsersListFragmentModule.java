package com.ltdung.fossilsofchallenge.ui.main.list;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.ui.main.list.paging.UserDiffUtilItemCallback;
import com.ltdung.fossilsofchallenge.ui.main.list.paging.UsersPagedAdaptor;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Dung Luong on 05/12/2019
 */
@Module
public class SOFUsersListFragmentModule {

    @Provides
    LinearLayoutManager provideLinearLayout(SOFUsersListFragment sofUsersListFragment){
        return new LinearLayoutManager(sofUsersListFragment.getActivity(),
                LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    UserDiffUtilItemCallback provideUserDiffUtilItemCallback(){
        return new UserDiffUtilItemCallback();
    }

    @Provides
    UsersPagedAdaptor provideUsersPagedAdaptor(UserDiffUtilItemCallback userDiffUtilItemCallback,
                                               SOFUsersListFragment sofUsersListFragment,
                                               DataManager dataManager,
                                               SchedulerProvider schedulerProvider){
        return new UsersPagedAdaptor(userDiffUtilItemCallback, sofUsersListFragment, dataManager, schedulerProvider);
    }
}
