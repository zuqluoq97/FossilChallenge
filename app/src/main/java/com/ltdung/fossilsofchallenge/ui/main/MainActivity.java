package com.ltdung.fossilsofchallenge.ui.main;

import android.os.Bundle;

import com.ltdung.fossilsofchallenge.BR;
import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.ViewModelProviderFactory;
import com.ltdung.fossilsofchallenge.data.model.User;
import com.ltdung.fossilsofchallenge.databinding.ActivityMainBinding;
import com.ltdung.fossilsofchallenge.ui.base.BaseActivity;
import com.ltdung.fossilsofchallenge.ui.detail.UserDetailsActivity;
import com.ltdung.fossilsofchallenge.utils.CommonUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.ltdung.fossilsofchallenge.utils.AppConstants.SELECTED_USER;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator, HasSupportFragmentInjector {

    private static final String TAG = "MainActivity";

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

    @Inject
    FragmentPagerItemAdapter mFragmentPagerItemAdapter;

    @Inject
    SmartTabLayout.TabProvider mCustomTabProvider;

    private ActivityMainBinding mMainActivityBinding;

    private MainViewModel mMainViewModel;

    private ViewPager mViewPager;

    private SmartTabLayout mSmartTabLayout;

    private static long mBackPressResponseTime;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelProviderFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityBinding = getViewDataBinding();
        mMainActivityBinding.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);

        initToolbar();
        initTabLayout();
    }

    private void initToolbar() {
        Toolbar toolbar = mMainActivityBinding.toolbar;
        toolbar.setTitle(R.string.home_title);
    }

    private void initTabLayout() {
        mViewPager = mMainActivityBinding.viewpager;
        mViewPager.setAdapter(mFragmentPagerItemAdapter);
        mViewPager.setAdapter(mFragmentPagerItemAdapter);
        mViewPager.setOffscreenPageLimit(2);

        mSmartTabLayout = mMainActivityBinding.viewpagertab;
        mSmartTabLayout.setCustomTabView(mCustomTabProvider);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    @Override
    public void moveToUserDetailActivity(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_USER, user);
        startActivity(UserDetailsActivity.newIntent(this).putExtras(bundle));
    }

    @Override
    public void onBackPressed() {
        if(mBackPressResponseTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
        }else{
            CommonUtils.showQuickToast(this, getString(R.string.double_to_exit));
        }
        mBackPressResponseTime = System.currentTimeMillis();
    }
}
