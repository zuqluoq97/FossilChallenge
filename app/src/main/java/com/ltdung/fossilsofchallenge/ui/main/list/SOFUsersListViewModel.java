package com.ltdung.fossilsofchallenge.ui.main.list;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.ui.base.BaseViewModel;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

/**
 * Created by Dung Luong on 05/12/2019
 */
public class SOFUsersListViewModel extends BaseViewModel<SOFUsersListCallback> {

    public SOFUsersListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
