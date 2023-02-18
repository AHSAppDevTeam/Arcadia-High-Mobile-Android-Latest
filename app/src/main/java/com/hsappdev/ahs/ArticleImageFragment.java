package com.hsappdev.ahs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.hsappdev.ahs.ui.viewpager2.ArticleFragmentStateAdapter;

public class ArticleImageFragment extends Fragment {
    ArticleFragmentStateAdapter articleFragmentStateAdapter;

    ViewPager2 viewPager2;
    public static final String ARG_OBJECT = "object";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_image, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        viewPager2 = view.findViewById(R.id.article_board_viewpager2);
        ((TextView) view.findViewById(R.id.article_board_imageView))
                .setText(String.valueOf(args.getStringArrayList(ARG_OBJECT)));
    }


}
