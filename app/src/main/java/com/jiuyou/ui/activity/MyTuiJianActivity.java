package com.jiuyou.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.TuiJianBean;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MyTuiJianActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;

    private List<Fragment> mFragments;
    private List<RadioButton> mRadioButtons;

    private FragmentManager mManager;

    private int currindex = 0, targetIndex = 0;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tui_jian);
        setCenterTitle("我的推荐");
        initView();
        getTuiJian();
        setListener();
    }

    private void initdata(TuiJianBean bean) {
        mManager = getSupportFragmentManager();
        mFragments = new ArrayList<>();
        mRadioButtons = new ArrayList<>();

        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton button = (RadioButton) mRadioGroup.getChildAt(i);
            MyFragment instance = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", button.getText().toString());
            bundle.putSerializable("data", bean);
            instance.setArguments(bundle);
            mFragments.add(instance);
            mRadioButtons.add((RadioButton) mRadioGroup.getChildAt(i));
        }

        mRadioButtons.get(currindex).setChecked(true);
        mRadioButtons.get(currindex).setTextColor(getResources().getColor(R.color.text_red));
        mRadioButtons.get(currindex).setBackgroundColor(Color.parseColor("#eff0f4"));

        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(R.id.fragment_my, mFragments.get(0));
        transaction.commit();
        currindex = targetIndex;
    }

    private void setListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                for (int i = 0; i < mRadioButtons.size(); i++) {
                    //获取要跳转的页面的对应的坐标位置
                    if (mRadioButtons.get(i).getId() == checkedId) {
                        mRadioButtons.get(i).setTextColor(getResources().getColor(R.color.text_red));
                        mRadioButtons.get(i).setBackgroundColor(Color.parseColor("#eff0f4"));
                        targetIndex = i;
                    } else {
                        mRadioButtons.get(i).setTextColor(Color.rgb(0, 0, 0));
                        mRadioButtons.get(i).setBackgroundColor(Color.parseColor("#feffff"));
                    }
                }
                translateFragment();
            }
        });
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_my);
    }

    private void translateFragment() {
        if (currindex != targetIndex) {
            Fragment curFragment = mFragments.get(currindex);
            Fragment tarFragment = mFragments.get(targetIndex);
            FragmentTransaction transaction = mManager.beginTransaction();
            if (tarFragment.isAdded()) {
                transaction.hide(curFragment).show(tarFragment);
            } else {
                transaction.hide(curFragment).add(R.id.fragment_my, tarFragment);
            }
            transaction.commitAllowingStateLoss();
            currindex = targetIndex;
        }
    }

    protected void setCenterTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        TextView tvTitle = (TextView) findViewById(R.id.center_title_tv);
        tvTitle.setText(title);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取推荐人
     */
    private void getTuiJian() {
        RetrofitClient.getInstance().createApi().getTuiJian(BaseApp.token())
                .compose(RxUtils.<HttpResult<TuiJianBean>>io_main())
                .subscribe(new BaseObjObserver<TuiJianBean>(this, "获取中") {
                    @Override
                    protected void onHandleSuccess(TuiJianBean tuiJianBean) {
                        initdata(tuiJianBean);
                    }
                });
    }
}
