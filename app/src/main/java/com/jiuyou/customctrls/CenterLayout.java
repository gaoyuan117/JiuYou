/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014. Ray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jiuyou.customctrls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews.RemoteView;

@RemoteView
public class CenterLayout extends ViewGroup {
  private int mPaddingLeft = 0;
  private int mPaddingRight = 0;
  private int mPaddingTop = 0;
  private int mPaddingBottom = 0;
  private int mWidth, mHeight;

  public CenterLayout(Context context) {
    super(context);
  }

  public CenterLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CenterLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int count = getChildCount();

    int maxHeight = 0;
    int maxWidth = 0;

    measureChildren(widthMeasureSpec, heightMeasureSpec);

    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);
      if (child.getVisibility() != GONE) {
        int childRight;
        int childBottom;

        LayoutParams lp = (LayoutParams) child.getLayoutParams();

        childRight = lp.x + child.getMeasuredWidth();
        childBottom = lp.y + child.getMeasuredHeight();

        maxWidth = Math.max(maxWidth, childRight);
        maxHeight = Math.max(maxHeight, childBottom);
      }
    }

    maxWidth += mPaddingLeft + mPaddingRight;
    maxHeight += mPaddingTop + mPaddingBottom;

    maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
    maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

    setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec));
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int count = getChildCount();
    mWidth = getMeasuredWidth();
    mHeight = getMeasuredHeight();
    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);
      if (child.getVisibility() != GONE) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childLeft = mPaddingLeft + lp.x;
        if (lp.width > 0)
          childLeft += (int) ((mWidth - lp.width) / 2.0);
        else
          childLeft += (int) ((mWidth - child.getMeasuredWidth()) / 2.0);
        int childTop = mPaddingTop + lp.y;
        if (lp.height > 0)
          childTop += (int) ((mHeight - lp.height) / 2.0);
        else
          childTop += (int) ((mHeight - child.getMeasuredHeight()) / 2.0);
        child.layout(childLeft, childTop, childLeft + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
      }
    }
  }

  @Override
  protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
    return p instanceof LayoutParams;
  }

  @Override
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
    return new LayoutParams(p);
  }

  public static class LayoutParams extends ViewGroup.LayoutParams {
    public int x;
    public int y;

    public LayoutParams(int width, int height, int x, int y) {
      super(width, height);
      this.x = x;
      this.y = y;
    }

    public LayoutParams(ViewGroup.LayoutParams source) {
      super(source);
    }
  }
}
