package com.boya.kandiannews.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boya.kandiannews.App;
import com.boya.kandiannews.R;
import com.boya.kandiannews.adpter.viewholder.NewsViewHolder;
import com.boya.kandiannews.adpter.viewholder.NewsViewHolder2;
import com.boya.kandiannews.adpter.viewholder.NewsViewHolder3;
import com.boya.kandiannews.mvp.bean.NewsList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private View inflate;
    private List<NewsList.DataBean> newsLists;
    private OnItemClickListener onItemClickListener;

    public NewsRecyclerViewAdapter() {
        newsLists = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return newsLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        String img = newsLists.get(position).getImg();
        if (img != null && !img.equals("")) {
            List<String> stringList = Arrays.asList(img.split(","));
            switch (stringList.size()) {
                case 0:
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                default:
                    return 1;


            }
        }
        return 1;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        switch (viewType) {
            case 1:
                inflate = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_news, parent, false);
                return new NewsViewHolder(inflate);
            case 2:
                inflate = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_news2, parent, false);
                return new NewsViewHolder2(inflate);
            case 3:
                inflate = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_news3, parent, false);
                return new NewsViewHolder3(inflate);
        }
        inflate = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NewsList.DataBean rowsBean = newsLists.get(position);
        // newsViewHolder.mIvNewsImage
        String img = rowsBean.getImg();
        List<String> stringList = new ArrayList<>();
        if (img != null && !img.equals("")) {
            stringList = Arrays.asList(img.split(","));
        }
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            newsViewHolder.mTvNewsTitle.setText(rowsBean.getValue());//标题
            newsViewHolder.mTvCount.setText(rowsBean.getCount() + "阅读");
            String imgUrl = stringList.size() > 0 ? stringList.get(0) : "";
            App.loadingImageAd(imgUrl, newsViewHolder.mIvNewsImage, true);//图片

        } else if (holder instanceof NewsViewHolder2) {
            NewsViewHolder2 newsViewHolder2 = (NewsViewHolder2) holder;
            newsViewHolder2.mTvNewsTitle.setText(rowsBean.getValue());//标题
            newsViewHolder2.mTvCount.setText(rowsBean.getCount() + "阅读");
            String imgUrl = stringList.size() > 0 ? stringList.get(0) : "";
            String imgUrl2 = stringList.size() > 1 ? stringList.get(1) : "";
            App.loadingImageAd(imgUrl, newsViewHolder2.mIvNewsImage, true);//图片
            App.loadingImageAd(imgUrl2, newsViewHolder2.mIvNewsImage2, true);//图片
        } else if (holder instanceof NewsViewHolder3) {
            NewsViewHolder3 newsViewHolder3 = (NewsViewHolder3) holder;
            newsViewHolder3.mTvNewsTitle.setText(rowsBean.getValue());//标题
            newsViewHolder3.mTvCount.setText(rowsBean.getCount() + "阅读");
            String imgUrl = stringList.size() > 0 ? stringList.get(0) : "";
            String imgUrl2 = stringList.size() > 1 ? stringList.get(1) : "";
            String imgUrl3 = stringList.size() > 2 ? stringList.get(2) : "";
            App.loadingImageAd(imgUrl, newsViewHolder3.mIvNewsImage, true);//图片
            App.loadingImageAd(imgUrl2, newsViewHolder3.mIvNewsImage2, true);//图片
            App.loadingImageAd(imgUrl3, newsViewHolder3.mIvNewsImage3, true);//图片
        }
        for (int i = 0; i < stringList.size(); i++) {
            Log.d("NewsRecyclerViewAdapter", stringList.get(i));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItem(rowsBean);
                }
            }
        });

    }

    //添加数据
    public void addData(List<NewsList.DataBean> newsLists2) {
        newsLists.addAll(newsLists.size(), newsLists2);
        notifyDataSetChanged();
    }


    //清空数据
    public void deleteData() {
        newsLists.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItem(NewsList.DataBean rowsBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
