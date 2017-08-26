package com.jiuyou.network.response.OtherResponse;

import com.jiuyou.network.annotate.ParamName;

import java.io.Serializable;

/**
 */
public class CollectionContent implements Serializable {
    @ParamName("isChecked")
    boolean isChecked=false;
    @ParamName("collectionPic")
    String collectionPic;
    @ParamName("subjectName")
    String subjectName;
    @ParamName("oneLevel")
    String oneLevel;
    @ParamName("twoLevel")
    String twoLevel;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCollectionPic() {
        return collectionPic;
    }

    public void setCollectionPic(String collectionPic) {
        this.collectionPic = collectionPic;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getOneLevel() {
        return oneLevel;
    }

    public void setOneLevel(String oneLevel) {
        this.oneLevel = oneLevel;
    }

    public String getTwoLevel() {
        return twoLevel;
    }

    public void setTwoLevel(String twoLevel) {
        this.twoLevel = twoLevel;
    }

    public CollectionContent(boolean isChecked, String collectionPic, String subjectName, String oneLevel, String twoLevel) {
        this.isChecked = isChecked;
        this.collectionPic = collectionPic;
        this.subjectName = subjectName;
        this.oneLevel = oneLevel;
        this.twoLevel = twoLevel;
    }

    @Override
    public String toString() {
        return "CollectionContent{" +
                "isChecked=" + isChecked +
                ", collectionPic='" + collectionPic + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", oneLevel='" + oneLevel + '\'' +
                ", twoLevel='" + twoLevel + '\'' +
                '}';
    }
}
