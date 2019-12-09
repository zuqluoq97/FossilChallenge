package com.ltdung.fossilsofchallenge.ui.main.list.paging;

import com.ltdung.fossilsofchallenge.data.model.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by Dung Luong on 08/12/2019
 */
public class UserDiffUtilItemCallback extends DiffUtil.ItemCallback<User> {

    @Override
    public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        return oldItem.id() == newItem.id();
    }

    @Override
    public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        return oldItem.isBookMarked() == newItem.isBookMarked();
    }
}
