package com.ltdung.fossilsofchallenge.ui.main.list.paging;

import com.ltdung.fossilsofchallenge.BR;
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
public class UserItemViewModel extends BaseViewModel {

    private static final String TAG = "UserItemViewModel";

    @Bindable
    public ObservableField<User> userObservable;

    @Bindable
    public ObservableField<Boolean> bookmarkStatusObservable;

    @Bindable
    public ObservableField<String> tagsObservable;

    private final UserItemClickListener listener;

    private User user;

    public UserItemViewModel(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             User user,
                             UserItemClickListener listener) {
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
        checkBookmarkStatus(user);
    }

    public void onBookmarkButtonClick(){
        setIsLoading(true);
        if(user.isBookMarked()){
            user = user.withIsBookMarked(false);
            removeBookmarkedUser(user);
        }else{
            user = user.withIsBookMarked(true);
            addBookmarkedUser(user);
        }
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
                            setIsLoading(false);
                        }, throwable -> {
                            AppLogger.d(throwable, TAG);
                            setIsLoading(false);
                        }));
    }

    private void addBookmarkedUser(User user){
        getCompositeDisposable().add(getDataManager()
                .insertBookMarkedUser(user)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    listener.showMessage("Add to bookmark");
                    setIsLoading(false);
                }, throwable -> {
                    AppLogger.d(throwable, TAG);
                    setIsLoading(false);
                }));
    }

    private void checkBookmarkStatus(User user){
        getCompositeDisposable().add(getDataManager()
                .getBookmarkedStatus(user)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(count -> {
                    if(count > 0){
                        updateBookMarkUser(user);
                        bookmarkStatusObservable.set(true);
                        notifyPropertyChanged(BR.bookmarkStatusObservable);
                    }
                }, throwable -> {
                    AppLogger.d(throwable, TAG);
                }));
    }

    private void updateBookMarkUser(User user){
        getCompositeDisposable().add(getDataManager()
                .updateBookMarkedUser(user.withIsBookMarked(true))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {

                }, throwable -> {
                    AppLogger.d(throwable, TAG);
                }));
    }

    public interface UserItemClickListener{
        void showMessage(String message);
        void onItemClick(User user);
    }
}
