package com.jiuyou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.ClearEditText;
import com.jiuyou.customctrls.FontTextView;
import com.jiuyou.customctrls.XCRoundRectImageView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.SearchResultResponse;
import com.jiuyou.ui.Utils.GoodsUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.LoginUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.title_bar_common_tv_search)
    FontTextView mTitleBarCommonTvSearch;
    @Bind(R.id.title_bar_common_et_search)
    ClearEditText mTitleBarCommonEtSearch;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView mTitleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout mTitleBarCommonRvViewGroup;
    @Bind(R.id.gridview)
    GridView gridview;
    @Bind(R.id.rl_nogoods)
    RelativeLayout rl_nogoods;
    MySearchAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        rl_nogoods.setVisibility(View.INVISIBLE);
        mTitleBarCommonTvSearch=(FontTextView) findViewById(R.id.title_bar_common_tv_search);
        mTitleBarCommonEtSearch=(ClearEditText) findViewById(R.id.title_bar_common_et_search);
        mTitleBarCommonEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String keyword = mTitleBarCommonEtSearch.getText().toString().trim();

                    if (TextUtils.isEmpty(keyword)) {
                        ToastUtil.show("请输入您要搜索的关键字");
                        return true;
                    }
                    initData();
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().
                                    getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        gridview=(GridView)findViewById(R.id.gridview);
        mTitleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
                AppConfig.currentTAB=MainActivity.TAB_HOME;
            }
        });

        mTitleBarCommonTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {


                String keyword = mTitleBarCommonEtSearch.getText().toString().trim();

                if (TextUtils.isEmpty(keyword)) {
                    ToastUtil.show("请输入您要搜索的关键字");
                    return;
                }
                initData();
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        String key = "";
        key = mTitleBarCommonEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            ToastUtil.show("请输入关键字进行搜索");
            return;
        }
        getLoadingDataBar().show();
        GoodsUtils.getSearchResultInfos(key, new GoodsUtils.getSearchResultListener() {
            @Override
            public void load(boolean status, SearchResultResponse info, String message) {
                if(status){
                    gridview.setVisibility(View.VISIBLE);
                    rl_nogoods.setVisibility(View.INVISIBLE);
                    adapter=new MySearchAdapter(SearchActivity.this,info);
                    gridview.setAdapter(adapter);
                }else{
                    gridview.setVisibility(View.INVISIBLE);
                    rl_nogoods.setVisibility(View.VISIBLE);
                    if (!message.equals("")) {
                        ToastUtil.show(message);
                    }

                }
                closeProgressBar();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:

        }
    }

    public class MySearchAdapter extends BaseAdapter {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater;
        Context context;
        SearchResultResponse searchResultResponse;
        public MySearchAdapter(Context context, SearchResultResponse searchResultResponse) {
            this.searchResultResponse = searchResultResponse;
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return searchResultResponse.getData().size();
        }

        @Override
        public Object getItem(int position) {
            return searchResultResponse.getData().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_searchresult, null);
                viewHolder = new ViewHolder();
                viewHolder.ll_goods1 = (RelativeLayout) convertView.findViewById(R.id.ll_goods1);
                viewHolder.tv_goods1 = (ImageView) convertView.findViewById(R.id.tv_goods1);
                viewHolder.tv_goodsname1 = (TextView) convertView.findViewById(R.id.tv_goodsname1);
                viewHolder.tv_goodsprice1 = (TextView) convertView.findViewById(R.id.tv_goodsprice1);
                viewHolder.iv_jiahao1 = (ImageView) convertView.findViewById(R.id.iv_jiahao1);
                viewHolder.iv_shouwan=(XCRoundRectImageView) convertView.findViewById(R.id.iv_shouwan);
                convertView.setTag(viewHolder);

            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String quantity=searchResultResponse.getData().get(position).getQuantity();
            if(Integer.parseInt(quantity)>0){
                viewHolder.iv_shouwan.setVisibility(View.INVISIBLE);
                viewHolder.iv_jiahao1.setVisibility(View.VISIBLE);
            }else{
                viewHolder.iv_shouwan.setVisibility(View.VISIBLE);
                viewHolder.iv_jiahao1.setVisibility(View.GONE);
            }
            viewHolder.ll_goods1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(SearchActivity.this,GoodsDetailActivity.class);
                    intent.putExtra("goodid",searchResultResponse.getData().get(position).getId());
                    intent.putExtra("quantity",searchResultResponse.getData().get(position).getQuantity());
                    SearchActivity.this.startActivity(intent);
                    SearchActivity.this.finish();
                }
            });
            AppContext.getInstance().getImageLoader().displayImage(AppConfig.ENDPOINTPIC+searchResultResponse.getData().get(position).getMasterImg(),viewHolder.tv_goods1);
            viewHolder.tv_goodsname1.setText(searchResultResponse.getData().get(position).getTitle());
            viewHolder.tv_goodsprice1.setText("¥"+searchResultResponse.getData().get(position).getPrice());
            viewHolder.iv_jiahao1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入结算
                    if (PrefereUtils.getInstance().isLogin()) {
                        AppConfig.currentTAB=MainActivity.TAB_ACCOUNT;
                        SearchActivity.this.finish();
                    } else {
                        LoginUtil.showLogin(SearchActivity.this, LoginActivity.class);
                    }

                }
            });



            return convertView;
        }
        public class ViewHolder {
            private RelativeLayout ll_goods1;
            private ImageView tv_goods1;
            private TextView tv_goodsname1;
            private TextView tv_goodsprice1;
            private ImageView iv_jiahao1;
            private XCRoundRectImageView iv_shouwan;
        }
    }
}
