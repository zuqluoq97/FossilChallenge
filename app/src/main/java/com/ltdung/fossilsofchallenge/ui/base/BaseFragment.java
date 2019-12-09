package com.ltdung.fossilsofchallenge.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel>
        extends Fragment {

    private BaseActivity mActivity;
    private View mRootView;
    private T mViewDataBinding;
    private V mViewModel;

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    private void performDependencyInjection(){
        AndroidSupportInjection.inject(this);
    }

    public void handleError(String error){
        mActivity.handleError(error);
    }

    public void showMessage(String message){
        mActivity.showMessage(message);
    }

    public T getViewDataBinding(){
        return mViewDataBinding;
    }

    public interface Callback{
        void onFragmentAttached(int res, Fragment instance, String tag);

        void onFragmentDetached(String tag);
    }
}
