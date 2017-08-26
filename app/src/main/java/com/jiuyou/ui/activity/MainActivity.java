package com.jiuyou.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuyou.R;
import com.jiuyou.core.ActivitysManager;
import com.jiuyou.global.AppConfig;
import com.jiuyou.ui.base.BaseFragmentActivity;
import com.jiuyou.ui.base.DummyTabContent;
import com.jiuyou.ui.fragment.AccountFragment;
import com.jiuyou.ui.fragment.HomeFragment;
import com.jiuyou.ui.fragment.MapFragment;
import com.jiuyou.ui.fragment.MineFragment;


public class MainActivity extends BaseFragmentActivity {
    public static String TAB_HOME = "home";
    public static String TAB_MAP = "map";
    public static String TAB_ACCOUNT = "account";
    public static String TAB_MINE = "mine";

    FragmentManager fragmentManager;
    TabHost mTabHost;
    HomeFragment homePageFragment;
    MapFragment mapPageFragment;
    AccountFragment accountPageFragment;
    MineFragment mineFragment;

    ImageView homeImg, mapImg, accountImg, mineImg;
    TextView homeTxt, mapTxt, accountTxt, mineTxt;
    Fragment lastFrag;

    View homeView, mapView, accountView, mineView;
    private static final int BAIDU_READ_PHONE_STATE = 100;

    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        } else {
//            init();
        }
    }


    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
//                    init();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(this.getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showContacts();
        init();
    }

    @Override
    protected void onResume() {
        if (AppConfig.currentTAB != "") {
            if (AppConfig.currentTAB.equals(MainActivity.TAB_HOME)) {
            } else {
                setTabSelection(AppConfig.currentTAB);
            }

        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        int tabHeight = screenHeight / 12;
        // int imgHeight = (int) ((float) (tabHeight / 4) * 3);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.getTabWidget().setDividerDrawable(null);
        TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
        android.view.ViewGroup.LayoutParams layoutParams = tabWidget
                .getLayoutParams();
        layoutParams.height = tabHeight;
        tabWidget.setLayoutParams(layoutParams);

        TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                setTabSelection(tabId);
            }
        };
        mTabHost.setOnTabChangedListener(onTabChangeListener);
        LayoutInflater inflater = LayoutInflater.from(this);
        // 首页
        homeView = inflater.inflate(R.layout.tabspec_home_page, null);
        homeImg = (ImageView) homeView.findViewById(R.id.img_home);
        homeTxt = (TextView) homeView.findViewById(R.id.txt_home);

        TabHost.TabSpec specHomePage = mTabHost.newTabSpec(TAB_HOME);
        specHomePage.setIndicator(homeView);
        specHomePage.setContent(new DummyTabContent(getBaseContext()));
        mTabHost.addTab(specHomePage);
        // 地图
        mapView = inflater.inflate(R.layout.tabspec_map, null);
        mapImg = (ImageView) mapView.findViewById(R.id.img_calendar);
        mapTxt = (TextView) mapView.findViewById(R.id.txt_calendar);

        TabHost.TabSpec specCalendar = mTabHost.newTabSpec(TAB_MAP);
        specCalendar.setIndicator(mapView);
        specCalendar.setContent(new DummyTabContent(getBaseContext()));
        mTabHost.addTab(specCalendar);

        //结算
        accountView = inflater.inflate(R.layout.tabspec_account, null);
        accountImg = (ImageView) accountView.findViewById(R.id.img_policy);
        accountTxt = (TextView) accountView.findViewById(R.id.txt_policy);
        TabHost.TabSpec specPolicy = mTabHost.newTabSpec(TAB_ACCOUNT);
        specPolicy.setIndicator(accountView);
        specPolicy.setContent(new DummyTabContent(getBaseContext()));
        mTabHost.addTab(specPolicy);

        // 我的
        mineView = inflater.inflate(R.layout.tabspec_mine, null);
        mineImg = (ImageView) mineView.findViewById(R.id.img_mine);
        mineTxt = (TextView) mineView.findViewById(R.id.txt_mine);
        TabHost.TabSpec specMine = mTabHost.newTabSpec(TAB_MINE);
        specMine.setIndicator(mineView);
        specMine.setContent(new DummyTabContent(getBaseContext()));
        mTabHost.addTab(specMine);

        Intent mResultIndex = getIntent();
        int showIndex = mResultIndex.getIntExtra("showIndex", 0);
        mTabHost.setCurrentTab(showIndex);

    }

    public void setTabSelection(String s) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        detachFragments(transaction);
        mTabHost.setCurrentTabByTag(s);
        if (s.equals(TAB_HOME)) {
            homeImg.setBackgroundResource(R.mipmap.icon_shouye_xuanzhong);
            homeView.setBackgroundColor(getResources().getColor(R.color.normal_bg));
            homeTxt.setTextColor(getResources().getColor(R.color.title_bg));
            if (homePageFragment == null) {
                lastFrag = homePageFragment = new HomeFragment();
                // 如果为空，则创建一个并添加到界面上
                transaction.replace(R.id.realtabcontent, homePageFragment,
                        TAB_HOME);
            } else {
                // 如果不为空，则直接将它显示出来
                lastFrag = homePageFragment;
                transaction.attach(homePageFragment);
            }
        } else if (s.equals(TAB_MAP)) {
            mapImg.setBackgroundResource(R.mipmap.icon_ditu_xauzhong);
            mapView.setBackgroundColor(getResources().getColor(R.color.normal_bg));
            mapTxt.setTextColor(getResources().getColor(R.color.title_bg));
            if (mapPageFragment == null) {
                lastFrag = mapPageFragment = new MapFragment();
                // 如果为空，则创建一个并添加到界面上
                transaction.replace(R.id.realtabcontent, mapPageFragment,
                        TAB_MAP);
            } else {
                // 如果不为空，则直接将它显示出来
                lastFrag = mapPageFragment;
                transaction.attach(mapPageFragment);
            }
        } else if (s.equals(TAB_MINE)) {
            mineImg.setBackgroundResource(R.mipmap.iconwode_xuanzhong);
            mineView.setBackgroundColor(getResources().getColor(R.color.normal_bg));
            mineTxt.setTextColor(getResources().getColor(R.color.title_bg));
            if (mineFragment == null) {
                lastFrag = mineFragment = new MineFragment();
                // 如果为空，则创建一个并添加到界面上
                transaction.replace(R.id.realtabcontent, mineFragment,
                        TAB_MINE);
            } else {
                // 如果不为空，则直接将它显示出来
                lastFrag = mineFragment;
                transaction.attach(mineFragment);
            }
        } else if (s.equals(TAB_ACCOUNT)) {
            accountImg.setBackgroundResource(R.mipmap.icon_jiesuan_xuanzhogn);
            accountView.setBackgroundColor(getResources().getColor(R.color.normal_bg));
            accountTxt.setTextColor(getResources().getColor(R.color.title_bg));
            if (accountPageFragment == null) {
                lastFrag = accountPageFragment = new AccountFragment();
                // 如果为空，则创建一个并添加到界面上
                transaction.replace(R.id.realtabcontent, accountPageFragment,
                        TAB_ACCOUNT);
            } else {
                // 如果不为空，则直接将它显示出来
                lastFrag = accountPageFragment;
                transaction.attach(accountPageFragment);
            }
        }
        transaction.commitAllowingStateLoss();

    }


    @SuppressLint("ResourceAsColor")
    private void detachFragments(FragmentTransaction transaction) {
        if (lastFrag != null) {
            transaction.detach(lastFrag);
        }
        if (homePageFragment != null) {
            homeImg.setBackgroundResource(R.mipmap.icon_shouye_weixuanzhong);
            homeView.setBackgroundColor(getResources().getColor(
                    R.color.normal_bg));
            homeTxt.setTextColor(getResources().getColor(
                    R.color.shallow_bg));

        }

        if (mapPageFragment != null) {
            mapImg.setBackgroundResource(R.mipmap.icon_ditu_weixuanzhong);
            mapTxt.setBackgroundColor(getResources().getColor(
                    R.color.normal_bg));
            mapTxt.setTextColor(getResources().getColor(
                    R.color.shallow_bg));

        }

        if (accountPageFragment != null) {
            accountImg.setBackgroundResource(R.mipmap.icon_jiesuan_weixuanzhong);
            accountTxt.setBackgroundColor(getResources().getColor(
                    R.color.normal_bg));
            accountTxt.setTextColor(getResources().getColor(
                    R.color.shallow_bg));

        }
        if (mineFragment != null) {
            mineImg.setBackgroundResource(R.mipmap.icon_wode_wexuaznhong);
            mineTxt.setBackgroundColor(getResources().getColor(
                    R.color.normal_bg));
            mineTxt.setTextColor(getResources().getColor(
                    R.color.shallow_bg));
        }
    }


    private long exitTime = 0;

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivitysManager.getInstance().AppExit(this);
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
