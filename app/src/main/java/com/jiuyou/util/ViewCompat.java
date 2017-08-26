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
package com.jiuyou.util;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;

@SuppressWarnings("deprecation")
public class ViewCompat {

	public static void postOnAnimation(View view, Runnable runnable) {
		if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
			SDK16.postOnAnimation(view, runnable);
		} else {
			view.postDelayed(runnable, 16);
		}
	}

	public static void setBackground(View view, Drawable background) {
		if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
			SDK16.setBackground(view, background);
		} else {
			view.setBackgroundDrawable(background);
		}
	}

	public static void setLayerType(View view, int layerType) {
		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			SDK11.setLayerType(view, layerType);
		}
	}

	@TargetApi(11)
	static class SDK11 {

		public static void setLayerType(View view, int layerType) {
			view.setLayerType(layerType, null);
		}
	}

	@TargetApi(16)
	static class SDK16 {

		public static void postOnAnimation(View view, Runnable runnable) {
			view.postOnAnimation(runnable);
		}

		public static void setBackground(View view, Drawable background) {
			view.setBackground(background);
		}

	}

}
