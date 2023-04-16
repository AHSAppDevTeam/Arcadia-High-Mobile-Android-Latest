package com.hsappdev.ahs;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsappdev.ahs.db.ArticleRepository;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.ui.reusable.recyclerview.AbstractDataRecyclerView;
import com.hsappdev.ahs.ui.reusable.recyclerview.DataTypeViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * for jennifer
 */
public class NotifActivity extends AppCompatActivity {
    public String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                if(second == 1){
                    convTime = second + " Second " + suffix;
                }
                else{
                    convTime = second + " Seconds " + suffix;
                }

            } else if (minute < 60) {
                if(minute == 1){
                    convTime = minute + " Minute " + suffix;
                }
                else{
                    convTime = minute + " Minutes "+suffix;
                }

            } else if (hour < 24) {
                if(hour == 1) {
                    convTime = hour + " Hour " + suffix;
                }
                else{
                    convTime = hour + " Hours "+suffix;
                }

            } else if (day >= 7) {
                if (day > 365) {
                    if(day < 730){
                        convTime = (day / 365) + " Year " + suffix;
                    }
                    else{
                        convTime = (day / 365) + " Years " + suffix;
                    }

                } else if (day > 30) {
                    if(day < 60){
                        convTime = (day / 30) + " Month " + suffix;
                    }
                    else{
                        convTime = (day / 30) + " Months " + suffix;
                    }

                } else {
                    if(day < 14){
                        convTime = (day / 7) + " Week " + suffix;
                    }
                    else{
                        convTime = (day / 7) + " Weeks " + suffix;
                    }
                }

            } else if (day < 7) {
                if(day == 1){
                    convTime = day+" Day "+suffix;
                }
                else{
                    convTime = day+" Days "+suffix;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        RecyclerView notifRecyclerView = findViewById(R.id.notificationRecyclerView);

        AbstractDataRecyclerView<ArticleDataType> adapter = new AbstractDataRecyclerView<>(R.layout.activity_notification_view, new DataTypeViewAdapter<ArticleDataType>() {
            private TextView topic, title, date;

            private ImageView notifColor;
            private CardView notification;

            @Override
            public void setDataToView(ArticleDataType data, View itemView) {
                topic = itemView.findViewById(R.id.notification_topic);
                title = itemView.findViewById(R.id.notification_title);
                date = itemView.findViewById(R.id.notification_date);
                notifColor = itemView.findViewById(R.id.notification_color);


                String notificationTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (data.getTimestamp()*1000));

                title.setText(data.getTitle());
                topic.setText(data.getCategoryID());
                date.setText(covertTimeToText(notificationTime));

                topic.setTextColor(data.getCategoryDisplayColor());
                notifColor.setBackgroundColor(data.getCategoryDisplayColor());
            }

            @Override
            public void handleOnClick(ArticleDataType data, View itemView) {

                Intent notificationIntent = new Intent(itemView.getContext(), ArticleActivity.class);
                notificationIntent.putExtra(ArticleDataType.ARTICLE_EXTRA_ID, data);
                itemView.getContext().startActivity(notificationIntent);

            }
        });

        notifRecyclerView.setAdapter(adapter);
        notifRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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