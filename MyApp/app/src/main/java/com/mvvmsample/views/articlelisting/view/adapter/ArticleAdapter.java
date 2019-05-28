package com.mvvmsample.views.articlelisting.view.adapter;

import android.content.Context;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mvvmsample.R;
import com.mvvmsample.databinding.RvListItemBinding;
import com.mvvmsample.views.articlelisting.model.ResultsBean;
import com.mvvmsample.views.articlelisting.view.ui.callback.ListItemClickEvent;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<ResultsBean> mResultList;
    private List<ResultsBean> mResultTempList;
    private Context mContext;
    private SearchFilter filter;

    public ArticleAdapter(Context mContext, List<ResultsBean> list) {
        this.mContext = mContext;
        this.mResultList = list;
        this.mResultTempList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_list_item, parent, false);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            ResultsBean model = mResultList.get(position);
            ((ArticleViewHolder) holder).binding.setResultPojo(model);
            ((ArticleViewHolder) holder).binding.executePendingBindings();
            ((ArticleViewHolder) holder).binding.setHandlers(new ListItemClickEvent());
            Glide.with(((ArticleViewHolder) holder).binding.profileImage.getContext())
                    .load(model.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((ArticleViewHolder) holder).binding.profileImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
            filter=new SearchFilter();
        return filter;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
         RvListItemBinding binding;

        ArticleViewHolder(RvListItemBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }

    class SearchFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                mResultList = mResultTempList;
            } else {
                List<ResultsBean> filteredList = new ArrayList<>();
                for (ResultsBean row : mResultTempList) {

                    if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                            || row.getPublishedDate().contains(charString.toLowerCase())) {
                        filteredList.add(row);
                    }
                }

                mResultList = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = mResultList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mResultList = (ArrayList<ResultsBean>) results.values;
            notifyDataSetChanged();
        }
    }
}
