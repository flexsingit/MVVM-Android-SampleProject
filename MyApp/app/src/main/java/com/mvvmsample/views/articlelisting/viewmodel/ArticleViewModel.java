package com.mvvmsample.views.articlelisting.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.mvvmsample.R;
import com.mvvmsample.network.ApiClient;
import com.mvvmsample.network.CheckConnection;
import com.mvvmsample.views.articlelisting.model.ArticleBean;
import com.mvvmsample.views.articlelisting.services.ArticleApiServices;
import com.mvvmsample.views.articlelisting.view.ui.callback.IArticleListener;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ArticleViewModel extends AndroidViewModel {
    private IArticleListener mArticleListener;
    private MutableLiveData<ArticleBean> articleMutableLiveData;
    private Disposable disposable;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * The purpose of this method to implement listner
     *
     * @param mArticleListener
     */
    public void setArticleListener(IArticleListener mArticleListener) {
        this.mArticleListener = mArticleListener;
    }

    //we will call this method to get the data
    public LiveData<ArticleBean> getArticle(boolean isShowing) {
        if (articleMutableLiveData == null) {
            articleMutableLiveData = new MutableLiveData<ArticleBean>();
            loadArticles(isShowing);
        }
        return articleMutableLiveData;
    }

    //This method is using Retrofit to get the JSON data from URL
    public void loadArticles(boolean isShowing) {
        if (CheckConnection.isConnected(getApplication().getApplicationContext())) {
            callArticleApi(isShowing);
        } else {
            mArticleListener.onFailure(getApplication().getApplicationContext().getString(R.string.check_internet_connection));
            mArticleListener.showProgressDialog(false);
        }
    }

    //This method is using Request the api using retrofit
    private void callArticleApi(boolean isShowing) {
        Retrofit retrofit = ApiClient.getClient();

        if (retrofit == null) return;
        if (isShowing) {
            mArticleListener.showProgressDialog(true);
        }
        Observable<ArticleBean> articleObserver = retrofit.create(ArticleApiServices.class).getNewsList("UoxG2hYgUPioGcdkqI8OEX1RGmrkxAVT");
        disposable = articleObserver.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::handleResponse, this::handleError);
        //subscribe.dispose();
    }

    /**
     * The purpose of this method to handle response
     *
     * @param articleBean
     */
    private void handleResponse(ArticleBean articleBean) {
        if (articleBean != null && articleBean.getStatus().equals("OK")) {
            mArticleListener.showProgressDialog(false);
            mArticleListener.onSuccess(articleBean);
            articleMutableLiveData.setValue(articleBean);
        } else {
            mArticleListener.showProgressDialog(false);
            mArticleListener.showNoDataFound();
        }
    }

    /**
     * The purpose of  this method to handle error
     *
     * @param throwable
     */
    private void handleError(Throwable throwable) {
        mArticleListener.showProgressDialog(false);
        mArticleListener.onFailure(throwable.getMessage());
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onCleared();
    }
}
