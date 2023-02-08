package com.hsappdev.ahs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.ui.reusable.BackNavigationActivity;
import com.hsappdev.ahs.ui.viewpager2.ArticleFragmentStateAdapter;

public class ArticleActivity extends BackNavigationActivity {

    private static final String TAG = "ArticleActivity";

    private ArticleDataType articleData;
    private ViewPager2 articleViewPager2;
    ArticleFragmentStateAdapter articleFragmentStateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        articleViewPager2 = findViewById(R.id.article_board_viewpager2);
        articleFragmentStateAdapter = new ArticleFragmentStateAdapter(getSupportFragmentManager(), getLifecycle());
        articleFragmentStateAdapter.addFragment(new Fragment());





        articleData.getImageURLs();





        Intent srcIntent = getIntent();
        articleData = srcIntent.getParcelableExtra(ArticleDataType.ARTICLE_EXTRA_ID);

        Log.d(TAG, articleData.toString());

    }
}