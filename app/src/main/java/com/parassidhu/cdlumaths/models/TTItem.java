package com.parassidhu.cdlumaths.models;

public class TTItem {
    private String teacherName;
    private String subName;
    private String now;
    private String time;

    public String getNow() {
        return now;
    }

    public void setNow(String now) { this.now = now; }

    public String getTime() {
        return time;
    }

    public TTItem(String teacherName, String subName, String now, String time) {
        this.teacherName = teacherName;
        this.subName = subName;
        this.now = now;
        this.time = time;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getSubName() {
        return subName;
    }
}
