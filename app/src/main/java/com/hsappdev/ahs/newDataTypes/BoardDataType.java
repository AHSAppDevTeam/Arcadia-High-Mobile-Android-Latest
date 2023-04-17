package com.hsappdev.ahs.newDataTypes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.ColorInt;

import com.hsappdev.ahs.newCache.DataType;
import com.hsappdev.ahs.util.ScreenUtil;

import java.util.ArrayList;

public class BoardDataType extends DataType implements Parcelable {
    private ArrayList<String> articleIds;

    private long editTimestamp;

    private int sort;

    private String title;

    @ColorInt
    private int color;

    protected BoardDataType(Parcel in) {
        title = in.readString();
        sort = in.readInt();
        color = in.readInt();
        in.readParcelable(ClassLoader.getSystemClassLoader());
    }

    public BoardDataType(String dataID, ArrayList<String> articleIds, long editTimestamp, int sort, String title) {
        super(dataID);
        this.setArticleIds(articleIds);
        this.setEditTimestamp(editTimestamp);
        this.setSort(sort);
        this.setTitle(ScreenUtil.capitalizeFully(title));

    }

//    @Override
//    public void setDataToView(View view) {
//        // extract the view elements
//        TextView titleTextView = view.findViewById(R.id.board_title_text);
//
//        titleTextView.setText(title);
//    }
//
//    @Override
//    public void handleOnClick(View view) {
//        // Toast.makeText(view.getContext(), title, Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(view.getContext(), ArticleBoardActivity.class);
//        intent.putExtra(ArticleBoardActivity.ARTICLE_BOARD_TITLE_DATA_KEY, title);
//        intent.putStringArrayListExtra(ArticleBoardActivity.ARTICLE_IDS_DATA_KEY, articleIds);
//
//        view.getContext().startActivity(intent);
//
//
//    }

    public static final Creator<BoardDataType> CREATOR = new Creator<BoardDataType>() {
        @Override
        public BoardDataType createFromParcel(Parcel in) {
            return new BoardDataType(in);
        }

        @Override
        public BoardDataType[] newArray(int size) {
            return new BoardDataType[size];
        }
    };

    @Override
    public int compareTo(DataType o) {
        return Long.compare(this.getSort(), ((BoardDataType) o).getSort());
    }

    public ArrayList<String> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(ArrayList<String> articleIds) {
        this.articleIds = articleIds;
    }

    public long getEditTimestamp() {
        return editTimestamp;
    }

    public void setEditTimestamp(long editTimestamp) {
        this.editTimestamp = editTimestamp;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public @ColorInt int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(sort);
        parcel.writeInt(color);

    }

    @Override
    public String toString() {
        return "BoardDataType{" +
                "articleIds=" + articleIds +
                ", editTimestamp=" + editTimestamp +
                ", sort=" + sort +
                ", title='" + title + '\'' +
                ", color=" + color +
                ", dataId='" + dataId + '\'' +
                ", dataHash='" + dataHash + '\'' +
                ", isLoading=" + isLoading +
                '}';
    }
}
