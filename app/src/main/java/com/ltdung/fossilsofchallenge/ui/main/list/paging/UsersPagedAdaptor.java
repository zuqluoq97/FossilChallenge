package com.ltdung.fossilsofchallenge.ui.main.list.paging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.data.DataManager;
import com.ltdung.fossilsofchallenge.data.model.NetworkState;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.databinding.ItemLoadingBinding;
import com.ltdung.fossilsofchallenge.databinding.ItemNetworkFailureBinding;
import com.ltdung.fossilsofchallenge.databinding.ItemUserBinding;
import com.ltdung.fossilsofchallenge.ui.base.BaseActivity;
import com.ltdung.fossilsofchallenge.ui.main.MainActivity;
import com.ltdung.fossilsofchallenge.utils.rx.SchedulerProvider;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Dung Luong on 08/12/2019
 */
public class UsersPagedAdaptor extends PagedListAdapter<User, RecyclerView.ViewHolder> {

    private final Callback callback;

    private Context context;

    private NetworkState currentNetworkState;

    private DataManager dataManager;

    private SchedulerProvider schedulerProvider;

    public UsersPagedAdaptor(@NonNull DiffUtil.ItemCallback<User> diffCallback,
                             Callback callback,
                             DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        super(diffCallback);
        this.callback = callback;
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType){
            case R.layout.item_loading:
                ItemLoadingBinding itemLoadingBinding = ItemLoadingBinding.inflate(inflater, parent, false);
                return new LoadingHolder(itemLoadingBinding);
            case R.layout.item_network_failure:
                ItemNetworkFailureBinding itemNetworkFailureBinding = ItemNetworkFailureBinding.inflate(inflater, parent, false);
                NetworkFailureHolder networkFailureHolder = new NetworkFailureHolder(itemNetworkFailureBinding);
                networkFailureHolder.binding.retryButton.setOnClickListener(v -> callback.onRetryClicked());
                return networkFailureHolder;
            case R.layout.item_user:
            default:
                ItemUserBinding itemUserBinding = ItemUserBinding.inflate(inflater, parent, false);
                return new UserHolder(itemUserBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case R.layout.item_loading:
                LoadingHolder loadingHolder = (LoadingHolder) holder;
                loadingHolder.binding.spinner.setVisibility(View.VISIBLE);
                break;
            case R.layout.item_network_failure:
                NetworkFailureHolder networkHolder = (NetworkFailureHolder) holder;
                networkHolder.binding.networkPbm.setText(currentNetworkState.message());
                break;
            case R.layout.item_user:
            default:
                ((UserHolder) holder).onBind(getItem(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            if (currentNetworkState.status() == NetworkState.Status.LOADING) {
                return R.layout.item_loading;
            } else {
                return R.layout.item_network_failure;
            }
        } else {
            return R.layout.item_user;
        }
    }

    public void setNetworkState(NetworkState newNetworkState){
        NetworkState previousNetworkState = currentNetworkState;
        boolean hadExtraRow = hasExtraRow();
        currentNetworkState = newNetworkState;
        boolean hasExtraRow = hasExtraRow();
        if(hadExtraRow != hasExtraRow){
            if(hadExtraRow){
                notifyItemRemoved(getItemCount());
            }else{
                notifyItemInserted(getItemCount());
            }
        }else if(hasExtraRow && !previousNetworkState.equals(newNetworkState)){
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + getExtraRow();
    }

    private int getExtraRow(){
        if(hasExtraRow()) return 1;
        else return 0;
    }

    private boolean hasExtraRow(){
        return currentNetworkState != null
                && currentNetworkState.status() != NetworkState.Status.SUCCESS;
    }

    class UserHolder extends RecyclerView.ViewHolder
            implements UserItemViewModel.UserItemClickListener {

        private ItemUserBinding binding;

        public UserHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(User user){
            UserItemViewModel userItemViewModel = new UserItemViewModel(dataManager,
                    schedulerProvider, user, this);
            binding.setViewModel(userItemViewModel);
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
        public void onItemClick(User user) {
            ((MainActivity) context).moveToUserDetailActivity(user);
        }
    }

    class LoadingHolder extends RecyclerView.ViewHolder{

        private ItemLoadingBinding binding;

        public LoadingHolder(ItemLoadingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class NetworkFailureHolder extends RecyclerView.ViewHolder {

        private ItemNetworkFailureBinding binding;

        NetworkFailureHolder(ItemNetworkFailureBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback{
        void onRetryClicked();
    }
}
