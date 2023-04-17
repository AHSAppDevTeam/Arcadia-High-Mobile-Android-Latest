package com.hsappdev.ahs;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsappdev.ahs.db.ArticleRepository;
import com.hsappdev.ahs.newDataTypes.ArticleDataType;
import com.hsappdev.ahs.newDataTypes.BoardDataType;
import com.hsappdev.ahs.ui.reusable.BackNavigationActivity;
import com.hsappdev.ahs.ui.reusable.recyclerview.AbstractDataRecyclerView;
import com.hsappdev.ahs.ui.reusable.recyclerview.DataTypeViewAdapter;
import com.hsappdev.ahs.util.ImageUtil;
import com.hsappdev.ahs.util.ScreenUtil;
import com.hsappdev.ahs.viewModels.ArticleBoardViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArticleBoardActivity extends BackNavigationActivity {

    public static final int MAX_BLURB_LENGTH = 200;

    private static final String TAG = "ArticleBoardActivity";

    public static final String ARTICLE_BOARD_DATA_KEY = "ARTICLE_TITLE_ID";
    private BoardDataType board;

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
        titleText.setText(board.getTitle());

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

        ArticleRepository ar = new ArticleRepository(this.getApplication());

        viewModel.getArticles().observe(this, new Observer<List<ArticleDataType>>() {
            @Override
            public void onChanged(List<ArticleDataType> boardsList) {
                Log.d(TAG, String.format("List Size: %d", boardsList.size()));
                for (ArticleDataType a : boardsList) {
                    a.setCategoryDisplayColor(board.getColor());
                }
                ArticleRepository articleRepository = new ArticleRepository(getApplication());
                articleRepository.addOrUpdate(boardsList.toArray(new ArticleDataType[0]));
//                articleRepository.getAllArticles();
                // articleRepository.deleteAll();

//                articleRepository.getArticle("asdfadsf").observe(ArticleBoardActivity.this, new Observer<ArticleDataType>() {
//                    @Override
//                    public void onChanged(ArticleDataType articleDataType) {
//                        articleDataType.getArticleID();
//
//                    }
//                });

                dataRecyclerViewAdapter.setDataList(boardsList);
            }
        });

    }

    private void extractDataFromIntent() {
        if(getIntent() != null) {
            board = getIntent().getParcelableExtra(ARTICLE_BOARD_DATA_KEY);
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