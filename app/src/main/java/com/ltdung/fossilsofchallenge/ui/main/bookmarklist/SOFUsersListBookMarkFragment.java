package com.ltdung.fossilsofchallenge.ui.main.bookmarklist;

import android.os.Bundle;
import android.view.View;

import com.ltdung.fossilsofchallenge.BR;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.ViewModelProviderFactory;
import com.ltdung.fossilsofchallenge.databinding.FragmentSofUsersListBookmarkBinding;
import com.ltdung.fossilsofchallenge.ui.base.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by Dung Luong on 05/12/2019
 */
public class SOFUsersListBookMarkFragment extends BaseFragment<FragmentSofUsersListBookmarkBinding,
        SOFUsersListBookMarkViewModel> implements SOFUsersListBookMarkCallback {

    private static final String TAG = "SOFUsersListBookMarkFragment";

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    BookMarkUsersAdapter mBookMarkUsersAdapter;

    private SOFUsersListBookMarkViewModel sofUsersListBookMarkViewModel;

    private FragmentSofUsersListBookmarkBinding fragmentSofUsersListBookmarkBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sof_users_list_bookmark;
    }

    @Override
    public SOFUsersListBookMarkViewModel getViewModel() {
        sofUsersListBookMarkViewModel = ViewModelProviders.of(this, mViewModelProviderFactory).get(SOFUsersListBookMarkViewModel.class);
        return sofUsersListBookMarkViewModel;
    }

    public static SOFUsersListBookMarkFragment newInstance(){
        Bundle args = new Bundle();
        SOFUsersListBookMarkFragment fragment = new SOFUsersListBookMarkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentSofUsersListBookmarkBinding = getViewDataBinding();
        fragmentSofUsersListBookmarkBinding.setViewModel(sofUsersListBookMarkViewModel);
        sofUsersListBookMarkViewModel.setNavigator(this);

        initialAdapter();

        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        sofUsersListBookMarkViewModel.getListBookmarkedUsersLiveData().observe(this,
                bookmarkedUsers -> {
                    if(bookmarkedUsers.size() != 0){
                        fragmentSofUsersListBookmarkBinding.hint.setVisibility(View.GONE);
                    }else{
                        fragmentSofUsersListBookmarkBinding.hint.setVisibility(View.VISIBLE);
                    }
                    mBookMarkUsersAdapter.setBookmarkedUsers(bookmarkedUsers);
                });
    }

    private void initialAdapter() {
        fragmentSofUsersListBookmarkBinding.bookMarkedUsersRecyclerView.setLayoutManager(mLinearLayoutManager);
        fragmentSofUsersListBookmarkBinding.bookMarkedUsersRecyclerView.setAdapter(mBookMarkUsersAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            sofUsersListBookMarkViewModel.getBookmarkedUsers();
        }
    }
}
