package io.github.wottrich.models

import androidx.annotation.Keep

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 16/10/2020
 *
 * Copyright © 2020 SuperGenerator. All rights reserved.
 *
 */

@Keep
data class ControlNumber(
    val number: Int,
    val blocked: Boolean = false
)