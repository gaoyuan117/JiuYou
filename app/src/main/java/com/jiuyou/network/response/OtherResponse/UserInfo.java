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

import com.google.gson.Gson;
import com.jiuyou.network.annotate.ParamName;
import com.jiuyou.util.GsonUtils;

/**
 * 用户信息
 */
public class UserInfo implements Cloneable {

    @ParamName("id")
    private String mId;
    
   
    
//    @ParamName("name")
//    private String name;
    
    

	@ParamName("email")
    private String mEmail;

	@ParamName("sex")
    private Gender mGender;

    @ParamName("face")
    private String mAvatar;

    @ParamName("phone")
    private String mPhone;

    @ParamName("name")
    private String mName;

    // FIXME 应该用枚举
    @ParamName("grade")
    private String mGrade;

    @ParamName("is_oper")
    private int mBusinessManFlag;

    @ParamName("rank_id")
    private String mRankId;

	@ParamName("product_category")
    private String product_category;
	
	@ParamName("app_if_ip")
	private String app_if_ip;
	
	

	@ParamName("app_if_url")
	private String app_if_url;
	
	@ParamName("password")
	private String password;
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
//	 public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProduct_category() {
			return product_category;
		}

	 public void setProduct_category(String product_category) {
			this.product_category = product_category;
		}
	
    public String getId() {
        return mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public Gender getGender() {
        return mGender;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getGrade() {
        return mGrade;
    }

    public boolean getBusinessManFlag() {
        return mBusinessManFlag == 1;
    }

    public String getRankId() {
        return mRankId;
    }

    @Override
    public String toString() {
        Gson gson = GsonUtils.newInstance();
        return gson.toJson(this);
    }

    public void setId(String id) {
        mId = id;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setGender(Gender gender) {
        mGender = gender;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public void setGrade(String grade) {
        mGrade = grade;
    }
	
	public String getApp_if_ip() {
		return app_if_ip;
	}

	public void setApp_if_ip(String app_if_ip) {
		this.app_if_ip = app_if_ip;
	}

	public String getApp_if_url() {
		return app_if_url;
	}

	public void setApp_if_url(String app_if_url) {
		this.app_if_url = app_if_url;
	}
	
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public UserInfo getClone() {
        try {
            return (UserInfo) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || UserInfo.class != o.getClass()) {
            return false;
        }

        UserInfo userInfo = (UserInfo) o;

        if (mAvatar != null ? !mAvatar.equals(userInfo.mAvatar) : userInfo.mAvatar != null) {
            return false;
        }
        if (mEmail != null ? !mEmail.equals(userInfo.mEmail) : userInfo.mEmail != null) {
            return false;
        }
        if (mGender != userInfo.mGender) {
            return false;
        }
        if (mName != null ? !mName.equals(userInfo.mName)
                : userInfo.mName != null) {
            return false;
        }
        if (mPhone != null ? !mPhone.equals(userInfo.mPhone) : userInfo.mPhone != null) {
            return false;
        }
        if(product_category!=null?product_category.equals(userInfo.product_category):userInfo.product_category!=null){
        	return false;
        }
        if(app_if_ip!=null?app_if_ip.equals(userInfo.app_if_ip):userInfo.app_if_ip!=null){
        	return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mEmail != null ? mEmail.hashCode() : 0);
        result = 31 * result + (mGender != null ? mGender.hashCode() : 0);
        result = 31 * result + (mAvatar != null ? mAvatar.hashCode() : 0);
        result = 31 * result + (mPhone != null ? mPhone.hashCode() : 0);
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mGrade != null ? mGrade.hashCode() : 0);
        result = 31 * result + (mRankId != null ? mRankId.hashCode() : 0);
        result = 31 * result + (product_category != null ? product_category.hashCode() : 0);
        result = 31 * result + (app_if_ip!=null?app_if_ip.hashCode():0);
        return result;
    }
}
