package com.ltdung.fossilsofchallenge.ui.main.bookmarklist;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.ui.base.BaseViewModel;
import com.ltdung.fossilsofchallenge.utils.AppLogger;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by Dung Luong on 05/12/2019
 */
public class SOFUsersListBookMarkViewModel extends BaseViewModel<SOFUsersListBookMarkCallback> {

    private static final String TAG = "SOFUsersListBookMarkViewModel";

    private MutableLiveData<List<User>> listBookmarkedUsersLiveData;

    public SOFUsersListBookMarkViewModel(DataManager dataManager,
                                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        listBookmarkedUsersLiveData = new MutableLiveData<>();
    }

    public void getBookmarkedUsers(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getBookMarkedUsers()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(bookmarkedUsers ->{
                    AppLogger.d(TAG + " - " + bookmarkedUsers.toString());
                    listBookmarkedUsersLiveData.postValue(bookmarkedUsers);
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    AppLogger.e(throwable, TAG);
                }));
    }

    public MutableLiveData<List<User>> getListBookmarkedUsersLiveData() {
        return listBookmarkedUsersLiveData;
    }
}
