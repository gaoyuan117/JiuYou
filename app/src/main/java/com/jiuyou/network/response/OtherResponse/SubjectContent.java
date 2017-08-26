package com.jiuyou.network.response.OtherResponse;

import java.io.Serializable;

/**
 */
public class SubjectContent implements Serializable {

    SubjectPartContent subjectPartContent1;
    SubjectPartContent subjectPartContent2;
    SubjectPartContent subjectPartContent3;

    public SubjectPartContent getSubjectPartContent1() {
        return subjectPartContent1;
    }

    public void setSubjectPartContent1(SubjectPartContent subjectPartContent1) {
        this.subjectPartContent1 = subjectPartContent1;
    }

    public SubjectPartContent getSubjectPartContent2() {
        return subjectPartContent2;
    }

    public void setSubjectPartContent2(SubjectPartContent subjectPartContent2) {
        this.subjectPartContent2 = subjectPartContent2;
    }

    public SubjectPartContent getSubjectPartContent3() {
        return subjectPartContent3;
    }

    public void setSubjectPartContent3(SubjectPartContent subjectPartContent3) {
        this.subjectPartContent3 = subjectPartContent3;
    }

    public SubjectContent(SubjectPartContent subjectPartContent1, SubjectPartContent subjectPartContent3, SubjectPartContent subjectPartContent2) {
        this.subjectPartContent1 = subjectPartContent1;
        this.subjectPartContent3 = subjectPartContent3;
        this.subjectPartContent2 = subjectPartContent2;
    }

    public SubjectContent() {
    }
}
