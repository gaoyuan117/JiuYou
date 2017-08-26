/*
 * CommonUtils     2016/11/5 10:36
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.jiuyou.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommonUtil {
    private static final String TAG = CommonUtil.class.getSimpleName();
    private static long lastClickTime;

    /**
     * 判断页面在一秒内是否被多次点击
     * @return boolean
     */
    public static boolean isLeastSingleClick(){
        return isLeastSingleClick(1000);
    }

    /**
     * 判断页面否被多次点击
     * @param durtion 间隔时长
     * @return
     */
    public static boolean isLeastSingleClick(int durtion){
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if(0 < timeD && timeD < durtion){
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 类型安全的String 转 Int
     * @param objString 要转换的对象
     * @param defValue  默认值
     * @return
     */
    public final static int convertToInt(Object objString, int defValue){
        if(objString == null || "".equals(objString.toString().trim())){
            return defValue;
        }

        try{
            return Integer.valueOf(objString.toString());
        }catch(Exception e){
            try{
                return Double.valueOf(objString.toString()).intValue();
            }catch(Exception el){
                return defValue;
            }
        }
    }

    /**
     * 设置EditView光标在文字末尾
     * @param editText
     */
    public static void setEditViewSelectionEnd(EditText editText){
        CharSequence text = editText.getText();
        if(text instanceof Spannable){
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * 设置TextView 右侧图片
     * @param context
     * @param tvew
     * @param resId   图片资源ID
     */
    public static void setDrawableRight(Context context, TextView tvew, int resId){
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvew.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置TextView 右侧图片
     * @param context
     * @param tvew
     * @param drawable 图片资源ID
     */
    public static void setDrawableRight(Context context, TextView tvew, Drawable drawable){
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        tvew.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置TextView 顶部图片
     * @param context
     * @param tvew
     * @param resId   图片资源ID
     */
    public static void setDrawableTop(Context context, TextView tvew, int resId){
        Drawable drawable = context.getResources().getDrawable(resId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvew.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * Description: 设置TextView 左侧图片
     * @param context
     * @param tvew
     * @param resId   图片资源ID
     */
    public static void setDrawableLeft(Context context, TextView tvew, int resId){
        Drawable drawable = context.getResources().getDrawable(resId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvew.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * Description: 设置TextView 底部图片
     * @param context
     * @param tvew
     * @param resId   图片资源ID
     * @date 2014-3-31
     */
    public static void setDrawableBottom(Context context, TextView tvew, int resId){
        Drawable drawable = context.getResources().getDrawable(resId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvew.setCompoundDrawables(null, null, null, drawable);
    }

    /**
     * 检验EditText输入的内容是否为空
     */
    public static boolean checkEt(Context context, EditText editText, String toast){
        String content = editText.getText().toString().trim();

        if(TextUtils.isEmpty(content)){
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 检验EditText输入的内容是否为空
     */
    public static boolean checkEt(Context context, EditText editText){
        return checkEt(context, editText, "请填写完整信息");
    }

    /**
     * 不能输入表情的EditText
     */
    public static class TextNoEmojiWatcher implements TextWatcher{
        private EditText et;
        private Activity context;
        private int length;

        public TextNoEmojiWatcher(EditText et,Activity context){
            this.et = et;
            this.context=context;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){
            // i("s:" + s);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count){
            // e("s:" + s + "  start:" + start + " before:" + before + " count:" + count);
            //输入的类容
            CharSequence input = s.subSequence(start, start + count);
            //e("输入信息:" + input);
            // 退格
            if(count == 0) return;

            //如果 输入的类容包含有Emoji
            if(isEmojiCharacter(input)){
                //那么就去掉
                et.setText(removeEmoji(s));
            }

            //如果输入的字符超过最大限制,超出的部分 砍掉~
            if(s.length() > 50){
                et.setText(s.subSequence(0, start));
                Toast.makeText(context,"最多输入50个字！",Toast.LENGTH_LONG).show();
            }
            //最后光标移动到最后 TODO 这里可能会有更好的解决方案
            et.setSelection(et.getText().toString().length());

        }

        @Override
        public void afterTextChanged(Editable s){
            // d("s:" + s);
            if(s.length() == 50){
                Toast.makeText(context,"最多输入50个字！",Toast.LENGTH_LONG).show();
            }
        }


        /**
         * 去除字符串中的Emoji表情
         * @param source
         * @return
         */
        private String removeEmoji(CharSequence source){
            String result = "";
            for(int i = 0; i < source.length(); i++){
                char c = source.charAt(i);
                if(isEmojiCharacter(c)){
                    continue;
                }
                result += c;
            }
            return result;
        }


        /**
         * 判断一个字符串中是否包含有Emoji表情
         * @param input
         * @return true 有Emoji
         */
        private boolean isEmojiCharacter(CharSequence input){
            for(int i = 0; i < input.length(); i++){
                if(isEmojiCharacter(input.charAt(i))){
                    return true;
                }
            }
            return false;
        }

        /**
         * 是否是Emoji 表情,抄的那哥们的代码
         * @param codePoint
         * @return true 是Emoji表情
         */
        private boolean isEmojiCharacter(char codePoint){
            // Emoji 范围
            boolean isScopeOf = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                    || ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
                    || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                    || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

            return !isScopeOf;
        }
    }

    /***
     * 输入价格的EditText
     */
    public static class DecimalTextWatcher implements TextWatcher{
        private EditText editText;
        private int length = 0;

        /**
         * @param length 小数点位数
         */
        DecimalTextWatcher(EditText editText, int length){
            this.editText = editText;
            this.length = length;
        }

        @Override
        public void afterTextChanged(Editable arg0){
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count){
            if(s.toString().contains(".")){
                if(s.length() - 1 - s.toString().indexOf(".") > length){
                    s = s.toString().subSequence(0, s.toString().indexOf(".") + length + 1);
                    editText.setText(s);
                    editText.setSelection(s.length());
                }else{
                }
            }else{
            }

            if(s.toString().trim().equals(".")){ //又只有一个 .
                s = "0" + s;
                editText.setText(s);
                editText.setSelection(2);
            }

            if(s.toString().startsWith("0") && s.toString().trim().length() > 1){
                if(!s.toString().substring(1, 2).equals(".")){
                    editText.setText(s.subSequence(0, 1));
                    editText.setSelection(1);
                }else{
                }
            }

            if(s.toString().trim().endsWith(".")){
            }
        }
    }
}
