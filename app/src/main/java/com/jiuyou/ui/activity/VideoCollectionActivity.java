package com.jiuyou.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.jiuyou.R;

/**
 * Created by 子皓 on 2016/9/8.
 */
public class VideoCollectionActivity extends Activity{
    private TextView tv_edit;
    private ListView lv_video;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_collection);

    }
}
