package com.parassidhu.cdlumaths.models

import java.io.File

class Pair(var f: File) : Comparable<Any> {
    var t: Long = 0

    init {
        t = f.lastModified()
    }

    override operator fun compareTo(other: Any): Int {
        val u = (other as Pair).t
        return if (t < u) -1 else if (t == u) 0 else 1
    }
}