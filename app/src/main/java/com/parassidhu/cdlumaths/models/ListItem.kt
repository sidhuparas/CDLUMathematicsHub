package com.parassidhu.cdlumaths.models

class ListItem(var subName: String, var content: String) {

    var param: String = ""

    constructor(subName: String, content: String, param: String) : this(subName, content) {
        this.param = param
    }
}
