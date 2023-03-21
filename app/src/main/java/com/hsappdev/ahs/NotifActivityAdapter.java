package com.hsappdev.ahs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsappdev.ahs.newDataTypes.ArticleDataType;

import java.util.ArrayList;
import java.util.List;

public class NotifActivityAdapter extends RecyclerView.Adapter<NotifActivityAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ArticleDataType> data;


    NotifActivityAdapter(Context context, List<ArticleDataType> data){
        this.layoutInflater = LayoutInflater.from(context);
        // this.data = data;
        this.data = new ArrayList<>();
        ArticleDataType a = new ArticleDataType();
        for (int i = 0; i < 10; i++) {
            a.setTitle("hi, there" + i);
            a.setCategoryDisplayName("category " + i);
            this.data.add(a);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_notification_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ArticleDataType article = data.get(position);
        String topic = article.getCategoryDisplayName();
        holder.topic.setText(topic);

        String title = article.getTitle();
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<ArticleDataType> articleDataTypes) {
        this.data = articleDataTypes;
        this.notifyDataSetChanged(); // updates the recycler view
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView topic, title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.notification_topic);
            title = itemView.findViewById(R.id.notification_title);
        }
    }

}
