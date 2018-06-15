package com.parassidhu.cdlumaths.models;

public class ListItem {

    private String subName;
    private String content;

    public ListItem(String subName, String content, String param) {
        this.subName = subName;
        this.content = content;
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    private String param;

    public ListItem(String subName, String content) {
        this.subName = subName;
        this.content = content;
    }

    public String getSubName() {
        return subName;
    }

    public String getContent() {
        return content;
    }
}
