package com.jiuyou.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiuyou.Banner;
import com.jiuyou.MyImageLoader;
import com.jiuyou.customctrls.CustomSwipRefresh;
import com.jiuyou.customctrls.HomeView;
import com.jiuyou.customctrls.MyScrollView;
import com.jiuyou.network.response.JZBResponse.BannerData;
import com.jiuyou.network.response.JZBResponse.BannerResponse1;
import com.jiuyou.ui.activity.GoodsDetailActivity;
import com.jiuyou.ui.activity.MainActivity;
import com.jiuyou.ui.base.OnPageEventListener;
import com.jiuyou.util.PrefereUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jiuyou.R;
import com.jiuyou.customctrls.ImageCycleView;
import com.jiuyou.customctrls.MyGridView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.BannerResponse;
import com.jiuyou.network.response.JZBResponse.DataBanner;
import com.jiuyou.network.response.JZBResponse.HomeDataList;
import com.jiuyou.network.response.JZBResponse.HoomeGoodsResponse;
import com.jiuyou.ui.Utils.GoodsUtils;
import com.jiuyou.ui.activity.SearchActivity;
import com.jiuyou.ui.adapter.HomeListAdapter;
import com.jiuyou.ui.base.BaseFragment;
import com.jiuyou.util.ScreenUtils;
import com.jiuyou.util.ToastUtil;

import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment implements View.OnClickListener, OnPageEventListener, OnBannerClickListener {

    private View view;
    private ImageView search_filter, erweima;
    private TextView edt_search;
    private ImageCycleView mHomeBanner;
    Banner mBanner;
    private List<String> picStr;
    private List<DataBanner> dataList;
    private List<Category> categories;
    private StringAdapter mAdapter;
    private List<BannerData> mBanners;
    private HomeListAdapter adapter;
    private Context mConText;
    private PopupWindow pop;
    private ListView listView;
    private String currentCategory = "0";
    private CustomSwipRefresh srfl_my_dynamic;
    private MyGridView myGridView;
    private int mCurrentPage = 1;
    private List<HomeDataList> data_list;
    private TextView pro_all;
    private MyScrollView scrollView;
    private String totalPage;
    private HomeView homeView;
    View contentview;
    private boolean isRefresh = false;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            isRefresh = false;
            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            initView(view);
        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }

        mConText = getActivity();
        return view;
    }

    @Override
    public View initView() {
        picStr = new ArrayList<>();
        contentview = LayoutInflater.from(getActivity()).inflate(R.layout.home_view_content, null, false);
        pro_all = (TextView) contentview.findViewById(R.id.pro_all);
        pro_all.setText("全部商品");
        myGridView = (MyGridView) contentview.findViewById(R.id.gridview);
        mHomeBanner = (ImageCycleView) contentview.findViewById(R.id.homebanner1);
        mBanner = (Banner) contentview.findViewById(R.id.banner);
        mBanner.setOnBannerClickListener(this);
        ViewGroup.LayoutParams params = mHomeBanner.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        params.height = (int) (widthPixels / 1.33);
        mHomeBanner.setLayoutParams(params);
        srfl_my_dynamic = (CustomSwipRefresh) contentview.findViewById(R.id.refresh_view);

        srfl_my_dynamic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 0;
                if(data_list!=null){
                    dataList.clear();
                }
                initInfos();
                getBanners();
                if (srfl_my_dynamic.isRefreshing()) {
                    srfl_my_dynamic.setRefreshing(false);
                }
            }
        });
        scrollView = (MyScrollView) contentview.findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);
        scrollView.setOnScrollToBottomLintener(new MyScrollView.OnScrollToBottomListener() {

            @Override
            public void onScrollBottomListener(boolean isBottom) {
                /**这里遇到一个问题，当数据加载完成后，向上滑动ScrollView,还会提示一遍“没有更多数据了”，所以多加了一个向下滑动的标记isTop;如果是判断向下滑动，并且isBottom是滑动到了最低端才加载数据**/
                if (isBottom && scrollView.isTop() && !isRefresh) {
                    isRefresh = true;
                    if (srfl_my_dynamic.isRefreshing()) {
                        srfl_my_dynamic.setRefreshing(false);
                    }
                    mCurrentPage++;
                    if (mCurrentPage <= Integer.parseInt(totalPage)) {
                        loadMoreData(mCurrentPage);
                    } else {
                        ToastUtil.show("没有更多商品了！");
                    }
                    handler.sendEmptyMessageDelayed(0, 1000);
                } else {
                    //GetToast.showToast(ScrollingActivity.this,isBottom+"");
                }
            }
        });
        return contentview;
    }

    @Override
    public void loadData() {
        initData();
    }

    private void initView(View view) {
        search_filter = (ImageView) view.findViewById(R.id.search_filter);
        search_filter.setOnClickListener(this);
        erweima = (ImageView) view.findViewById(R.id.erweima);
        erweima.setOnClickListener(this);
        edt_search = (TextView) view.findViewById(R.id.edt_search);
        edt_search.setOnClickListener(this);
        homeView = (HomeView) view.findViewById(R.id.home_view);
        homeView.setOnPageEventListener(this);
        homeView.showViewByState(HomeView.UNLOADING);
    }

    /**
     * 更改商品状态的弹窗
     */
    public PopupWindow showPopWindow() {
        if (categories != null && categories.size() > 0) {
            View popView = LayoutInflater.from(mConText).inflate(R.layout.pop_popup, null);
            listView = (ListView) popView.findViewById(R.id.fenlei_listview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentCategory = categories.get(position).getId();
                    pro_all.setText(categories.get(position).getCategory());
                    data_list = null;
                    splshInfos();
                    pop.dismiss();
                }
            });
            mAdapter = new StringAdapter();
            listView.setAdapter(mAdapter);
            // 创建PopupWindow对象
            pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
//                      pop = new PopupWindow(popView, 100, 152, false);
            // 需要设置一下此参数，点击外边可消失
            pop.setBackgroundDrawable(new ColorDrawable());
            // 设置点击窗口外边窗口消失
            pop.setOutsideTouchable(true);
            // 设置此参数获得焦点，否则无法点击
            pop.setFocusable(true);
            pop.setHeight((int) (ScreenUtils.getScreenH(getActivity()) / 2.2));
            pop.setWidth((ScreenUtils.getScreenW(getActivity()) / 51) * 20);
            pop.showAsDropDown(search_filter, -15, 50);
            WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
            getActivity().getWindow().setAttributes(params);
            return pop;
        } else {
            return null;
        }
    }


    private void initData() {
//        initCircleImageView();
        getBanners();
        initInfos();
        initCatagory();
    }

    //获取首页分类列表
    private void initCatagory() {
        GoodsUtils.getCategory(PrefereUtils.getInstance().getToken(), new GoodsUtils.getCategoryListener() {
            @Override
            public void load(boolean status, BannerResponse info, String message) {
                if (status) {
                    dataList = info.getData();
                    categories = new ArrayList<Category>();
                    categories.add(new Category("", "全部商品"));
                    if (null != dataList && dataList.size() > 0) {
                        for (int i = 0; i < dataList.size(); i++) {
                            categories.add(new Category(dataList.get(i).getId(), dataList.get(i).getTitle()));
                        }
                    }

                } else {
                    if (message != null) {
                        ToastUtil.show(message);
                    }
                }
            }
        });
    }

    //网络请求首页展示信息
    private void initInfos() {
        mCurrentPage = 1;
        GoodsUtils.getHomeInfos(currentCategory, mCurrentPage + "", AppConfig.pageSize + "", new GoodsUtils.getInfosListener() {
            @Override
            public void load(boolean status, HoomeGoodsResponse info, String message) {
                if (status) {
                    ArrayList<HomeDataList> list = info.getData().getData_list();
                    if (data_list == null) {
                        totalPage = info.getData().getTotalPage();
                        data_list = list;
                        adapter = new HomeListAdapter((MainActivity) getActivity(), data_list);
                        myGridView.setAdapter(adapter);
                    } else {
                        data_list.clear();
                        data_list = list;
                        adapter.setmDatas(data_list);
                    }
                    homeView.showViewByState(HomeView.LOADSUCCESS);

                } else {
                    if (!message.equals("")) {
                        ToastUtil.show(message);
                    }

                }
            }
        });

    }

    //网络请求首页展示信息
    private void splshInfos() {
        mCurrentPage = 1;
        GoodsUtils.getHomeInfos(currentCategory, mCurrentPage + "", AppConfig.pageSize + "", new GoodsUtils.getInfosListener() {
            @Override
            public void load(boolean status, HoomeGoodsResponse info, String message) {
                if (status) {
                    data_list = info.getData().getData_list();
                    adapter = new HomeListAdapter((MainActivity) getActivity(), data_list);
                    myGridView.setAdapter(adapter);

                } else {
                    if (!message.equals("")) {
                        List<HomeDataList> data_list = new ArrayList<HomeDataList>();
                        adapter = new HomeListAdapter((MainActivity) getActivity(), data_list);
                        myGridView.setAdapter(adapter);
                        ToastUtil.show(message);
                    }

                }
            }
        });

    }

    private void loadMoreData(int currentPage) {
        GoodsUtils.getHomeInfos(currentCategory, currentPage + "", AppConfig.pageSize + "", new GoodsUtils.getInfosListener() {
            @Override
            public void load(boolean status, HoomeGoodsResponse info, String message) {
                if (status) {
                    List<HomeDataList> data_list = info.getData().getData_list();
                    adapter.addDatas(data_list);
                } else {
                    ToastUtil.show("没有更多商品了");
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
//                        ToastUtil.show("进入商品详情");

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_filter:
                showPopWindow();
                break;
            case R.id.erweima:
                //扫描操作
                Intent intent = new Intent(getActivity(),
                        com.jiuyou.ui.activity.saomiao.CaptureActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.edt_search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String result = scanResult.getContents();
            Log.d("code", result);
            ToastUtil.show(result);
        }
    }

    @Override
    public void OnBannerClick(int position) {
        try {
            Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
            intent.putExtra("goodid", mBanners.get(position - 1).getId());
            getActivity().startActivity(intent);
        } catch (Exception e) {

        }
    }

    /**
     * 适配器
     */
    private class StringAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.feilei_item, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.name);
            tv.setText(categories.get(position).getCategory());
            return convertView;
        }
    }

    private class Category {
        private String id;
        private String category;

        public Category(String id, String category) {
            this.id = id;
            this.category = category;
        }

        public String getId() {
            return id;
        }

        public String getCategory() {
            return category;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }


    /**
     * set Banner
     */
    private void setBanner(List<String> list) {
        //设置banner样式
        ViewGroup.LayoutParams params = mBanner.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        params.height = (int) (widthPixels / 1.33);
        mBanner.setLayoutParams(params);

        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new MyImageLoader());
        //设置图片集合
        mBanner.setImages(list);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        mBanner.setViewPagerIsScroll(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    private void getBanners() {
        GoodsUtils.getHomeBanner(new GoodsUtils.getHomeBannerListener() {
            @Override
            public void load(boolean status, BannerResponse1 info, String message) {
                if (status) {
                    if (picStr == null) {
                        return;
                    }
                    picStr.clear();
                    mBanners = info.getData();
                    Log.e("gy", "信息：" + info.getData().size());
                    for (int i = 0; i < info.getData().size(); i++) {
                        picStr.add(info.getData().get(i).getBanner());
                    }

                    setBanner(picStr);
                }
            }
        });
    }
}
