package com.mvvmsample.views.articlelisting.services;


import com.mvvmsample.views.articlelisting.model.ArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleApiServices {
    @GET("7.json")
    Observable<ArticleBean> getNewsList(@Query("api-key") String apiKey);
}
