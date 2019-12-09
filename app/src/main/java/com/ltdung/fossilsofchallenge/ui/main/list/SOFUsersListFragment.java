package com.ltdung.fossilsofchallenge.ui.main.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ltdung.fossilsofchallenge.BR;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.ViewModelProviderFactory;
import com.ltdung.fossilsofchallenge.data.model.NetworkState;
import com.ltdung.fossilsofchallenge.databinding.FragmentSofUsersListBinding;
import com.ltdung.fossilsofchallenge.ui.base.BaseFragment;
import com.ltdung.fossilsofchallenge.ui.main.list.paging.UsersPagedAdaptor;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by Dung Luong on 05/12/2019
 */
public class SOFUsersListFragment extends BaseFragment<FragmentSofUsersListBinding, SOFUsersListViewModel>
        implements SOFUsersListCallback, UsersPagedAdaptor.Callback{

    public static final String TAG = "SOFUsersListFragment";

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

    @Inject
    UsersPagedAdaptor mUsersPagedAdaptor;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    private SOFUsersListViewModel mSOFUsersListViewModel;

    private FragmentSofUsersListBinding mFragmentSOFUsersListBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sof_users_list;
    }

    @Override
    public SOFUsersListViewModel getViewModel() {
        mSOFUsersListViewModel = ViewModelProviders.of(this, mViewModelProviderFactory).get(SOFUsersListViewModel.class);
        return mSOFUsersListViewModel;
    }

    public static SOFUsersListFragment newInstance(){
        Bundle args = new Bundle();
        SOFUsersListFragment fragment = new SOFUsersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSOFUsersListBinding = getViewDataBinding();
        mFragmentSOFUsersListBinding.setViewModel(mSOFUsersListViewModel);
        mSOFUsersListViewModel.setNavigator(this);

        mSOFUsersListViewModel.onScreenCreated();
        initialAdapter();

        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mSOFUsersListViewModel.initialLoadState().observe(this, this::setProgress);

        mSOFUsersListViewModel.paginatedLoadState().observe(this,
                networkState -> mUsersPagedAdaptor.setNetworkState(networkState));

        mSOFUsersListViewModel.getUsers().observe(this,
                users -> mUsersPagedAdaptor.submitList(users));
    }

    private void setProgress(NetworkState initalLoadState){
        switch (initalLoadState.status()){
            case NetworkState.Status.SUCCESS:
                mSOFUsersListViewModel.setIsLoading(false);
                break;
            case NetworkState.Status.ERROR:
                mSOFUsersListViewModel.setIsLoading(false);
                handleError(initalLoadState.message());
                break;
            case NetworkState.Status.LOADING:
            default:
                mSOFUsersListViewModel.setIsLoading(true);
                break;
        }
    }

    private void initialAdapter() {
        mFragmentSOFUsersListBinding.usersRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFragmentSOFUsersListBinding.usersRecyclerView.setAdapter(mUsersPagedAdaptor);
    }

    @Override
    public void onRetryClicked() {
        mSOFUsersListViewModel.retry();
    }

    @Override
    public void onBookMarkClicked() {

    }
}
