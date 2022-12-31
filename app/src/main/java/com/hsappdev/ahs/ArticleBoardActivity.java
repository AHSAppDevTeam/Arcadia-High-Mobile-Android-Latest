package com.hsappdev.ahs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsappdev.ahs.db.ArticleRepository;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.ui.BackNavigationActivity;
import com.hsappdev.ahs.ui.reusable.recyclerview.AbstractDataRecyclerView;
import com.hsappdev.ahs.ui.reusable.recyclerview.DataTypeViewAdapter;
import com.hsappdev.ahs.util.ImageUtil;
import com.hsappdev.ahs.util.ScreenUtil;
import com.hsappdev.ahs.viewModels.ArticleBoardViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleBoardActivity extends BackNavigationActivity {

    public static final int MAX_BLURB_LENGTH = 200;

    private static final String TAG = "ArticleBoardActivity";

    public static final String ARTICLE_BOARD_TITLE_DATA_KEY = "ARTICLE_TITLE_ID";
    private String title;

    public static final String ARTICLE_IDS_DATA_KEY = "ARTICLE_IDS";
    private ArrayList<String> articleIds;

    private ArticleBoardViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_board);

        extractDataFromIntent();

        viewModel = new ViewModelProvider(this).get(ArticleBoardViewModel.class);
        viewModel.startLoadingArticleData(articleIds, getResources());

        setupUI();
    }

    private void setupUI() {
        // title
        TextView titleText = findViewById(R.id.board_articles_title_bold);
        titleText.setText(title);

        // recycler view
        // Recycler view stuff
        AbstractDataRecyclerView<ArticleDataType> dataRecyclerViewAdapter =
                new AbstractDataRecyclerView<>(R.layout.bulletin_article_section,
                        new DataTypeViewAdapter<ArticleDataType>() {
                            @Override
                            public void setDataToView(ArticleDataType data, View itemView) {
                                TextView titleTextView = itemView.findViewById(R.id.board_title_text);
                                TextView boardTextView = itemView.findViewById(R.id.board_description_text);
                                ImageView imageView = itemView.findViewById(R.id.board_image);

                                Log.wtf(TAG, "wtf");

                                titleTextView.setText(data.getTitle());
                                ScreenUtil.setPlainHTMLStringToTextView(data.getBody().substring(0, Math.min(data.getBody().length(), MAX_BLURB_LENGTH)), boardTextView);

                                // performs checks for images
                                if (data.getImageURLs().length > 0) {
                                    ImageUtil.setImageToView(data.getImageURLs()[0], imageView);
                                }
                            }

                            @Override
                            public void handleOnClick(ArticleDataType data, View view) {
                                Intent articleIntent = new Intent(view.getContext(), ArticleActivity.class);
                                articleIntent.putExtra(ArticleDataType.ARTICLE_EXTRA_ID, data);
                                view.getContext().startActivity(articleIntent);
                            }
                        }
                );
        RecyclerView recyclerView = findViewById(R.id.articleBoardRecyclerView);

        recyclerView.setAdapter(dataRecyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        viewModel.getArticles().observe(this, new Observer<List<ArticleDataType>>() {
            @Override
            public void onChanged(List<ArticleDataType> boardsList) {
                Log.d(TAG, String.format("List Size: %d", boardsList.size()));
                ArticleRepository articleRepository = new ArticleRepository(getApplication());
                articleRepository.addOrUpdate(boardsList.toArray(new ArticleDataType[0]));

                dataRecyclerViewAdapter.setDataList(boardsList);
            }
        });

    }

    private void extractDataFromIntent() {
        if(getIntent() != null) {
            title = getIntent().getStringExtra(ARTICLE_BOARD_TITLE_DATA_KEY);
            articleIds = getIntent().getStringArrayListExtra(ARTICLE_IDS_DATA_KEY);

//            // TODO: override here
//            articleIds = new ArrayList<>();
//            articleIds.add("cyan-pearl-magenta");
//            articleIds.add("lemon-snow-quincy");
//            articleIds.add("magenta-toolbox-jonquil");
//            articleIds.add("mango-fulvous-fawn");

        } else {
            // something is wrong, go back home
            finish();
        }
    }
}