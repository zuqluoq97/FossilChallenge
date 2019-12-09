package com.ltdung.fossilsofchallenge.ui.main.list.paging;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.model.NetworkState;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.utils.AppLogger;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Dung Luong on 08/12/2019
 */
@Singleton
public class UsersDataSource extends ItemKeyedDataSource<Integer, User> {

    private static final String TAG = "UsersDataSource";

    private final DataManager dataManager;

    private SchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;

    private int pageNumber = 1;

    private MutableLiveData<NetworkState> paginatedNetworkStateLiveData;

    private MutableLiveData<NetworkState> initialLoadStateLiveData;

    // Retry
    private LoadParams<Integer> params;

    private LoadCallback<User> callback;

    public UsersDataSource(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable){
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
        initialLoadStateLiveData = new MutableLiveData<>();
        paginatedNetworkStateLiveData = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<User> callback) {
        AppLogger.d(TAG + " - " + "Fetching first page");
        initialLoadStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.LOADING).build());
        Disposable usersDisposable = dataManager.getCompleteSOFUsers(pageNumber)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(users -> onUsersFetched(users, callback), this::onError);
        compositeDisposable.add(usersDisposable);
    }

    private void onError(Throwable throwable) {
        initialLoadStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.ERROR)
                .message(throwable.getMessage()).build());
        AppLogger.e(throwable, TAG + " - " + throwable.getLocalizedMessage());
    }

    private void onUsersFetched(List<User> users, LoadInitialCallback<User> callback){
        initialLoadStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.SUCCESS).build());
        pageNumber++;
        callback.onResult(users);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<User> callback) {
        this.params = params;
        this.callback = callback;
        AppLogger.d(TAG + " - " + "Fetching next page: %s", pageNumber);
        paginatedNetworkStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.LOADING).build());
        Disposable showsDisposable = dataManager.getCompleteSOFUsers(params.key)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(users -> {
                    AppLogger.d(TAG + " - Loaded users: %s", users.toString());
                    onMoreShowsFetched(users, callback);
                }, this::onPaginationError);
        compositeDisposable.add(showsDisposable);
    }

    private void onMoreShowsFetched(List<User> users, LoadCallback<User> callback) {
        paginatedNetworkStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.SUCCESS).build());
        pageNumber++;
        callback.onResult(users);
    }

    private void onPaginationError(Throwable throwable) {
        paginatedNetworkStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.ERROR)
                .message(throwable.getMessage()).build());
        AppLogger.e(throwable, TAG + " - " + throwable.getLocalizedMessage());
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
        // Do nothing because data is loaded from our initial load itself
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull User item) {
        return pageNumber;
    }

    public void clear(){
        pageNumber = 1;
    }

    public LiveData<NetworkState> getPaginatedNetworkStateLiveData() {
        return paginatedNetworkStateLiveData;
    }

    public LiveData<NetworkState> getInitialLoadStateLiveData() {
        return initialLoadStateLiveData;
    }

    public void retryPagination() {
        loadAfter(params, callback);
    }

}
