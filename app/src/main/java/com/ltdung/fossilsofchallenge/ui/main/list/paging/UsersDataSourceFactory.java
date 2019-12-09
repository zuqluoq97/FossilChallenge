package com.ltdung.fossilsofchallenge.ui.main.list.paging;

import com.ltdung.fossilsofchallenge.data.model.User;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * Created by Dung Luong on 08/12/2019
 */
public class UsersDataSourceFactory extends DataSource.Factory<Integer, User> {

    private final UsersDataSource usersDataSource;

    private MutableLiveData<UsersDataSource> usersDataSourceMutableLiveData;

    public UsersDataSourceFactory(UsersDataSource usersDataSource){
        this.usersDataSource = usersDataSource;
        usersDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, User> create() {
        usersDataSourceMutableLiveData.postValue(usersDataSource);
        return usersDataSource;
    }

    public UsersDataSource getUsersDataSource(){
        return usersDataSource;
    }
}
