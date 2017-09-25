package com.jiuyou.ui.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuyou.R;
import com.jiuyou.customctrls.RoundImageView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.Cart;
import com.jiuyou.network.response.JZBResponse.CartResponse;
import com.jiuyou.ui.Utils.CartUtils;
import com.jiuyou.ui.activity.MainActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {

    private MainActivity context;
    private List<Cart> loans;
    private LayoutInflater inflater;
    private static HashMap<Integer, Boolean> isSelected;
    private static HashMap<Integer, Integer> numbers;
    private Handler handler;
//    int num;// 商品数量

    static class ViewHolder { // 自定义控件集合
        public CheckBox ck_select;
        public ImageView pic_goods;
        public TextView color_goods;
        public TextView integral_goods;
        public LinearLayout layout;
        public TextView number;
        public Button minus;
        public Button plus;
    }

    /**
     * 实例化Adapter
     *
     * @param context
     * @param data
     */
    public ShoppingCartAdapter(MainActivity context, Handler handler, List<Cart> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.loans = data;
        this.handler = handler;
        isSelected = new HashMap<Integer, Boolean>();
        numbers = new HashMap<Integer, Integer>();
        initDate();
    }

    public void addDatas(List<Cart> list) {
        this.loans.addAll(list);
        initDate();
        this.notifyDataSetChanged();
    }

    private void initDate() {
        for (int i = 0; i < loans.size(); i++) {
            getIsSelected().put(i, false);
            getNumbers().put(i, Integer.valueOf(loans.get(i).getQuantity()));
        }
    }

    public void notifyDateSet(List<Cart> list) {
        this.loans = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return loans.size();
    }

    @Override
    public Object getItem(int position) {
        return loans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 自定义视图
        ViewHolder itemView = null;
        if (convertView == null) {
            // 获取list_item布局文件的视图
            itemView = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_shopping_cart_item, null);
            // 获取控件对象
            itemView.ck_select = (CheckBox) convertView
                    .findViewById(R.id.ck_select);
            itemView.pic_goods = (ImageView) convertView
                    .findViewById(R.id.pic_goods);
//            itemView.pic_goods.setType(RoundImageView.TYPE_ROUND);
            itemView.color_goods = (TextView) convertView
                    .findViewById(R.id.color_goods);
            itemView.integral_goods = (TextView) convertView
                    .findViewById(R.id.integral_goods);
            itemView.number = (TextView) convertView.findViewById(R.id.number);
            itemView.minus = (Button) convertView.findViewById(R.id.minus);
            itemView.plus = (Button) convertView.findViewById(R.id.plus);
            convertView.setTag(itemView);
        } else {
            itemView = (ViewHolder) convertView.getTag();
        }

        init(itemView, position);

        itemView.ck_select.setChecked(getIsSelected().get(position));
        itemView.number.setText(getNumbers().get(position).toString());
        if (getNumbers().get(position).toString().equals("1")) {
            itemView.minus.setBackgroundResource(R.drawable.icon_jianhao_bukedianji);
            itemView.minus.setEnabled(false);
        }
        if (getIsSelected().get(position)) {
            itemView.ck_select.setChecked(true);
        } else {
            itemView.ck_select.setChecked(false);
        }

        String a = itemView.number.getText().toString();
        loans.get(position).setQuantity(a);

        Cart test = loans.get(position);
        itemView.color_goods.setText((CharSequence) test.getTitle());
        itemView.integral_goods.setText("¥" + (CharSequence) test.getPrice());
        Glide.with(context).load(AppConfig.ENDPOINTPIC + test.getMasterImg()).placeholder(R.mipmap.logo).into(itemView.pic_goods);
        return convertView;
    }

    private void init(final ViewHolder itemView, final int position) {

        itemView.ck_select
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        isSelected.put(position, true);
                        getIsSelected().put(position, isChecked);
                        itemView.ck_select.setChecked(getIsSelected().get(
                                position));
                        handler.sendMessage(handler.obtainMessage(10,
                                getTotalPrice()));

                        Iterator iterator = isSelected.entrySet().iterator();
                        List<Boolean> array = new ArrayList<Boolean>();
                        while (iterator.hasNext()) {
                            HashMap.Entry entry = (HashMap.Entry) iterator
                                    .next();
                            Integer key = (Integer) entry.getKey();
                            Boolean val = (Boolean) entry.getValue();
                            array.add(val);
                        }
                        handler.sendMessage(handler.obtainMessage(11, array));
                        handler.sendMessage(handler.obtainMessage(12, getTotalSum()));
                    }
                });

        itemView.plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                itemView.minus.setBackgroundResource(R.drawable.icon_jianhao_kedainji);
                itemView.minus.setEnabled(true);
                int num = Integer.valueOf(itemView.number.getText().toString());
                if (++num < 1) // 先加，再判断
                {
                    num--;
                } else {
                    context.getLoadingDataBar().show();
                    CartUtils.getchangeCart(PrefereUtils.getInstance().getToken(), "add", loans.get(position).getProduct_id(), "", new CartUtils.getChangeCartListener() {
                        @Override
                        public void load(boolean status, CartResponse info, String message) {
                            int plusNum = Integer.valueOf(itemView.number.getText().toString());
                            if (status) {
                                itemView.number.setText(String.valueOf(plusNum + 1));
                                loans.get(position).setQuantity((plusNum + 1) + "");
                                numbers.put(position, plusNum + 1);
                                handler.sendMessage(handler.obtainMessage(10,
                                        getTotalPrice()));
                                handler.sendMessage(handler.obtainMessage(12,
                                        getTotalSum()));
                            } else {
                                ToastUtil.show(message);
                            }
                            context.loadingDataBarClose();
                        }
                    });
                }
            }
        });
        itemView.minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(itemView.number.getText().toString());
                if (--num < 1) // 先加，再判断
                {
                    num++;
                    itemView.minus.setBackgroundResource(R.drawable.icon_jianhao_bukedianji);
                    itemView.minus.setEnabled(false);
                } else {
                    if (num == 1) {
                        itemView.minus.setBackgroundResource(R.drawable.icon_jianhao_bukedianji);
                        itemView.minus.setEnabled(false);
                    }
                    context.getLoadingDataBar().show();
                    CartUtils.getchangeCart(PrefereUtils.getInstance().getToken(), "sub", loans.get(position).getProduct_id(), "", new CartUtils.getChangeCartListener() {
                        @Override
                        public void load(boolean status, CartResponse info, String message) {
                            int minusNum = Integer.valueOf(itemView.number.getText().toString());
                            if (status) {
                                itemView.number.setText(String.valueOf(minusNum - 1));
                                loans.get(position).setQuantity((minusNum - 1) + "");
                                numbers.put(position, minusNum - 1);
                                handler.sendMessage(handler.obtainMessage(10,
                                        getTotalPrice()));
                                handler.sendMessage(handler.obtainMessage(12,
                                        getTotalSum()));
                            } else {
                                ToastUtil.show(message);
                            }
                            context.loadingDataBarClose();
                        }
                    });

                }

            }
        });

    }

    /**
     * 计算选中商品的积分
     *
     * @return 返回需要付费的总积分
     */
    private float getTotalPrice() {
        Cart bean = null;
        float totalPrice = 0;
        for (int i = 0; i < loans.size(); i++) {
            bean = loans.get(i);
            if (ShoppingCartAdapter.getIsSelected().get(i)) {
                totalPrice += Integer.valueOf(bean.getQuantity())
                        * Double.valueOf(bean.getPrice());
            }
        }
        return totalPrice;
    }

    /**
     * 计算选中商品的数量
     *
     * @return 返回需要商品的数量
     */
    private int getTotalSum() {
        Cart bean = null;
        int totalNum = 0;
        for (int i = 0; i < loans.size(); i++) {
            bean = loans.get(i);
            if (ShoppingCartAdapter.getIsSelected().get(i)) {
                totalNum += Integer.valueOf(bean.getQuantity());
            }
        }
        return totalNum;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        ShoppingCartAdapter.isSelected = isSelected;
    }

    public static HashMap<Integer, Integer> getNumbers() {
        return numbers;
    }

    public static void setNumbers(HashMap<Integer, Integer> numbers) {
        ShoppingCartAdapter.numbers = numbers;
    }
}
