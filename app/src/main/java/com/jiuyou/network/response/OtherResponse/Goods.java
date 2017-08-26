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

import android.os.Parcel;
import android.os.Parcelable;

import com.jiuyou.network.annotate.ParamName;

/**
 */
public class Goods implements Parcelable {

	//商品id
	@ParamName("id")
	private String mId;
	//商品名称
	@ParamName("name")
	private String mName;
	//商品图片
	@ParamName("pic")
	private String mPic;
	//商品数量
	@ParamName("num")
	private int mCount;
	//商品价格
	@ParamName("price")
	private String mPrice;
	//商品描述
	@ParamName("description")
	private String mDescription;
	//商品图片s
	@ParamName("pics")
	private String mPics;
	//商品类别
//	@ParamName("category")
//	private int mCategory;
	@ParamName("product_category")
	private int product_category;
	
	//商品重量
	@ParamName("weight")
	private String weight;
	
	//商品标签
	@ParamName("tags")
	private int tags;

	public int getTags() {
		return tags;
	}

	public void setTags(int tags) {
		this.tags = tags;
	}

	public String getWeight() {
		return weight;
	}

	@ParamName("discount")
	private String mDiscount;

	@ParamName("discount_price")
	private String mDiscountPrice;

	public String getId() {
		return mId;
	}
	
	public void setmId(String mId) {
		this.mId = mId;
	}


	public String getName() {
		return mName;
	}

	public String getPic() {
		return mPic;
	}

	public int getCount() {
		return mCount;
	}

	public void setCount(int count) {
		this.mCount = count;
	}

	public String getPrice() {
		return mPrice;
	}

	public String getDescription() {
		return mDescription;
	}

	public String getPics() {
		return mPics;
	}

	public int getProduct_category() {
		return product_category;
	}

	public void setProduct_category(int product_category) {
		this.product_category = product_category;
	}

	public String getDiscount() {
		return mDiscount;
	}

	public String getDiscountPrice() {
		return mDiscountPrice;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mId);
		dest.writeString(mName);
		dest.writeString(mPic);
		dest.writeInt(mCount);
		dest.writeString(mPrice);
		dest.writeString(mDescription);
		dest.writeString(mPics);
		dest.writeInt(product_category);
		dest.writeString(mDiscount);
		dest.writeString(mDiscountPrice);
		dest.writeInt(tags);
	}

	public static final Creator<Goods> CREATOR = new Creator<Goods>() {
		public Goods createFromParcel(Parcel in) {
			return new Goods(in);
		}

		public Goods[] newArray(int size) {
			return new Goods[size];
		}
	};

	private Goods(Parcel in) {
		mId = in.readString();
		mName = in.readString();
		mPic = in.readString();
		mCount = in.readInt();
		mPrice = in.readString();
		mDescription = in.readString();
		mPics = in.readString();
		product_category = in.readInt();
		mDiscount = in.readString();
		mDiscountPrice = in.readString();
		tags=in.readInt();
	}

	

	public Goods() {
		// TODO Auto-generated constructor stub
	}

	public void increaseCount() {
		mCount++;
	}

	public void decreaseCount() {
		mCount--;
		if (mCount < 1) {
			mCount = 1;
		}
	}
}
