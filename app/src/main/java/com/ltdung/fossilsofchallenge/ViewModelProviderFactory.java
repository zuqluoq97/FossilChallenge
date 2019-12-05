package com.ltdung.fossilsofchallenge;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.ui.main.MainViewModel;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Dung Luong on 05/12/2019
 */
@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory{

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager,
                                    SchedulerProvider schedulerProvider){
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    @SuppressWarnings({"unchecked"})
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(mDataManager, mSchedulerProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass);
    }
}
