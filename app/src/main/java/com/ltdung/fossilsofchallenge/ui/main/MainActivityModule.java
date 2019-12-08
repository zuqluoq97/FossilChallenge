package com.ltdung.fossilsofchallenge.ui.main;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltdung.fossilsofchallenge.R;
import com.ltdung.fossilsofchallenge.ui.main.bookmarklist.SOFUsersListBookMarkFragment;
import com.ltdung.fossilsofchallenge.ui.main.list.SOFUsersListFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dung Luong on 05/12/2019
 */
@Module
public class MainActivityModule {

    @Provides
    FragmentPagerItemAdapter provideFragmentPagerItemAdapter(MainActivity mainActivity){
        return new FragmentPagerItemAdapter(
                mainActivity.getSupportFragmentManager(),
                FragmentPagerItems.with(mainActivity)
                        .add(R.string.tab_user, SOFUsersListFragment.class)
                        .add(R.string.tab_user_bookmarked, SOFUsersListBookMarkFragment.class)
                        .create());
    }

    @Provides
    SmartTabLayout.TabProvider provideTabProvider(MainActivity mainActivity){
        final LayoutInflater inflater = LayoutInflater.from(mainActivity);
        final Resources res = mainActivity.getResources();

        return (container, position, adapter) -> {
            View itemView = inflater.inflate(R.layout.custom_tab_icon_and_text, container, false);
            TextView title = itemView.findViewById(R.id.tab_title);
            title.setText(adapter.getPageTitle(position));

            ImageView icon = itemView.findViewById(R.id.tab_icon);
            switch (position){
                case 0:
                    icon.setImageDrawable(res.getDrawable(R.drawable.users));
                    break;
                case 1:
                    icon.setImageDrawable(res.getDrawable(R.drawable.gray_heart));
                    break;
                default:
                    throw new IllegalStateException("Invalid position: " + position);
            }

            return itemView;
        };
    }

}
