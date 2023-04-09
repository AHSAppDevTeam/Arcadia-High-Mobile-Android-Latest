package com.hsappdev.ahs.util;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsappdev.ahs.R;
import com.hsappdev.ahs.db.DatabaseConstants;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;

import java.util.ArrayList;

public class FirebaseUtil {

    private static final String TAG = "FirebaseUtil";

    public static void loadArticle(String articleId, Resources r, FirebaseUtil.DataCallback<ArticleDataType> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance(FirebaseApp.getInstance(DatabaseConstants.FIREBASE_REALTIME_DB))
                .getReference()
                .child("articles")
                .child(articleId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // extract values and set
                Log.d(TAG, String.format("Article Id Print: %s", articleId));

                ArticleDataType article = new ArticleDataType(articleId);

                String author = snapshot.child(r.getString(R.string.db_articles_author)).getValue(String.class);
                String title = snapshot.child(r.getString(R.string.db_articles_title)).getValue(String.class);
                String body = snapshot.child(r.getString(R.string.db_articles_body)).getValue(String.class);
                String category = snapshot.child(r.getString(R.string.db_articles_categoryID)).getValue(String.class);
                ArrayList<String> imageURLs = new ArrayList<>();
                ArrayList<String> videoURLs = new ArrayList<>();
                for (DataSnapshot imageURL : snapshot.child(r.getString(R.string.db_articles_imageURLs)).getChildren()) {
                    imageURLs.add(imageURL.getValue(String.class));
                }
                for (DataSnapshot videoURL : snapshot.child(r.getString(R.string.db_articles_videoURLs)).getChildren()) {
                    videoURLs.add(videoURL.getValue(String.class));
                }

                Long extractedTimestamp = snapshot.child(r.getString(R.string.db_articles_timestamp)).getValue(long.class);

                long timestamp = (extractedTimestamp == null) ? 0 : extractedTimestamp;

                article.setArticleID(articleId);
                article.setAuthor(author);
                article.setTitle(title);
                article.setBody(body);
                article.setCategoryID(category);
                article.setImageURLs(imageURLs.toArray(new String[0]));
                article.setVideoURLs(videoURLs.toArray(new String[0]));
                article.setTimestamp(timestamp);

                Log.d(TAG, article.toString());

                callback.dataLoaded(article);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public static abstract class DataCallback<T> {
        public abstract void dataLoaded(T a);
    }

}
