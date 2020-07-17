package com.cqs.ysa.bean;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.previewlibrary.enitity.IThumbViewInfo;

/**
 * Created by Administrator on 2020/7/17 0017.
 */

public class ThumbViewInfo implements Parcelable,IThumbViewInfo{

    private String url;  //图片地址
    private Rect mBounds; // 记录坐标

    public ThumbViewInfo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Rect getBounds() {
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return null;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, 0);
    }

    protected ThumbViewInfo(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Creator<ThumbViewInfo> CREATOR = new Creator<ThumbViewInfo>() {
        public ThumbViewInfo createFromParcel(Parcel source) {
            return new ThumbViewInfo(source);
        }

        public ThumbViewInfo[] newArray(int size) {
            return new ThumbViewInfo[size];
        }
    };
}