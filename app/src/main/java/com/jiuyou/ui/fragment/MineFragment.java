package com.jiuyou.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.CircleImageView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.MineItem;
import com.jiuyou.network.response.JZBResponse.UserInfo;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.activity.AboutActivity;
import com.jiuyou.ui.activity.AccountManagerActivity;
import com.jiuyou.ui.activity.AdressActivity;
import com.jiuyou.ui.activity.AllDingDanActivity;
import com.jiuyou.ui.activity.InviteCodeActivity;
import com.jiuyou.ui.activity.MyCommentActivity;
import com.jiuyou.ui.activity.FeedBackActivity;
import com.jiuyou.ui.activity.LoginActivity;
import com.jiuyou.ui.activity.MyTuiJianActivity;
import com.jiuyou.ui.activity.RechargeActivity;
import com.jiuyou.ui.adapter.CommonAdapter;
import com.jiuyou.ui.base.BaseFragment;
import com.jiuyou.ui.base.DefaultHolder;
import com.jiuyou.ui.base.impl.PersonalCenterHolder;
import com.jiuyou.util.PopUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;


public class MineFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private CircleImageView img_head;
    private TextView fujin_login;
    private TextView nick_name;
    private TextView my_yue;
    private TextView money;
    private TextView chongzhi;
    private LinearLayout rl_head;
    private View view;
    private Context mConText;
    private Button btn_exit;
    private LinearLayout llAll;
    CommonAdapter adapter;
    ListView listView;
    List<MineItem> mineItems;
    private TextView tvDaiZhiFu;
    private TextView tvYiZhiFu;
    private TextView tvPeiSong;
    private TextView tvYiWanCheng;
    private TextView tvTuiuan;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView(inflater, container);
        mConText = getActivity();
        return view;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_personal_center, container, false);
        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        llAll = (LinearLayout) view.findViewById(R.id.ll_my_all_dz);
        tvDaiZhiFu = (TextView) view.findViewById(R.id.tv_my_daizhifu);
        tvYiZhiFu = (TextView) view.findViewById(R.id.tv_my_yizhifu);
        tvPeiSong = (TextView) view.findViewById(R.id.tv_my_peisongzhong);
        tvYiWanCheng = (TextView) view.findViewById(R.id.tv_my_yiwancheng);
        tvTuiuan = (TextView) view.findViewById(R.id.tv_my_tuikuan);

        tvDaiZhiFu.setOnClickListener(this);
        tvYiZhiFu.setOnClickListener(this);
        tvPeiSong.setOnClickListener(this);
        tvYiWanCheng.setOnClickListener(this);
        tvTuiuan.setOnClickListener(this);

        llAll.setOnClickListener(this);
        rl_head = (LinearLayout) view.findViewById(R.id.rl_head);
        rl_head.setOnClickListener(this);
        img_head = (CircleImageView) view.findViewById(R.id.img_head);
        fujin_login = (TextView) view.findViewById(R.id.fujin_login);
        nick_name = (TextView) view.findViewById(R.id.nick_name);
        my_yue = (TextView) view.findViewById(R.id.my_yue);
        money = (TextView) view.findViewById(R.id.money);
        chongzhi = (TextView) view.findViewById(R.id.chongzhi);
        chongzhi.setOnClickListener(this);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        mineItems = getNoneHongData();
        listView = (ListView) view.findViewById(R.id.listView_personal_center);
        adapter = new CommonAdapter(mineItems) {
            @Override
            public DefaultHolder getHolder() {
                return new PersonalCenterHolder();
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        UiUtils.setListViewHeightOnChildren(listView);
    }

    @Override
    public void onResume() {
        splashUI();
        super.onResume();
    }

    private void splashUI() {
        if (isLogin()) {
            btn_exit.setVisibility(View.VISIBLE);
            //登录以后可以进行更换头像昵称等操作
            nick_name.setVisibility(View.VISIBLE);

            UserUtils.getUserInfo(getTokenId(), new UserUtils.getUserInfoListener() {
                @Override
                public void load(boolean status, UserResponse info, String message) {
                    if (status) {
                        //保存用户信息
                        upDateUserStatus(info.getUserInfo());
                        //显示用户信息详情
                        showUserInfo(info.getUserInfo());

                    } else {
                        ToastUtil.show(message);
                    }
                    loadingDataBarClose();
                }
            });
        } else {
            img_head.setImageResource(R.mipmap.icon_personal_center_user_head);
            btn_exit.setVisibility(View.INVISIBLE);
            fujin_login.setText("注册/登录");
            nick_name.setVisibility(View.GONE);
            money.setText("¥0.00");
            rl_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toNext(LoginActivity.class);
                }
            });
            img_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toNext(LoginActivity.class);
                }
            });
        }
    }

    private void showUserInfo(UserInfo info) {
        AppContext.getImageLoaderProxy().displayImage(AppConfig.ENDPOINTPIC + info.getAvatar(), img_head);
        fujin_login.setText(info.getNickname());
        if (info.getCd_acc() != null) {
            nick_name.setText("智能货柜号:" + info.getCd_acc());
        } else {
            nick_name.setText("");
        }
        money.setText("¥" + String.format("%.2f", Double.parseDouble(info.getAmount())));
        rl_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AccountManagerActivity.class));
            }
        });
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNext(AccountManagerActivity.class);
            }
        });
    }


    private List<MineItem> getNoneHongData() {
        List<MineItem> list = new ArrayList<>();
        String[] array = UiUtils.getStringArray(R.array.personal_center_v);
        for (int i = 0; i < array.length; i++) {
            MineItem item = new MineItem();
            if (array[i].equals("收货地址")) {
                item.setItem("收货地址");
                item.setVisible(false);
                item.setIcon(R.mipmap.grzx_shouhuodizhi);
            }

            if (array[i].equals("邀请好友")) {
                item.setItem("邀请好友");
                item.setVisible(false);
                item.setIcon(R.mipmap.icon_goumaijilu);
            }

            if (array[i].equals("邀请码")) {
                item.setItem("邀请码");
                item.setVisible(false);
                item.setIcon(R.mipmap.icon_yaoqingma);
            }

            if (array[i].equals("我的推荐")) {
                item.setItem("我的推荐");
                item.setVisible(false);
                item.setIcon(R.mipmap.icon_bangzhuyufankui);
            }

            if (array[i].equals("我的评价")) {
                item.setItem("我的评价");
                item.setVisible(false);
                item.setIcon(R.mipmap.icon_wodepinjia);
            }

            if (array[i].equals("反馈")) {
                item.setItem("反馈");
                item.setVisible(false);
                item.setIcon(R.mipmap.icon_fankui);
            }

            if (array[i].equals("关于我们")) {
                item.setItem("关于我们");
                item.setVisible(false);
                item.setIcon(R.mipmap.icon_gunayu);
            }
            list.add(item);
        }

        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MineItem item = mineItems.get(position);
        switch (item.getIcon()) {
            case R.mipmap.grzx_shouhuodizhi://收货地址
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), AdressActivity.class));
                } else {
                    toNext(LoginActivity.class);
                }
                break;

            case R.mipmap.icon_goumaijilu://邀请好友
                if (isLogin()) {
                    share();
                } else {
                    toNext(LoginActivity.class);
                }
                break;

            case R.mipmap.icon_yaoqingma://邀请码
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), InviteCodeActivity.class));
                } else {
                    toNext(LoginActivity.class);
                }
                break;

            case R.mipmap.icon_bangzhuyufankui://我的推荐
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), MyTuiJianActivity.class));
                } else {
                    toNext(LoginActivity.class);
                }
                break;

            case R.mipmap.icon_wodepinjia://我的评价
                if (isLogin()) {
                    toNext(MyCommentActivity.class);
                } else {
                    toNext(LoginActivity.class);
                }
                break;

            case R.mipmap.icon_fankui://反馈
                if (isLogin()) {
                    toNext(FeedBackActivity.class);
                } else {
                    toNext(LoginActivity.class);
                }
                break;

            case R.mipmap.icon_gunayu://关于
                toNext(AboutActivity.class);
                break;
        }
    }

    View.OnClickListener exitLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PrefereUtils.getInstance().fixLoginStatus(false);
            PrefereUtils.getInstance().saveToken("");
            splashUI();
            btn_exit.setVisibility(View.INVISIBLE);
            PopUtil.dismissPop();
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fujin_login:
                toNext(LoginActivity.class);
                break;
            case R.id.nick_name:

                break;
            case R.id.chongzhi:
                if (isLogin()) {
                    toNext(RechargeActivity.class);
                } else {
                    toNext(LoginActivity.class);
                }

                break;
            case R.id.btn_exit:
                PopUtil.showDialog(getActivity(), "退出确认", "退出当前账号", "取消", "确定退出", null, exitLoginListener);
                break;

            case R.id.ll_my_all_dz://全部订单
                Intent intent = new Intent(getActivity(), AllDingDanActivity.class);
                intent.putExtra("type", "99");
                startActivity(intent);
                break;
            case R.id.tv_my_daizhifu://待支付
                Intent intent2 = new Intent(getActivity(), AllDingDanActivity.class);
                intent2.putExtra("type", "0");
                startActivity(intent2);
                break;

            case R.id.tv_my_yizhifu://已支付
                Intent intent3 = new Intent(getActivity(), AllDingDanActivity.class);
                intent3.putExtra("type", "1");
                startActivity(intent3);
                break;
            case R.id.tv_my_peisongzhong://配送中
                Intent intent4 = new Intent(getActivity(), AllDingDanActivity.class);
                intent4.putExtra("type", "7");
                startActivity(intent4);
                break;
            case R.id.tv_my_yiwancheng://已完成
                Intent intent5 = new Intent(getActivity(), AllDingDanActivity.class);
                intent5.putExtra("type", "9");
                startActivity(intent5);
                break;
            case R.id.tv_my_tuikuan://退款
                Intent intent6 = new Intent(getActivity(), AllDingDanActivity.class);
                intent6.putExtra("type", "2");
                startActivity(intent6);
                break;

        }
    }

    /**
     * 分享
     */
    private void share() {
        String uid = BaseApp.uid();
        String shareUrl = AppConfig.ENDPOINT + "/api/Invitingfriends/index.html?uid=" + uid + "&driver=" + 1;

        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("邀请好友");
        oks.setTitleUrl(shareUrl);
        oks.setText(shareUrl);
        oks.setImagePath(AppConfig.ENDPOINT + "/logo.png");//确保SDcard下面存在此张图片
        oks.setUrl(shareUrl);
        oks.setSite(getString(R.string.app_name));
        oks.setSiteUrl(shareUrl);
        oks.show(getActivity());
    }
}
