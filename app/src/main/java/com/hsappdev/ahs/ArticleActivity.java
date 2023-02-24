package com.hsappdev.ahs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

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
    private TextView articleTextView;
    private TextView articleCategory;
    private TextView articleTitle;
    private TextView articleAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        articleImageURLSArrayList = new ArrayList<>();
        articleTextView = findViewById(R.id.article_text_view);
        articleViewPager2 = findViewById(R.id.article_board_viewpager2);
        articleCategory = findViewById(R.id.article_board_title_bold);
        articleTitle = findViewById(R.id.article_title);
        articleAuthor = findViewById(R.id.article_author);

        Intent srcIntent = getIntent();
        articleData = srcIntent.getParcelableExtra(ArticleDataType.ARTICLE_EXTRA_ID);

        Log.d(TAG, articleData.toString());

        articleTextView.setText(Html.fromHtml(articleData.getBody(), Html.FROM_HTML_MODE_COMPACT));
        articleTitle.setText(articleData.getTitle());
        articleCategory.setText(articleData.getCategoryID());
        articleAuthor.setText(articleData.getAuthor());
        articleImageURLSArrayList.addAll(Arrays.asList(articleData.getImageURLs()));



        //make sure that the new fragment passed is basically with the text of the image url
        articleFragmentStateAdapter = new ArticleFragmentStateAdapter(this, articleImageURLSArrayList);

        articleViewPager2.setAdapter(articleFragmentStateAdapter);



    }
}