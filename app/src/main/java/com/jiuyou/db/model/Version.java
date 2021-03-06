package com.jiuyou.db.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table VERSION.
 */
public class Version {

    private Long id;
    private String versionInfor;
    private String versioNote;

    public Version() {
    }

    public Version(Long id) {
        this.id = id;
    }

    public Version(Long id, String versionInfor, String versioNote) {
        this.id = id;
        this.versionInfor = versionInfor;
        this.versioNote = versioNote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionInfor() {
        return versionInfor;
    }

    public void setVersionInfor(String versionInfor) {
        this.versionInfor = versionInfor;
    }

    public String getVersioNote() {
        return versioNote;
    }

    public void setVersioNote(String versioNote) {
        this.versioNote = versioNote;
    }

}
