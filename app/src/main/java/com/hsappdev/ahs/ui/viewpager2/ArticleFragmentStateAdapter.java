package com.hsappdev.ahs.ui.viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hsappdev.ahs.ArticleImageFragment;

import java.util.ArrayList;

//Change into actual attribute
public class ArticleFragmentStateAdapter extends FragmentStateAdapter {
    private ArrayList<String> fragmentList;

    public ArticleFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<String> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new ArticleImageFragment();
        Bundle args = new Bundle();
        args.putString(ArticleImageFragment.ARG_OBJECT, fragmentList.get(position));
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
