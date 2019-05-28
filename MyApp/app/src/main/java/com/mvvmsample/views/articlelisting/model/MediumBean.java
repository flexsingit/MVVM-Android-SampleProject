package com.mvvmsample.views.articlelisting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MediumBean implements Parcelable {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("approved_for_syndication")
    @Expose
    private Integer approvedForSyndication;
    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadatumBean> mediaMetadata = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getApprovedForSyndication() {
        return approvedForSyndication;
    }

    public void setApprovedForSyndication(Integer approvedForSyndication) {
        this.approvedForSyndication = approvedForSyndication;
    }

    public List<MediaMetadatumBean> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(List<MediaMetadatumBean> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.subtype);
        dest.writeString(this.caption);
        dest.writeString(this.copyright);
        dest.writeValue(this.approvedForSyndication);
        dest.writeList(this.mediaMetadata);
    }

    public MediumBean() {
    }

    protected MediumBean(Parcel in) {
        this.type = in.readString();
        this.subtype = in.readString();
        this.caption = in.readString();
        this.copyright = in.readString();
        this.approvedForSyndication = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mediaMetadata = new ArrayList<>();
        in.readList(this.mediaMetadata, MediaMetadatumBean.class.getClassLoader());
    }

    public static final Creator<MediumBean> CREATOR = new Creator<MediumBean>() {
        @Override
        public MediumBean createFromParcel(Parcel source) {
            return new MediumBean(source);
        }

        @Override
        public MediumBean[] newArray(int size) {
            return new MediumBean[size];
        }
    };
}
