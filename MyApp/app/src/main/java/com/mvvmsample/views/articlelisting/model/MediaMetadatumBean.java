package com.mvvmsample.views.articlelisting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaMetadatumBean implements Parcelable {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.format);
        dest.writeValue(this.height);
        dest.writeValue(this.width);
    }

    public MediaMetadatumBean() {
    }

    protected MediaMetadatumBean(Parcel in) {
        this.url = in.readString();
        this.format = in.readString();
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<MediaMetadatumBean> CREATOR = new Creator<MediaMetadatumBean>() {
        @Override
        public MediaMetadatumBean createFromParcel(Parcel source) {
            return new MediaMetadatumBean(source);
        }

        @Override
        public MediaMetadatumBean[] newArray(int size) {
            return new MediaMetadatumBean[size];
        }
    };
}
