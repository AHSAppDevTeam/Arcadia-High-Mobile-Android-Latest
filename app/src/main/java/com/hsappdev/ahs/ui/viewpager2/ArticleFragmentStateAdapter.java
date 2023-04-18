package com.hsappdev.ahs.ui.viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;
import com.hsappdev.ahs.ArticleImageFragment;

import java.util.ArrayList;

public class ArticleFragmentStateAdapter extends FragmentStateAdapter {
    private ArrayList<Glide> fragmentList = new ArrayList<>();

    public ArticleFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Glide> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new ArticleImageFragment();
        Bundle args = new Bundle();
//        args.putStringArrayList(ArticleImageFragment.ARG_OBJECT, fragmentList);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
