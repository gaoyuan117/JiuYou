package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jiuyou.R;
import com.jiuyou.customctrls.ImageCycleView;
import com.jiuyou.customctrls.pager.ListViewFootLayout;
import com.jiuyou.customctrls.pager.PullToRefreshBase;
import com.jiuyou.customctrls.pager.PullToRefreshListView;
import com.jiuyou.customctrls.pager.RotateLoadingLayout;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.DataList;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.network.response.JZBResponse.OrderDetail;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.adapter.BuyHistoryAdapter;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.RunTaskUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BuyHistoryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2{
    @Bind(R.id.comment_listview)
    PullToRefreshListView comment_listview;
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    //当前页
    private int currentPage = 1;
    private BuyHistoryAdapter adapter;
    private int totalPage;
    private List<DataList> lists;
    private ArrayList<DataList> dataLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyhistory);
        ButterKnife.bind(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 10:
              String  order_no=data.getStringExtra("order_no");
              String  product_id=data.getStringExtra("product_id");
              for(int i=0;i<dataLists.size();i++){
                  if(dataLists.get(i).getOrder_no().equals(order_no)){
                      List<OrderDetail> details =   dataLists.get(i).getOrder_details();
                      for(int j=0;j<details.size();j++){
                          if(details.get(j).getProduct_id().equals(product_id)){
                              dataLists.get(i).getOrder_details().get(j).setIs_comment("1");
                          }
                      }
                  }
              }
              adapter.setList(dataLists);
              adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void getCommentList() {
        currentPage = 1;
        String token=PrefereUtils.getInstance().getToken();
        UserUtils.getBuyHistoryList(token, currentPage+"", AppConfig.pageSize+"", new UserUtils.getRechargeListListener() {
            @Override
            public void load(boolean status, GoodsResponse info, String message) {
                if(status){
                    if(dataLists!=null){
                        dataLists.clear();
                    }
                    dataLists=info.getData().getData_list();
                    int totalNum = Integer.parseInt(info.getData().getTotalPage());
                    totalPage=totalNum % AppConfig.pageSize == 0 ? (totalNum / AppConfig.pageSize) : (totalNum / AppConfig.pageSize + 1);
                    adapter=new BuyHistoryAdapter(BuyHistoryActivity.this,dataLists);
                    comment_listview.setAdapter(adapter);
                }else{
                    ToastUtil.show(message);
                }
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
            UserUtils.getBuyHistoryList(PrefereUtils.getInstance().getToken(), currentPage+"", AppConfig.pageSize+"", new UserUtils.getRechargeListListener() {
                @Override
                public void load(boolean status, GoodsResponse info, String message) {
                    if(status){
                        ArrayList<DataList> moreList = (ArrayList) info.getData().getData_list();
                        dataLists.addAll(moreList);
                        adapter.notifyDataSetChanged();
                        comment_listview.onRefreshComplete(moreList.size());
                    }else{
                        currentPage--;
                        comment_listview.onRefreshComplete(0);
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
        BuyHistoryActivity.this.finish();
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
            BuyHistoryActivity.this.finish();
            AppConfig.currentTAB=MainActivity.TAB_MINE;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
