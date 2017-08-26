package com.jiuyou.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sheyuan.universalimageloader.core.DisplayImageOptions;
import com.sheyuan.universalimageloader.core.assist.ImageScaleType;
import com.jiuyou.R;
import com.jiuyou.core.ImageLoaderProxy;
import com.jiuyou.network.response.OtherResponse.HotGoodsList;

import java.util.List;

public class HomeLandScapeAdapter extends RecyclerView.Adapter<HomeLandScapeAdapter.ViewHolder> {

  
    private Context mContext;
    private List<HotGoodsList.HotGood> mDatas;

    private DisplayImageOptions options;

    private void configOptions() {
        options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.mipmap.icon_nopic).showImageOnFail
                (R.mipmap.icon_nopic).showImageOnLoading(R.mipmap.icon_nopic).cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType
                .IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public HomeLandScapeAdapter(Context mContext, List<HotGoodsList.HotGood> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        configOptions();
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_pictures, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        HotGoodsList.HotGood hotGood = mDatas.get(position);
        ImageLoaderProxy.getInstance().displayImage(hotGood.getPicUrl(), holder.mPic, options);
        holder.mName.setText(hotGood.getTitle());
        holder.mPrice.setText("¥"+hotGood.getPrice());
        if(mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    public void setData(List<HotGoodsList.HotGood> commlist) {
        mDatas.clear();
        mDatas.addAll(commlist);
        notifyDataSetChanged();
    }
   

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mPic;
        TextView mName;
        TextView mPrice;
        public ViewHolder(View view) {
            super(view);
            mPic= (ImageView) view.findViewById(R.id.iv_picture);
            mName= (TextView) view.findViewById(R.id.tv_goodsname);
            mPrice= (TextView) view.findViewById(R.id.tv_goodsprice);
            
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
