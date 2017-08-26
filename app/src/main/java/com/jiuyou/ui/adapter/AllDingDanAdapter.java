package com.jiuyou.ui.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.global.MyListView;
import com.jiuyou.model.OrderBean;
import com.jiuyou.ui.activity.AllDingDanActivity;
import com.jiuyou.ui.activity.OrderDetailActivity;
import com.jiuyou.ui.activity.saomiao.CaptureActivity;
import com.jiuyou.util.PopUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyuan on 2017/8/10.
 */

public class AllDingDanAdapter extends BaseAdapter {

    private List<OrderBean> list;
    private AllDingDanActivity mContext;


    public AllDingDanAdapter(AllDingDanActivity context, List<OrderBean> list) {
        this.list = list;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_all_dd, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initEvent(holder, position);

        initData(holder, position);


        return convertView;
    }

    /**
     * 初始化数据
     */
    private void initData(ViewHolder holder, final int position) {
        OrderBean bean = list.get(position);
        String status = bean.getStatus();//订单状态
        String psType = bean.getPickup_mode();
        Log.e("gy", "pstype：" + psType);
        holder.tvAllDdNo.setText("单号：" + bean.getOrder_no());
        OrderItemAdapter adapter = new OrderItemAdapter(mContext, bean.getOrder_detail());
        holder.listView.setAdapter(adapter);
        holder.tvAllDdTime.setText("创建时间：" + bean.getCreate_time());
        holder.tvAllDdMoney.setText("¥ " + bean.getTotal_price());

        if (psType.equals("配送")) {
            holder.imgAllDdPs.setVisibility(View.VISIBLE);
            holder.imgAllDdZiqu.setVisibility(View.GONE);
        } else if (psType.equals("自取")) {
            holder.imgAllDdZiqu.setVisibility(View.VISIBLE);
            holder.imgAllDdPs.setVisibility(View.GONE);
        }

        if (status.equals("0")) {//待支付
            holder.tvAllDdStatus.setText("待支付");

            holder.imgAllDdZhifu.setVisibility(View.VISIBLE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.VISIBLE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("1")) {//已支付
            holder.tvAllDdStatus.setText("已支付");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.VISIBLE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("2")) {
            holder.tvAllDdStatus.setText("退款中");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("3")) {
            holder.tvAllDdStatus.setText("退款成功");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("4")) {
            holder.tvAllDdStatus.setText("已取消");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("5")) {
            holder.tvAllDdStatus.setText("等待配送员接单");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.VISIBLE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("6")) {
            holder.tvAllDdStatus.setText("商家已扫码确认");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.VISIBLE);
            holder.llAllAdd.setVisibility(View.VISIBLE);//确认收货.拒绝收货

        } else if (status.equals("7")) {
            holder.tvAllDdStatus.setText("配送中");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("8")) {//确认商品
            holder.tvAllDdStatus.setText("已送达");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.VISIBLE);
            holder.llAllAdd.setVisibility(View.VISIBLE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("9")) {//评价
            holder.tvAllDdStatus.setText("已完成");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.VISIBLE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("10")) {
            holder.tvAllDdStatus.setText("拒绝收货");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.GONE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        } else if (status.equals("11")) {
            holder.tvAllDdStatus.setText("已评价");

            holder.imgAllDdZhifu.setVisibility(View.GONE);//立即支付
            holder.imgAllDdQuxiao.setVisibility(View.GONE);//取消订单
            holder.imgAllDdTuikuan.setVisibility(View.GONE);//退款
            holder.imgAllDdPingjia.setVisibility(View.GONE);//评价
            holder.imgAllDdYiPingJia.setVisibility(View.VISIBLE);//已评价
            holder.imgAllDdYanZheng.setVisibility(View.GONE);
            holder.llAllAdd.setVisibility(View.GONE);//验证真伪.确认收货.拒绝收货

        }
    }

    /**
     * 点击事件
     *
     * @param holder
     * @param position
     */
    private void initEvent(ViewHolder holder, final int position) {

        holder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//订单详情
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("type", list.get(position).getStatus());
                intent.putExtra("psType", list.get(position).getPickup_mode());
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("position", position);
                mContext.startActivityForResult(intent, 110);
            }
        });

        holder.imgAllDdQuxiao.setOnClickListener(new View.OnClickListener() {//取消订单
            @Override
            public void onClick(View view) {//取消订单
                PopUtil.showDialog(mContext, "温馨提醒", "是否取消该商品订单", "取消", "确认", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.cancleOrder(position);
                        PopUtil.dismissPop();
                    }
                });
            }
        });

        holder.imgAllDdZhifu.setOnClickListener(new View.OnClickListener() {//立即支付
            @Override
            public void onClick(View view) {//立即支付
                mContext.toPay(position);
            }
        });

        holder.imgAllDdJujue.setOnClickListener(new View.OnClickListener() {//拒绝收货
            @Override
            public void onClick(View view) {//拒绝收货
                mContext.toRefuse(position);
            }
        });

        holder.imgAllDdPingjia.setOnClickListener(new View.OnClickListener() { //评价
            @Override
            public void onClick(View view) {
                mContext.toEvaluation(position);
            }
        });

        holder.imgAllDdTuikuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//退款
                mContext.toReturnMoney(position);
            }
        });

        holder.imgAllDdQueren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//确认收货
                mContext.confirmReceipt(position);
            }
        });

        holder.imgAllDdYanZheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CaptureActivity.class));
            }
        });
    }


    class ViewHolder {
        @Bind(R.id.tv_all_dd_no)
        TextView tvAllDdNo;
        @Bind(R.id.tv_all_dd_status)
        TextView tvAllDdStatus;
        @Bind(R.id.lv_all_add)
        MyListView listView;
        @Bind(R.id.tv_all_dd_time)
        TextView tvAllDdTime;
        @Bind(R.id.tv_all_add_money)
        TextView tvAllDdMoney;
        @Bind(R.id.img_all_dd_ziqu)
        ImageView imgAllDdZiqu;
        @Bind(R.id.img_all_dd_ps)
        ImageView imgAllDdPs;
        @Bind(R.id.img_all_dd_quxiao)
        ImageView imgAllDdQuxiao;
        @Bind(R.id.img_all_dd_zhifu)
        ImageView imgAllDdZhifu;
        @Bind(R.id.img_all_dd_tuikuan)
        ImageView imgAllDdTuikuan;
        @Bind(R.id.img_all_dd_pingjia)
        ImageView imgAllDdPingjia;
        @Bind(R.id.img_all_dd_jujue)
        ImageView imgAllDdJujue;
        @Bind(R.id.img_all_dd_queren)
        ImageView imgAllDdQueren;
        @Bind(R.id.img_all_dd_yanzheng)
        ImageView imgAllDdYanZheng;
        @Bind(R.id.img_all_dd_yipingjia)
        ImageView imgAllDdYiPingJia;
        @Bind(R.id.ll_all_add)
        LinearLayout llAllAdd;
        @Bind(R.id.ll_add_add_all)
        LinearLayout ll_add_add_all;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
