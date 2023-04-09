package com.hsappdev.ahs.viewModels;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsappdev.ahs.R;
import com.hsappdev.ahs.db.DatabaseConstants;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores articles for a <i>specific</i> board
 */
public class ArticleBoardViewModel extends ViewModel {

    private static final String TAG = "ArticleBoardViewModel";

    // do not modify array
    private ArrayList<String> articleIds;

    // functions as a "collector" (collects articles as they load)
    private final List<ArticleDataType> articleDataListCollector = new ArrayList<>();

    private final MutableLiveData<List<ArticleDataType>> articleMutableLiveData = new MutableLiveData<>();

    public LiveData<List<ArticleDataType>> getArticles() {
        return articleMutableLiveData;
    }


    public ArticleBoardViewModel() {
    }

    public void startLoadingArticleData(ArrayList<String> articleIds, Resources r) {
        this.articleIds = articleIds;
        startLoadingArticleData(r);
    }

    private void startLoadingArticleData(Resources r) {
        for (String articleId : articleIds) {
            FirebaseUtil.loadArticle(articleId, r, new FirebaseUtil.DataCallback<ArticleDataType>() {
                @Override
                public void dataLoaded(ArticleDataType article) {
                    if(article != null) {
                        articleDataListCollector.add(article);

                        articleMutableLiveData.setValue(new ArrayList<>(articleDataListCollector)); // must make new copy
                    }
                }
            });

        }
    }
}
