package com.jiuyou.network.response.OtherResponse;

import com.jiuyou.network.annotate.ParamName;

import java.io.Serializable;

/**
 */
public class SubjectPartContent implements Serializable {
    @ParamName("subjectPic")
    String subjectPic;
    @ParamName("subjectName")
    String subjectName;
    @ParamName("subjectNum")
    String subjectNum;

    public String getSubjectPic() {
        return subjectPic;
    }

    public void setSubjectPic(String subjectPic) {
        this.subjectPic = subjectPic;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(String subjectNum) {
        this.subjectNum = subjectNum;
    }

    public SubjectPartContent(String subjectPic, String subjectName, String subjectNum) {
        this.subjectPic = subjectPic;
        this.subjectName = subjectName;
        this.subjectNum = subjectNum;
    }

    public SubjectPartContent() {
    }
}
