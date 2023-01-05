package com.hsappdev.ahs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.ui.reusable.BackNavigationActivity;

public class ArticleActivity extends BackNavigationActivity {

    private static final String TAG = "ArticleActivity";

    private ArticleDataType articleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent srcIntent = getIntent();
        articleData = srcIntent.getParcelableExtra(ArticleDataType.ARTICLE_EXTRA_ID);

        Log.d(TAG, articleData.toString());

    }
}