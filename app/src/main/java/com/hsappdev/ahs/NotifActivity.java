package com.hsappdev.ahs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.hsappdev.ahs.db.ArticleRepository;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * for jennifer
 */
public class NotifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        RecyclerView notifRecyclerView = findViewById(R.id.notificationRecyclerView);
        NotifActivityAdapter adapter = new NotifActivityAdapter(this, null); // TESTING
        notifRecyclerView.setAdapter(adapter);
        // notifRecyclerView.setHasFixedSize(true);
        notifRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArticleRepository articleRepository = new ArticleRepository(getApplication());
        articleRepository.getAllArticles().observe(this, new Observer<List<ArticleDataType>>() {
            @Override
            public void onChanged(List<ArticleDataType> articleDataTypes) {
                List<ArticleDataType> isNotification = new ArrayList<>();
                Log.d("amongus", ""+articleDataTypes.size());
                for (int i = 0; i < articleDataTypes.size(); i++) {
                    // if(articleDataTypes.get(i).getIsNotification() == 1){
                        isNotification.add(articleDataTypes.get(i));
                    // }
                }
                adapter.update(isNotification);

            }
        });


    }
}