package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.jiuyou.R;
import com.jiuyou.customctrls.pager.ListViewFootLayout;
import com.jiuyou.customctrls.pager.PullToRefreshBase;
import com.jiuyou.customctrls.pager.PullToRefreshListView;
import com.jiuyou.customctrls.pager.RotateLoadingLayout;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.DataList;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.adapter.RechargeListAdapter;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class RechargeListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,PullToRefreshBase.OnRefreshListener2{

    private ImageView title_bar_operate_1;
    private PullToRefreshListView comment_listview;
    private RechargeListAdapter adapter;
    //当前页
    private int currentPage = 1;
    private int totalPage;
    private List<DataList> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechargelist);
        initView();
        initData();
    }

    private void initView(){
        title_bar_operate_1=(ImageView) findViewById(R.id.title_bar_operate_1);
        title_bar_operate_1.setOnClickListener(this);
        comment_listview=(PullToRefreshListView)findViewById(R.id.comment_listview);
        comment_listview.setMode(PullToRefreshBase.Mode.BOTH);
        comment_listview.setHeaderLayout(new RotateLoadingLayout(this, PullToRefreshBase.Mode.PULL_FROM_START, comment_listview.getPullToRefreshScrollDirection()));
        comment_listview.setFooterLayout(new ListViewFootLayout(this));
        comment_listview.setOnRefreshListener(this);
    }
    private void initData(){
        getLoadingDataBar().show();
        currentPage = 1;
        UserUtils.getRechargeListInfo(PrefereUtils.getInstance().getToken(), currentPage+"", AppConfig.pageSize+"", new UserUtils.getRechargeListListener() {
            @Override
            public void load(boolean status, GoodsResponse info, String message) {
                     if(status){
                         lists=info.getData().getData_list();
                         int totalNum = Integer.parseInt(info.getData().getTotalPage());
                         totalPage=totalNum % AppConfig.pageSize == 0 ? (totalNum / AppConfig.pageSize) : (totalNum / AppConfig.pageSize + 1);
                         adapter=new RechargeListAdapter(RechargeListActivity.this,info.getData().getData_list());
                         comment_listview.setAdapter(adapter);
                     }else{
                         ToastUtil.show(message);
                     }
                     closeProgressBar();
            }
        });

    }

    private void splshData(){

        getLoadingDataBar().show();
        currentPage = 1;
        UserUtils.getRechargeListInfo(PrefereUtils.getInstance().getToken(), currentPage+"", AppConfig.pageSize+"", new UserUtils.getRechargeListListener() {
            @Override
            public void load(boolean status, GoodsResponse info, String message) {
                if(status){
                    lists=info.getData().getData_list();
                    int totalNum = Integer.parseInt(info.getData().getTotalPage());
                    totalPage=totalNum % AppConfig.pageSize == 0 ? (totalNum / AppConfig.pageSize) : (totalNum / AppConfig.pageSize + 1);
                    adapter=new RechargeListAdapter(RechargeListActivity.this,info.getData().getData_list());
                    comment_listview.setAdapter(adapter);
                    comment_listview.onRefreshComplete(lists.size());
                }else{
                    ToastUtil.show(message);
                    if(lists!=null){
                        lists.clear();
                        adapter.notifyDataSetChanged();
                    }
                    comment_listview.onRefreshComplete(0);
                }


                closeProgressBar();
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        splshData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        currentPage++;
        if (currentPage <= totalPage) {
            UserUtils.getRechargeListInfo(PrefereUtils.getInstance().getToken(), currentPage+"", AppConfig.pageSize+"", new UserUtils.getRechargeListListener() {
                @Override
                public void load(boolean status, GoodsResponse info, String message) {
                    if(status){
                        ArrayList<DataList> moreList = (ArrayList) info.getData().getData_list();
                        lists.addAll(moreList);
                        adapter.notifyDataSetChanged();
                        comment_listview.onRefreshComplete(moreList.size());
                    }else{
                        ToastUtil.show(message);
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
                RechargeListActivity.this.finish();
                break;
        }
    }

}
