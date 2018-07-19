package com.boya.kandiannews.adpter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boya.kandiannews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsViewHolder3 extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_NewsTitle)
    public TextView mTvNewsTitle;
    @BindView(R.id.iv_NewsImage)
    public ImageView mIvNewsImage;
    @BindView(R.id.iv_NewsImage2)
    public ImageView mIvNewsImage2;
    @BindView(R.id.iv_NewsImage3)
    public ImageView mIvNewsImage3;
    @BindView(R.id.tv_count)
    public TextView mTvCount;
    public NewsViewHolder3(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
