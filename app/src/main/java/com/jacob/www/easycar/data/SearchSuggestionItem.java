package com.jacob.www.easycar.data;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.amap.api.services.core.LatLonPoint;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by 张兴锐 on 2017/11/16.
 */

@SuppressLint("ParcelCreator")
public class SearchSuggestionItem implements SearchSuggestion {

    String suggestoin;
    LatLonPoint latLonPoint;

    public SearchSuggestionItem(String suggestoin, LatLonPoint latLonPoint) {
        this.suggestoin = suggestoin;
        this.latLonPoint = latLonPoint;
    }

    public String getSuggestoin() {
        return suggestoin;
    }

    public LatLonPoint getLatLonPoint() {
        return latLonPoint;
    }

    @Override
    public String getBody() {
        return suggestoin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.suggestoin);
    }

    protected SearchSuggestionItem(Parcel in) {
        this.suggestoin = in.readString();
    }

    public static final Creator<SearchSuggestionItem> CREATOR = new Creator<SearchSuggestionItem>() {
        @Override
        public SearchSuggestionItem createFromParcel(Parcel source) {
            return new SearchSuggestionItem(source);
        }

        @Override
        public SearchSuggestionItem[] newArray(int size) {
            return new SearchSuggestionItem[size];
        }
    };
}
