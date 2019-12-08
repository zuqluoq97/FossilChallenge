package com.ltdung.fossilsofchallenge.ui.main.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ltdung.fossilsofchallenge.BR;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.ViewModelProviderFactory;
import com.ltdung.fossilsofchallenge.databinding.FragmentSofUsersListBinding;
import com.ltdung.fossilsofchallenge.ui.base.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Dung Luong on 05/12/2019
 */
public class SOFUsersListFragment extends BaseFragment<FragmentSofUsersListBinding, SOFUsersListViewModel>
        implements SOFUsersListCallback{

    public static final String TAG = "SOFUsersListFragment";

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

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
    }

}
