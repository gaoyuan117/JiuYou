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

import android.view.View;

/**
 * Interface that allows PullToRefreshBase to hijack the call to
 * AdapterView.setEmptyView()
 * 
 * @author chris
 */
public interface EmptyViewMethodAccessor {

	/**
	 * Calls upto AdapterView.setEmptyView()
	 * 
	 * @param emptyView - to set as Empty View
	 */
	public void setEmptyViewInternal(View emptyView);

	/**
	 * Should call PullToRefreshBase.setEmptyView() which will then
	 * automatically call through to setEmptyViewInternal()
	 * 
	 * @param emptyView - to set as Empty View
	 */
	public void setEmptyView(View emptyView);

}
