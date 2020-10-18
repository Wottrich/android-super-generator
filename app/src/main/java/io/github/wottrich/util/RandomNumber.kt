package io.github.wottrich.util

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 18/10/2020
 *
 * Copyright © 2020 SuperGenerator. All rights reserved.
 *
 */
 
object RandomNumber {

    fun random(start: Int, end: Int): Int {
        return (start..end).random()
    }

}