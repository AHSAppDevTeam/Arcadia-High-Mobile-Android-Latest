package com.hsappdev.ahs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ArticleImageFragment extends Fragment {
    private static final String ARG_IMAGE_URL = "imageUrl";

    private static String imageUrl;


    public static ArticleImageFragment newInstance(String param1) {
        ArticleImageFragment fragment = new ArticleImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            imageUrl = getArguments().getString(ARG_IMAGE_URL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_image, container, false);
        ImageView imageView = view.findViewById(R.id.article_image_view);
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);
        return view;
    }
}