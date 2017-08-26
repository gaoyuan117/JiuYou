package com.jiuyou.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jiuyou.R;

public class ActivityImageShower extends Activity {
	private ImageView iv_big,guanbi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageshower);
		iv_big=(ImageView) findViewById(R.id.iv_big);
		guanbi=(ImageView) findViewById(R.id.guanbi);
		String erweima=getIntent().getStringExtra("erweima");
		Glide.with(getApplicationContext()).load(erweima).placeholder(R.mipmap.icon_nopic).into(iv_big);
		guanbi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}


}
