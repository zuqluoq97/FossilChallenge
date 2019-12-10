package com.ltdung.fossilsofchallenge.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ltdung.fossilsofchallenge.BR;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.ViewModelProviderFactory;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.databinding.ActivityMainBinding;
import com.ltdung.fossilsofchallenge.databinding.ActivityUserDetailsBinding;
import com.ltdung.fossilsofchallenge.ui.base.BaseActivity;
import com.ltdung.fossilsofchallenge.ui.main.MainNavigator;
import com.ltdung.fossilsofchallenge.ui.main.MainViewModel;
import com.ltdung.fossilsofchallenge.utils.AppLogger;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

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