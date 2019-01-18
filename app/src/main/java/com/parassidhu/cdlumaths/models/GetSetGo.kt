package com.parassidhu.cdlumaths.models


class GetSetGo(var key: String, var value: String) {
    lateinit var rollNo: String
        internal set

    constructor(key: String, value: String, rollNo: String) : this(key, value) {
        this.rollNo = rollNo
    }
}
