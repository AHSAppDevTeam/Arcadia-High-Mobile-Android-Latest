package com.hsappdev.ahs.ui.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hsappdev.ahs.ArticleImageFragment;

import java.util.ArrayList;

public class ArticleFragmentStateAdapter extends FragmentStateAdapter {


    private ArrayList<String> imageUrls;

    public ArticleFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<String> imageUrls) {
        super(fragmentActivity);
        this.imageUrls = imageUrls;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String imageUrl = imageUrls.get(position);
        return ArticleImageFragment.newInstance(imageUrl);
    }
    @Override
    public int getItemCount() {
        return imageUrls.size();
    }
}
