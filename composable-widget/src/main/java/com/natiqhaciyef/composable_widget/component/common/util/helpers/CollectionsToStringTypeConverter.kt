package com.natiqhaciyef.composable_widget.component.common.util.helpers


fun main() {
    val map = mutableMapOf("start" to "21 June", "end" to "24 June")
    println(map.toSQLiteString())
    println(map.toSQLiteString().toSQLiteMutableMap())
}

fun <T> Map<T, T>.toSQLiteString(): String {
    val keys = this.keys
    var str = ""

    for (key in keys) {
        str += key
        str += ":"
        str += this[key]
        str += "#"
    }
    return str
}


fun String.toSQLiteMutableMap(): MutableMap<String, String> {
    val map = mutableMapOf<String, String>()
    val list = mutableListOf<String>()
    var vanishData = ""
    var value = ""
    var key = ""


    for (char in this) {
        if (char != '#')
            vanishData += char
        else {
            list.add(vanishData)
            vanishData = ""
        }
    }

    for (vd in list) {
        for (char in vd) {
            if (char != ':')
                value += char
            else {
                key = value
                value = ""
            }
        }
        map[key] = value
        key = ""
        value = ""
    }
    return map
}


fun <T> List<T>.toSQLiteString(): String {
    var str = ""
    for (element in this) {
        str += element
        str += "#"
    }
    return str
}


fun String.toSQLiteList(): List<String> {
    val list = mutableListOf<String>()
    var word = ""
    for (element in this) {
        if (element != '#')
            word += element
        else {
            list.add(word)
            word = ""
        }
    }

    return list
}


fun String.toSQLiteMutableListOfDouble(): MutableList<Double> {
    val list = mutableListOf<Double>()
    var word = ""
    for (element in this) {
        if (element != '#')
            word += element
        else {
            list.add(word.toDouble())
            word = ""
        }
    }

    return list
}