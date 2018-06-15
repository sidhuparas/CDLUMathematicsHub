package com.parassidhu.cdlumaths.models;

public class TTItem {
    private String TeacherName;
    private String SubName;
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
        TeacherName = teacherName;
        SubName = subName;
        this.now = now;
        this.time = time;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public String getSubName() {
        return SubName;
    }
}
