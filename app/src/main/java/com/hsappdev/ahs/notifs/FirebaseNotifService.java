package com.hsappdev.ahs.notifs;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hsappdev.ahs.ArticleActivity;
import com.hsappdev.ahs.NotifActivity;
import com.hsappdev.ahs.R;
import com.hsappdev.ahs.db.ArticleRepository;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.util.FirebaseUtil;

import java.util.Map;

public class FirebaseNotifService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseNotifService";

    private ArticleRepository articleRepository;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.d(TAG, "Message Received from: " + message.getFrom());
        if(articleRepository == null)
            articleRepository = new ArticleRepository(getApplication());
        // Check if message contains a data payload.
        if (message.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + message.getData());
            Map<String, String> dataMap = message.getData();
            String articleID = dataMap.get("articleID");
            FirebaseUtil.loadArticle(articleID, getResources(), new FirebaseUtil.DataCallback<ArticleDataType>() {
                @Override
                public void dataLoaded(ArticleDataType a) {
                    a.setIsNotification(1); // 1 == true
                    articleRepository.addOrUpdate(a);

                    RemoteMessage.Notification notification = message.getNotification();
                    if(notification != null) {
                        sendNotification(notification.getTitle(),
                                notification.getBody(),
                                getResources(),
                                a);
                    }
                }
            });
        }
    }

    private void sendNotification(String title, String messageBody, Resources r, ArticleDataType article) {
        Intent intent;
        if(article != null) {
            // if there is an article, show it
            intent = new Intent(this, ArticleActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(ArticleDataType.ARTICLE_EXTRA_ID, article);
        } else {
            // else show the notif page
            intent = new Intent(this, NotifActivity.class);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String channelId = r.getString(R.string.notificationChannelID);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.applogoclear_fullres)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    r.getString(R.string.notificationChannelName),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(article.getArticleID().hashCode(), notificationBuilder.build());
    }
}
