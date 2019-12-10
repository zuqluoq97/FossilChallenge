package com.ltdung.fossilsofchallenge.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ltdung.fossilsofchallenge.BR;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.ViewModelProviderFactory;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.databinding.ActivityUserDetailsBinding;
import com.ltdung.fossilsofchallenge.ui.base.BaseActivity;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import static com.ltdung.fossilsofchallenge.utils.AppConstants.SELECTED_USER;

/**
 * Created by Dung Luong on 10/12/2019
 */
public class UserDetailsActivity extends BaseActivity<ActivityUserDetailsBinding, UserDetailsViewModel>
        implements UserDetailsNavigator {

    private static final String TAG = "UserDetailsActivity";

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

    private ActivityUserDetailsBinding mActivityUserDetailsBinding;

    private UserDetailsViewModel mUserDetailsViewModel;

    public static Intent newIntent(Context context){
        return new Intent(context, UserDetailsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_details;
    }

    @Override
    public UserDetailsViewModel getViewModel() {
        mUserDetailsViewModel = ViewModelProviders.of(this, mViewModelProviderFactory).get(UserDetailsViewModel.class);
        return mUserDetailsViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUserDetailsBinding = getViewDataBinding();
        mActivityUserDetailsBinding.setViewModel(mUserDetailsViewModel);
        mUserDetailsViewModel.setNavigator(this);

        if(this.getIntent().getExtras() != null) {
            User selectedUser = this.getIntent().getExtras().getParcelable(SELECTED_USER);
            mUserDetailsViewModel.setUserObservableField(selectedUser);

            Glide.with(mActivityUserDetailsBinding.avatar).load(selectedUser.imageLink())
                    .apply(RequestOptions.placeholderOf(R.color.grey))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mActivityUserDetailsBinding.avatar);

            StringBuilder tags = new StringBuilder();
            if(selectedUser.tags().tags() != null) {
                for (int i = 0; i < selectedUser.tags().tags().size(); i++) {
                    tags.append(selectedUser.tags().tags().get(i).name());
                    if(i != selectedUser.tags().tags().size() -1){
                        tags.append(", ");
                    }
                }
            }

            mActivityUserDetailsBinding.tags.setText(tags.toString());
        }

        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = mActivityUserDetailsBinding.toolbar;
        toolbar.setTitle(R.string.user_detail_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}