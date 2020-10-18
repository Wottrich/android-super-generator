package io.github.wottrich.extensions

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 17/10/2020
 *
 * Copyright © 2020 SuperGenerator. All rights reserved.
 *
 */
 
fun <T> MutableList<T>.appendItems(separator: String = "", value: (T) -> String): String {
    val stringBuffer = StringBuffer()
    var first = true
    forEach {
        if (first) {
            first = false
            stringBuffer.append(value(it))
        } else {
            stringBuffer.append(separator).append(value(it))
        }
    }
    return stringBuffer.toString()
}