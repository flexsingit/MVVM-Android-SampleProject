package com.mvvmsample.views.articlelisting.view.ui.callback;


import com.mvvmsample.views.articlelisting.model.ArticleBean;

public interface IArticleListener {
     void onSuccess(ArticleBean article);
     void onFailure(String errorMessage);
     void showProgressDialog(boolean isShowing);
     void showNoDataFound();
}
