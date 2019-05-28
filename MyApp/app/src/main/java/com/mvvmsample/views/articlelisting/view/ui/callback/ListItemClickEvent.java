package com.mvvmsample.views.articlelisting.view.ui.callback;

import android.content.Intent;
import android.view.View;

import com.mvvmsample.utils.ToastUtil;
import com.mvvmsample.views.articledetails.view.activity.ArticleDetailsActivity;
import com.mvvmsample.views.articlelisting.model.ResultsBean;


public class ListItemClickEvent {

    /**
     * The purpose of this method to perform click event
     * @param v
     * @param resultsBean
     */
    public void onClickItem(View v, ResultsBean resultsBean) {
        //ToastUtil.showShortToast(v.getContext(), "Yes");
        Intent intent = new Intent(v.getContext(), ArticleDetailsActivity.class);
        intent.putExtra("data",resultsBean);
        v.getContext().startActivity(intent);
    }
}
