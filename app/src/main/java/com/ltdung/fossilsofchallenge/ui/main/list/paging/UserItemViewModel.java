package com.ltdung.fossilsofchallenge.ui.main.list.paging;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.model.Tag;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.ui.base.BaseViewModel;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

/**
 * Created by Dung Luong on 09/12/2019
 */
public class UserItemViewModel extends BaseViewModel {

    @Bindable
    public ObservableField<User> userObservable;

    @Bindable
    public ObservableField<String> tagsObservable;

    private final UserItemClickListener listener;

    public UserItemViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             User user,
                             UserItemClickListener listener) {
        super(dataManager, schedulerProvider);
        this.userObservable = new ObservableField<>(user);
        this.listener = listener;
        StringBuilder tags = new StringBuilder();
        if(user.tags().tags() != null) {
            for (int i = 0; i < user.tags().tags().size(); i++) {
                tags.append(user.tags().tags().get(i).name());
                if(i != user.tags().tags().size() -1){
                    tags.append(", ");
                }
            }
        }
        this.tagsObservable = new ObservableField<>(tags.toString());
    }

    public interface UserItemClickListener{
        void onBookmarkButtonClick();
    }
}
