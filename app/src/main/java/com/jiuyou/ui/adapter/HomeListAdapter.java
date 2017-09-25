package com.jiuyou.ui.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiuyou.R;
import com.jiuyou.customctrls.XCRoundRectImageView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.CartResponse;
import com.jiuyou.network.response.JZBResponse.HomeDataList;
import com.jiuyou.ui.Utils.CartUtils;
import com.jiuyou.ui.activity.GoodsDetailActivity;
import com.jiuyou.ui.activity.LoginActivity;
import com.jiuyou.ui.activity.MainActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;
import com.sheyuan.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeListAdapter extends BaseAdapter {
    private MainActivity activity;
    private List<HomeDataList> mDatas;
    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).showImageForEmptyUri(R.mipmap.icon_nopic).showImageOnFail(R.mipmap.icon_nopic).showImageOnLoading(R.mipmap.icon_nopic).bitmapConfig(Bitmap.Config.RGB_565).build();

    public HomeListAdapter(MainActivity activity, List<HomeDataList> mDatas) {
        this.activity = activity;
        this.mDatas = mDatas;
    }


    public void setmDatas(List<HomeDataList> list) {
        this.mDatas.clear();
        this.mDatas = list;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<HomeDataList> list) {
        this.mDatas.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }


    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.home_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mDatas.get(position) != null) {
            String quantity = mDatas.get(position).getQuantity();

//            if (quantity != null && Integer.parseInt(quantity) > 0) {
                holder.iv_shouwan.setVisibility(View.INVISIBLE);
//            } else {
//                holder.iv_shouwan.setVisibility(View.VISIBLE);
//                holder.iv_jiahao1.setVisibility(View.INVISIBLE);
//            }
            holder.tv_goodsprice1Over.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_goodsprice1Over.setText("¥" + mDatas.get(position).getOld_price());
            holder.tv_goodsname1.setText(mDatas.get(position).getTitle());
            holder.tv_goodsprice1.setText("¥" + mDatas.get(position).getPrice());

            Glide.with(activity).load(AppConfig.ENDPOINTPIC + mDatas.get(position).getMasterImg()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.logo).into(holder.tv_goods1);
            holder.ll_goods1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, GoodsDetailActivity.class);
                    intent.putExtra("goodid", mDatas.get(position).getId());
                    intent.putExtra("quantity", mDatas.get(position).getQuantity());
                    activity.startActivity(intent);

                }
            });
            holder.iv_jiahao1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (PrefereUtils.getInstance().isLogin()) {
                        CartUtils.getchangeCart(PrefereUtils.getInstance().getToken(), "add", mDatas.get(position).getId(), "1", new CartUtils.getChangeCartListener() {
                            @Override
                            public void load(boolean status, CartResponse info, String message) {
                                if (status) {
                                    AppConfig.currentTAB = MainActivity.TAB_ACCOUNT;
                                    activity.setTabSelection(MainActivity.TAB_ACCOUNT);
                                } else {
                                    ToastUtil.show(message);
                                }

                            }
                        });
                    } else {
                        activity.toNext(LoginActivity.class);
                    }


                }
            });
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ll_goods1)
        LinearLayout ll_goods1;
        @Bind(R.id.tv_goods1)
        ImageView tv_goods1;
        @Bind(R.id.tv_goodsname1)
        TextView tv_goodsname1;
        @Bind(R.id.tv_goodsprice1)
        TextView tv_goodsprice1;
        @Bind(R.id.tv_goodsprice1_over)
        TextView tv_goodsprice1Over;
        @Bind(R.id.iv_jiahao1)
        ImageView iv_jiahao1;
        @Bind(R.id.iv_shouwan)
        XCRoundRectImageView iv_shouwan;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}




