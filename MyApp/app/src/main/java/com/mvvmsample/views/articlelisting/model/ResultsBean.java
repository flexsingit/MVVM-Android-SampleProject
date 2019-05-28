package com.mvvmsample.views.articlelisting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResultsBean implements Parcelable {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("adx_keywords")
    @Expose
    private String adxKeywords;
    /*@SerializedName("column")
    @Expose
    private Object column;*/
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("asset_id")
    @Expose
    private String assetId;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("des_facet")
    @Expose
    private List<String> desFacet = null;
//    @SerializedName("org_facet")
//    @Expose
//    private List<String> orgFacet = null;
//    @SerializedName("per_facet")
//    @Expose
//    private List<String> perFacet = null;
//    @SerializedName("geo_facet")
//    @Expose
//    private String geoFacet;
    @SerializedName("media")
    @Expose
    private List<MediumBean> media = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public List<String> getDesFacet() {
        return desFacet;
    }

    public void setDesFacet(List<String> desFacet) {
        this.desFacet = desFacet;
    }

//    public List<String> getOrgFacet() {
//        return orgFacet;
//    }
//
//    public void setOrgFacet(List<String> orgFacet) {
//        this.orgFacet = orgFacet;
//    }

//    public List<String> getPerFacet() {
//        return perFacet;
//    }
//
//    public void setPerFacet(List<String> perFacet) {
//        this.perFacet = perFacet;
//    }

//    public String getGeoFacet() {
//        return geoFacet;
//    }
//
//    public void setGeoFacet(String geoFacet) {
//        this.geoFacet = geoFacet;
//    }

    public List<MediumBean> getMedia() {
        return media;
    }

    public void setMedia(List<MediumBean> media) {
        this.media = media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.adxKeywords);
        dest.writeString(this.section);
        dest.writeString(this.byline);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this._abstract);
        dest.writeString(this.publishedDate);
        dest.writeString(this.source);
        dest.writeString(this.id);
        dest.writeString(this.assetId);
        dest.writeValue(this.views);
        dest.writeStringList(this.desFacet);
        dest.writeList(this.media);
    }

    public ResultsBean() {
    }

    protected ResultsBean(Parcel in) {
        this.url = in.readString();
        this.adxKeywords = in.readString();
        this.section = in.readString();
        this.byline = in.readString();
        this.type = in.readString();
        this.title = in.readString();
        this._abstract = in.readString();
        this.publishedDate = in.readString();
        this.source = in.readString();
        this.id = in.readString();
        this.assetId = in.readString();
        this.views = (Integer) in.readValue(Integer.class.getClassLoader());
        this.desFacet = in.createStringArrayList();
        this.media = new ArrayList<MediumBean>();
        in.readList(this.media, MediumBean.class.getClassLoader());
    }

    public static final Creator<ResultsBean> CREATOR = new Creator<ResultsBean>() {
        @Override
        public ResultsBean createFromParcel(Parcel source) {
            return new ResultsBean(source);
        }

        @Override
        public ResultsBean[] newArray(int size) {
            return new ResultsBean[size];
        }
    };
}