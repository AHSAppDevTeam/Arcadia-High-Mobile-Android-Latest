package com.hsappdev.ahs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.hsappdev.ahs.util.ImageUtil;

public class ImageViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        String imageURL = getIntent().getStringExtra(ArticleImageFragment.ARG_OBJECT);

        ImageView image = findViewById(R.id.imageViewerImage);
        ImageUtil.setImageToView(imageURL, image);


    }
}