package com.ltdung.fossilsofchallenge.ui.detail;

import com.ltdung.fossilsofchallenge.BR;
import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.ui.base.BaseViewModel;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

/**
 * Created by Dung Luong on 10/12/2019
 */
public class UserDetailsViewModel extends BaseViewModel<UserDetailsNavigator> {

    private ObservableField<User> userObservableField;

    public UserDetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        userObservableField = new ObservableField<>();
    }

    @Bindable
    public ObservableField<User> getUserObservableField() {
        return userObservableField;
    }

    public void setUserObservableField(User user) {
        getUserObservableField().set(user);
        notifyPropertyChanged(BR.userObservableField);
    }
}
