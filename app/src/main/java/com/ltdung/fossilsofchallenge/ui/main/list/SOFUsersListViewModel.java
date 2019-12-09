package com.ltdung.fossilsofchallenge.ui.main.list;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.model.NetworkState;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.ui.base.BaseViewModel;
import com.ltdung.fossilsofchallenge.ui.main.list.paging.UsersDataSource;
import com.ltdung.fossilsofchallenge.ui.main.list.paging.UsersDataSourceFactory;
import com.ltdung.fossilsofchallenge.utils.AppConstants;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by Dung Luong on 05/12/2019
 */
public class SOFUsersListViewModel extends BaseViewModel<SOFUsersListCallback> {

    private final UsersDataSourceFactory usersDataSourceFactory;

    private LiveData<PagedList<User>> users;

    public SOFUsersListViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        UsersDataSource usersDataSource = new UsersDataSource(dataManager,
                schedulerProvider, getCompositeDisposable());
        usersDataSourceFactory = new UsersDataSourceFactory(usersDataSource);
    }

    public void onScreenCreated(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .build();
        users = new LivePagedListBuilder<>(usersDataSourceFactory, config).build();
    }

    public LiveData<PagedList<User>> getUsers(){
        return users;
    }

    public LiveData<NetworkState> initialLoadState(){
        return usersDataSourceFactory.getUsersDataSource().getInitialLoadStateLiveData();
    }

    public LiveData<NetworkState> paginatedLoadState(){
        return usersDataSourceFactory.getUsersDataSource().getPaginatedNetworkStateLiveData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        usersDataSourceFactory.getUsersDataSource().clear();
    }

    public void retry(){
        usersDataSourceFactory.getUsersDataSource().retryPagination();
    }
}
