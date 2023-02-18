package com.hsappdev.ahs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.ui.reusable.BackNavigationActivity;
import com.hsappdev.ahs.ui.viewpager2.ArticleFragmentStateAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ArticleActivity extends BackNavigationActivity {

    private static final String TAG = "ArticleActivity";

    private ArticleDataType articleData;
    private ViewPager2 articleViewPager2;
    private ArticleFragmentStateAdapter articleFragmentStateAdapter;
    private ArrayList<String> articleImageURLSArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        articleImageURLSArrayList = new ArrayList<>();

        articleViewPager2 = findViewById(R.id.article_board_viewpager2);
        TabLayout articleTabLayout = findViewById(R.id.article_tab_layout);
        Intent srcIntent = getIntent();
        articleData = srcIntent.getParcelableExtra(ArticleDataType.ARTICLE_EXTRA_ID);

        Log.d(TAG, articleData.toString());

        articleImageURLSArrayList.addAll(Arrays.asList(articleData.getImageURLs()));



        //make sure that the new fragment passed is basically with the text of the image url
        articleFragmentStateAdapter = new ArticleFragmentStateAdapter(this, articleImageURLSArrayList);

        articleViewPager2.setAdapter(articleFragmentStateAdapter);



    }
}