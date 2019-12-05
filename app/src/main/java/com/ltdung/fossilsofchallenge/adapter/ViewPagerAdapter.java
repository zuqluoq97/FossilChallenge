package com.ltdung.fossilsofchallenge.adapter;

import android.view.ViewGroup;

import com.ltdung.fossilsofchallenge.ui.base.BaseFragment;
import com.ltdung.fossilsofchallenge.ui.main.bookmarklist.SOFUsersListBookMarkFragment;
import com.ltdung.fossilsofchallenge.ui.main.list.SOFUsersListFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Dung Luong on 05/12/2019
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> mFragments;

    private BaseFragment mCurrentFragment;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mFragments = new ArrayList<>();
        mFragments.add(SOFUsersListFragment.newInstance());
        mFragments.add(SOFUsersListBookMarkFragment.newInstance());

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if(getCurrentFragment() != object){
            mCurrentFragment = (BaseFragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    public BaseFragment getCurrentFragment(){
        return mCurrentFragment;
    }
}
