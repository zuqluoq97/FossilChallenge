package com.ltdung.fossilsofchallenge.ui.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.utils.rx.CommonUtils;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity implements BaseFragment.Callback{

    private V mViewModel;
    private T mViewDataBinding;

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();

    private static long mBackPressResponseTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();
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

    private void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    private void performDataBinding(){
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public void restart(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void showMessage(String message){
        CommonUtils.showQuickToast(this, message);
    }

    public void handleError(String error){
        CommonUtils.showLongToast(this, error);
    }

}
