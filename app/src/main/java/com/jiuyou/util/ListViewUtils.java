package com.jiuyou.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewUtils extends ListView{
	   
	    public ListViewUtils(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	    }  
	  
	    public ListViewUtils(Context context) {  
	        super(context);  
	    }  
	  
	    public ListViewUtils(Context context, AttributeSet attrs, int defStyle) {  
	        super(context, attrs, defStyle);  
	    }  
	  
	    @Override  
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
	  
	        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	                MeasureSpec.AT_MOST);  
	        super.onMeasure(widthMeasureSpec, expandSpec);  
	    }  
	  
	}  
