package com.ltdung.fossilsofchallenge.ui.main.bookmarklist;

import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.ui.base.BaseViewModel;
import com.ltdung.fossilsofchallenge.utils.AppLogger;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

/**
 * Created by Dung Luong on 09/12/2019
 */
public class BookMarkUserItemViewModel extends BaseViewModel {
    
    private static final String TAG = "BookMarkUserItemViewModel";

    @Bindable
    public ObservableField<User> userObservable;

    @Bindable
    public ObservableField<Boolean> bookmarkStatusObservable;

    @Bindable
    public ObservableField<String> tagsObservable;

    private final BookMarkUserItemClickListener listener;

    private User user;

    public BookMarkUserItemViewModel(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     User user,
                                     BookMarkUserItemClickListener listener) {
        super(dataManager, schedulerProvider);
        this.userObservable = new ObservableField<>(user);
        this.bookmarkStatusObservable = new ObservableField<>(user.isBookMarked());
        this.user = user;
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

    public void onBookmarkButtonClick(){
        setIsLoading(true);
        removeBookmarkedUser(user);
    }

    public void onItemClick(){
        listener.onItemClick(user);
    }

    private void removeBookmarkedUser(User user){
        getCompositeDisposable().add(getDataManager()
                .removeBookMarkedUser(user)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    listener.showMessage("Remove from bookmark");
                    listener.removeBookmarkUser(user);
                    setIsLoading(false);
                }, throwable -> {
                    AppLogger.d(throwable, TAG);
                    setIsLoading(false);
                }));
    }

    public interface BookMarkUserItemClickListener{
        void showMessage(String message);
        void removeBookmarkUser(User user);
        void onItemClick(User user);
    }
}
