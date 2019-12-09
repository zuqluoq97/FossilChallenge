package com.ltdung.fossilsofchallenge.ui.main.bookmarklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.databinding.ItemBookmarkedUserBinding;
import com.ltdung.fossilsofchallenge.ui.base.BaseActivity;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Dung Luong on 09/12/2019
 */
public class BookMarkUsersAdapter
        extends RecyclerView.Adapter<BookMarkUsersAdapter.BookMarkUserViewHolder> {

    private Context context;

    private List<User> bookmarkedUsers;

    private DataManager dataManager;

    private SchedulerProvider schedulerProvider;

    public BookMarkUsersAdapter(DataManager dataManager,
                                SchedulerProvider schedulerProvider){
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        bookmarkedUsers = new ArrayList<>();
    }

    public void setBookmarkedUsers(List<User> users){
        bookmarkedUsers.clear();
        bookmarkedUsers.addAll(users);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookMarkUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        return new BookMarkUserViewHolder(ItemBookmarkedUserBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkUserViewHolder holder, int position) {
        holder.onBind(bookmarkedUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return bookmarkedUsers.size();
    }

    class BookMarkUserViewHolder extends RecyclerView.ViewHolder
            implements BookMarkUserItemViewModel.BookMarkUserItemClickListener{

        private ItemBookmarkedUserBinding binding;

        public BookMarkUserViewHolder(ItemBookmarkedUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(User user){
            BookMarkUserItemViewModel bookMarkUserItemViewModel = new BookMarkUserItemViewModel(dataManager,
                    schedulerProvider, user, this);
            binding.setViewModel(bookMarkUserItemViewModel);
            binding.executePendingBindings();

            if(user.imageLink() != null){
                Glide.with(binding.avatar).load(user.imageLink())
                        .apply(RequestOptions.placeholderOf(R.color.grey))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.avatar);
            }

        }

        @Override
        public void showMessage(String message) {
            ((BaseActivity) context).showMessage(message);
        }

        @Override
        public void removeBookmarkUser(User user) {
            bookmarkedUsers.remove(user);
            notifyDataSetChanged();
        }
    }
}
