package com.ltdung.fossilsofchallenge.ui.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {

    private WeakReference<N> mNavigator;

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private ObservableBoolean mIsLoading;

    public BaseViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider){
        mDataManager = dataManager;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
        mIsLoading = new ObservableBoolean(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }
}
