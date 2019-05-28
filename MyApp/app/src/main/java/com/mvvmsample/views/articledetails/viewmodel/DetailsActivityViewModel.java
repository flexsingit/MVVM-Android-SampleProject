package com.mvvmsample.views.articledetails.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.mvvmsample.views.articledetails.view.callback.DetailsListener;


public class DetailsActivityViewModel extends AndroidViewModel {

    private DetailsListener mInyArticleListener;

    public DetailsActivityViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * The purpose of this method to implement the listener
     * @param mInyArticleListener
     */
    public void setDetailsListener(DetailsListener mInyArticleListener) {
        this.mInyArticleListener = mInyArticleListener;
    }
}
