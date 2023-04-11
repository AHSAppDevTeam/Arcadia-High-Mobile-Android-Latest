package com.hsappdev.ahs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;

import java.util.ArrayList;

public class ArticleSectionAdapter extends RecyclerView.Adapter<ArticleSectionAdapter.ViewHolder> {
    Context context;
    ArrayList<ArticleDataType> articleSuggestions;


    public ArticleSectionAdapter(Context context, ArrayList<ArticleDataType> articleSuggestions) {
        this.context = context;
        this.articleSuggestions = articleSuggestions;
    }


    @NonNull
    @Override
    public ArticleSectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.article_general_section_holder, parent, false);

        return new ArticleSectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleSectionAdapter.ViewHolder holder, int position) {
        holder.articleTitleBottom.setText(articleSuggestions.get(position).getTitle());
        holder.articleImageBottom.setImageState(Glide.with(context).load(articleSuggestions.get(position).getImageURLs()).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(amongus));

    }

    @Override
    public int getItemCount() {
        return articleSuggestions.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView articleTitleBottom;
        ImageView articleImageBottom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleTitleBottom = itemView.findViewById(R.id.article_title_bottom);
            articleImageBottom = itemView.findViewById(R.id.article_page_thumbnail_bottom);
        }
    }
}
