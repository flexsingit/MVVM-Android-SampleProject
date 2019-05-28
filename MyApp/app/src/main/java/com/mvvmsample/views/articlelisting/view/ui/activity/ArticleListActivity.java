package com.mvvmsample.views.articlelisting.view.ui.activity;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.mvvmsample.R;
import com.mvvmsample.databinding.ActivityMainBinding;
import com.mvvmsample.utils.ToastUtil;
import com.mvvmsample.views.articlelisting.model.ArticleBean;
import com.mvvmsample.views.articlelisting.model.ResultsBean;
import com.mvvmsample.views.articlelisting.view.adapter.ArticleAdapter;
import com.mvvmsample.views.articlelisting.view.ui.callback.IArticleListener;
import com.mvvmsample.views.articlelisting.viewmodel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArticleListActivity extends AppCompatActivity implements IArticleListener {

    private ActivityMainBinding binding;
    private ArticleViewModel nyArticleApiModel;
    private List<ResultsBean> mResultDataList = new ArrayList<>();
    private ArticleAdapter mArticleAdapter;


    private void bindLiveDataObserver() {
        nyArticleApiModel.getArticle(true).observe(this, notificationObserver);
    }

    final Observer<ArticleBean> notificationObserver = notificationModel -> {
        if (notificationModel == null) return;
        bindAdapter(notificationModel);
    };

    private void bindAdapter(ArticleBean notificationModel) {
        mResultDataList.addAll(notificationModel.getResults());
        if (mArticleAdapter == null) {
            mArticleAdapter = new ArticleAdapter(this, mResultDataList);
            binding.rvNyArticles.setAdapter(mArticleAdapter);
        } else {
//            mCurrentDayAdapter.updateAdapter(this,appointmentDataList);
            mArticleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
        nyArticleApiModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        binding.setViewModel(nyArticleApiModel);
        nyArticleApiModel.setArticleListener(this);
        bindLiveDataObserver();
    }

    private void init() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.rvNyArticles.setLayoutManager(mLayoutManager);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(R.string.ny_times_most_popular);
        }
    }

    @Override
    public void onSuccess(ArticleBean article) {

    }

    @Override
    public void onFailure(String errorMessage) {
        ToastUtil.showShortToast(this, errorMessage);
    }

    @Override
    public void showProgressDialog(boolean isShowing) {
        if (isShowing) {
            binding.loadingProgress.setVisibility(View.VISIBLE);
        } else {
            binding.loadingProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoDataFound() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (binding.rvNyArticles.getAdapter() != null)
                    ((ArticleAdapter) binding.rvNyArticles.getAdapter()).getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (binding.rvNyArticles.getAdapter() != null)
                    ((ArticleAdapter) binding.rvNyArticles.getAdapter()).getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
