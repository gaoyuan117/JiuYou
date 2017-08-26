package com.jiuyou.customctrls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jiuyou.R;

public class HomeLoadMoreListview extends ListView implements AbsListView.OnScrollListener{

    private LinearLayout mLoadMore;
    private LinearLayout mNoMore;
    private View footView;

    int mLastVisibleItem;
    int mTotalItem;
    private boolean isLoading;
    private boolean hasNoMore;
    private RecyclerView mFootPics;
    private Context mContext;
    private LinearLayout mHasNoPics;
    private RelativeLayout mMoreGoods;

    public HomeLoadMoreListview(Context context) {
        super(context);
        initView(context);
    }

    public HomeLoadMoreListview(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mGestureDetector = new GestureDetector(new YScrollDetector());
//        setFadingEdgeLength(0);
        initView(context);
    }

    public HomeLoadMoreListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private GestureDetector mGestureDetector;
    OnTouchListener mGestureListener;

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        super.onInterceptTouchEvent(ev);
//        return mGestureDetector.onTouchEvent(ev);
//    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    public void initView(Context context) {
        mContext = context;
        footView = LayoutInflater.from(context).inflate(R.layout.homeloadmore_layout, null);
        this.addFooterView(footView);
        mLoadMore = (LinearLayout) footView.findViewById(R.id.rl_loadmore);
        mNoMore = (LinearLayout) footView.findViewById(R.id.ll_nomore);
        mFootPics = (RecyclerView) footView.findViewById(R.id.rv_landscape);
        mHasNoPics = (LinearLayout) footView.findViewById(R.id.ll_hasNoDatas);
        mMoreGoods = (RelativeLayout) footView.findViewById(R.id.rl_moreGoods);
        mLoadMore.setVisibility(GONE);
        mNoMore.setVisibility(GONE);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //滚动到最后并且滚动停止
        if (mLastVisibleItem == mTotalItem && scrollState == SCROLL_STATE_IDLE && hasNoMore == false) {
            if (!isLoading) {
                isLoading = true;
                mLoadMore.setVisibility(VISIBLE);
                mListener.onLoadMore();
            }
        }
    }

    public void loadComplete() {
        isLoading = false;
        mLoadMore.setVisibility(GONE);
    }

    public void hasNoMoreDatas() {
        isLoading = false;
        hasNoMore = true;
        mLoadMore.setVisibility(GONE);
        mNoMore.setVisibility(GONE);
        mHasNoPics.setVisibility(VISIBLE);
    }

    private HomeLoadMoreListener mListener;
    

    public void setLoadMoreListener(HomeLoadMoreListener listener) {
        mListener = listener;
    }


    public interface HomeLoadMoreListener {
        public void onLoadMore();
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mLastVisibleItem = firstVisibleItem + visibleItemCount;
        this.mTotalItem = totalItemCount;
    }
}
