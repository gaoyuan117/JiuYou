package com.jiuyou.customctrls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 */
public class FontTextView extends TextView{

    public static Typeface tf;
    public static Typeface tf_bold;

    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 字体
     */
    public void init(Context context){


            if (tf == null) {
                tf = Typeface.createFromAsset(context.getAssets(),
                        "font/SIMYOU.TTF");
            }
        setTypeface(tf);
    }



}
