package com.parassidhu.cdlumaths.models;


public class GetSetGo {
    String key;
    String value;
    String rollno;

    public GetSetGo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public GetSetGo(String key, String value, String rollno) {
        this.key = key;
        this.value = value;
        this.rollno = rollno;
    }

    public String getRollno() { return rollno; }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
