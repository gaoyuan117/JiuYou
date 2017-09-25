package com.jiuyou.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.model.MyAdressBean;
import com.jiuyou.ui.activity.AdressActivity;
import com.jiuyou.util.PopUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyuan on 2017/8/10.
 */

public class AdressAdapter extends BaseAdapter {
    private AdressActivity activity;
    private List<MyAdressBean> mList;

    public AdressAdapter(AdressActivity activity, List<MyAdressBean> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(activity, R.layout.item_adress, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final MyAdressBean bean = mList.get(i);
        String is_default = bean.getIs_default();
        String adress = bean.getProvince() + "省" + bean.getCity() + bean.getArea() + bean.getAddress();
        if (is_default.equals("1")) {//默认
            adress = "【默认】" + adress;
            viewHolder.cbAdress.setChecked(true);
            viewHolder.cbAdress.setEnabled(false);
        } else {
            viewHolder.cbAdress.setChecked(false);
            viewHolder.cbAdress.setEnabled(true);
        }

        viewHolder.tvAdressInfo.setText(adress);
        viewHolder.tvAdressP.setText(bean.getRealname());
        viewHolder.tvAdressPhone.setText(bean.getMobile());

        viewHolder.cbAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setDefaultAdress(mList.get(i).getId());
            }
        });

        viewHolder.tvAdressDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUtil.showDialog(activity, "温馨提醒", "是否删除该地址？", "取消", "确认", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.delAdress(mList.get(i).getId());
                        PopUtil.dismissPop();
                    }
                });
            }
        });

        viewHolder.tvAdressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.editAdress(mList.get(i));
            }
        });

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_adress_p)
        TextView tvAdressP;
        @Bind(R.id.tv_adress_phone)
        TextView tvAdressPhone;
        @Bind(R.id.tv_adress_info)
        TextView tvAdressInfo;
        @Bind(R.id.cb_adress)
        CheckBox cbAdress;
        @Bind(R.id.tv_adress_edit)
        TextView tvAdressEdit;
        @Bind(R.id.tv_adress_del)
        TextView tvAdressDel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
