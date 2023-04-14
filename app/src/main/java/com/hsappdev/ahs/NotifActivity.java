package com.hsappdev.ahs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.hsappdev.ahs.db.ArticleRepository;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.ui.reusable.recyclerview.AbstractDataRecyclerView;
import com.hsappdev.ahs.ui.reusable.recyclerview.DataTypeViewAdapter;

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
        //NotifActivityAdapter adapter = new NotifActivityAdapter(this, null); // TESTING

        AbstractDataRecyclerView<ArticleDataType> adapter = new AbstractDataRecyclerView<>(R.layout.activity_notification_view, new DataTypeViewAdapter<ArticleDataType>() {
            private TextView topic, title;

            @Override
            public void setDataToView(ArticleDataType data, View itemView) {
                topic = itemView.findViewById(R.id.notification_topic);
                title = itemView.findViewById(R.id.notification_title);

                topic.setText(data.getCategoryDisplayName());
                title.setText(data.getTitle());
            }

            @Override
            public void handleOnClick(ArticleDataType data, View itemView) {

            }
        });

        notifRecyclerView.setAdapter(adapter);
        // notifRecyclerView.setHasFixedSize(true);
        notifRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ArrayList<ArticleDataType> randomArrayList = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            ArticleDataType article = new ArticleDataType();
//            article.setTitle("hi");
//            article.setCategoryDisplayName("hey there");
//            randomArrayList.add(article);
//        }


//         adapter.setDataList(randomArrayList);

        List<ArticleDataType> isNotification = new ArrayList<>();

        ArticleRepository articleRepository = new ArticleRepository(getApplication());
        articleRepository.getAllArticles().observe(this, new Observer<List<ArticleDataType>>() {
            @Override
            public void onChanged(List<ArticleDataType> articleDataTypes) {
                List<ArticleDataType> isNotification = new ArrayList<>();
                for (int i = 0; i < articleDataTypes.size(); i++) {
                    isNotification.add(articleDataTypes.get(i));

                }
                adapter.setDataList(isNotification);
            }


        });


    }
}