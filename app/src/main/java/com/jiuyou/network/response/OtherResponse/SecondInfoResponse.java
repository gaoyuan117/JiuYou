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

package com.jiuyou.network.response.OtherResponse;

import com.jiuyou.network.annotate.ParamName;
import com.jiuyou.network.response.AbstractResponse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class SecondInfoResponse extends AbstractResponse implements Serializable {

    @ParamName("result")
    private ArrayList<SecondSorceInfo> result;

    public ArrayList<SecondSorceInfo> getResult() {
        return result;
    }

    public void setResult(ArrayList<SecondSorceInfo> result) {
        this.result = result;
    }

    public SecondInfoResponse(ArrayList<SecondSorceInfo> result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecondInfoResponse that = (SecondInfoResponse) o;

        return result != null ? result.equals(that.result) : that.result == null;

    }

    @Override
    public int hashCode() {
        return result != null ? result.hashCode() : 0;
    }
}
