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

/**
 * 搜索结果
 */
public class SearchResultInfo extends AbstractResponse implements Serializable {

    @ParamName("id")
    private String id;
    @ParamName("object_id")
    private String object_id;
    @ParamName("first_catalog")
    private String first_catalog;
    @ParamName("second_catalog")
    private String second_catalog;
    @ParamName("title")
    private String title;
    @ParamName("video_id")
    private String video_id;
    @ParamName("pic_url")
    private String pic_url;
    @ParamName("created")
    private String created;
    @ParamName("status")
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getFirst_catalog() {
        return first_catalog;
    }

    public void setFirst_catalog(String first_catalog) {
        this.first_catalog = first_catalog;
    }

    public String getSecond_catalog() {
        return second_catalog;
    }

    public void setSecond_catalog(String second_catalog) {
        this.second_catalog = second_catalog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SearchResultInfo(String id, String object_id, String first_catalog, String second_catalog, String title, String video_id, String pic_url, String created, int status) {
        this.id = id;
        this.object_id = object_id;
        this.first_catalog = first_catalog;
        this.second_catalog = second_catalog;
        this.title = title;
        this.video_id = video_id;
        this.pic_url = pic_url;
        this.created = created;
        this.status = status;
    }
}
