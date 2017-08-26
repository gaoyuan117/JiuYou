package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.customctrls.ImageCycleView;
import com.jiuyou.customctrls.pager.ListViewFootLayout;
import com.jiuyou.customctrls.pager.PullToRefreshBase;
import com.jiuyou.customctrls.pager.PullToRefreshListView;
import com.jiuyou.customctrls.pager.RotateLoadingLayout;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.DataList;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.ui.Utils.CommentUtils;
import com.jiuyou.ui.adapter.MyCommentListAdapter;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.RunTaskUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MyCommentActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2{
    @Bind(R.id.comment_listview)
    PullToRefreshListView comment_listview;
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    @Bind(R.id.tv_title)
    TextView tv_title;

    //当前页
    private int currentPage = 1;
    private String token;
    private MyCommentListAdapter adapter;
    private int totalPage;
    private List<DataList> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        token = PrefereUtils.getInstance().getToken();
        ButterKnife.bind(this);
        tv_title.setText("我的评价");
        initView();
        initDatas();
    }

    private void initView(){
        title_bar_operate_1.setOnClickListener(this);
        comment_listview.setMode(PullToRefreshBase.Mode.BOTH);
        comment_listview.setHeaderLayout(new RotateLoadingLayout(this, PullToRefreshBase.Mode.PULL_FROM_START, comment_listview.getPullToRefreshScrollDirection()));
        comment_listview.setFooterLayout(new ListViewFootLayout(this));
        comment_listview.setOnRefreshListener(this);
        comment_listview.setOnItemClickListener(this);
    }
    private void initDatas(){
        getCommentList();
    }
    private void getCommentList() {
        currentPage = 1;
        getLoadingDataBar().show();
        CommentUtils.getMyCommentList(token, currentPage+"", AppConfig.pageSize+"", new CommentUtils.getCommentListListener() {
            @Override
            public void load(boolean status, GoodsResponse info, String message) {
                if(status){
                    int totalNum = Integer.parseInt(info.getData().getTotalPage());
                    totalPage=totalNum % AppConfig.pageSize == 0 ? (totalNum / AppConfig.pageSize) : (totalNum / AppConfig.pageSize + 1);
                    lists=info.getData().getData_list();
                    adapter=new MyCommentListAdapter(lists,MyCommentActivity.this);
                    comment_listview.setAdapter(adapter);
                }else{
                     if(message!=null){
                         ToastUtil.show(message);
                     }
                }
                closeProgressBar();
            }
        });

    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
        }
    };
    private ViewGroup.LayoutParams getLayoutWidthParams(RelativeLayout layout) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.width = widthPixels;
        return params;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        RunTaskUtils.runTask(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                UiUtils.post(new Runnable() {
                    @Override
                    public void run() {
                        comment_listview.onRefreshComplete(0);
                    }
                });

            }
        });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

        currentPage++;
        if (currentPage <= totalPage) {
            CommentUtils.getCommentList(token, currentPage+"", AppConfig.pageSize+"", new CommentUtils.getCommentListListener() {
                @Override
                public void load(boolean status, GoodsResponse info, String message) {
                    if(status){
                        ArrayList<DataList> moreList = (ArrayList) info.getData().getData_list();
                        lists.addAll(moreList);
                        adapter.notifyDataSetChanged();
                        comment_listview.onRefreshComplete(moreList.size());
                    }else{
                        if(message!=null){
                            ToastUtil.show(message);
                        }
                    }
                }
            });
        } else {
            currentPage--;
            comment_listview.onRefreshComplete(0);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.title_bar_operate_1:
        MyCommentActivity.this.finish();
        AppConfig.currentTAB=MainActivity.TAB_MINE;
        break;
    case R.id.rl_pingjia:
        break;
}
    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            MyCommentActivity.this.finish();
            AppConfig.currentTAB=MainActivity.TAB_MINE;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
