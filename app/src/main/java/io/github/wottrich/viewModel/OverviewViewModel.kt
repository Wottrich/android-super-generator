package io.github.wottrich.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.wottrich.models.ControlNumber

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 16/10/2020
 *
 * Copyright © 2020 SuperGenerator. All rights reserved.
 *
 */
 
class OverviewViewModel : ViewModel() {

    val validMinMaxNumbers: Boolean
        get() = minNumberOrZero < maxNumberOrZero

    val minNumber = MutableLiveData<String>("1")
    val maxNumber = MutableLiveData<String>("60")

    private val _finalNumbers = MutableLiveData<String>()
    val finalNumbers: LiveData<String> = _finalNumbers

    private val maxNumberGenerated = 6
    private val numbers = mutableListOf<ControlNumber>()

    private val minNumberOrZero
        get() = minNumber.value?.toInt() ?: 0

    private val maxNumberOrZero
        get() = maxNumber.value?.toInt() ?: 0

    fun generateNumbers() {
        clearNumbers()
        managerNumbers()
        _finalNumbers.value = formatNumbers()
    }

    private fun clearNumbers() {
        numbers.clear()
    }

    private fun formatNumbers(): String {
        val stringAppend = StringBuffer()
        var first = true
        numbers.forEach {
            if (first) {
                first = false
                stringAppend.append(it.number)
            } else {
                stringAppend.append("-").append(it.number)
            }
        }
        return stringAppend.toString()
    }

    private fun managerNumbers() {
        val numberGenerated = getNumberNotContainsInList()
        numbers.add(ControlNumber(number = numberGenerated))
        if (numbers.size != maxNumberGenerated) {
            managerNumbers()
        }
    }

    private fun getNumberNotContainsInList(): Int {
        val generatedNumber = random(minNumberOrZero, maxNumberOrZero)
        return if (numbers.map { it.number }.contains(generatedNumber)) {
            getNumberNotContainsInList()
        } else {
            generatedNumber
        }
    }

    private fun random(start: Int, end: Int): Int {
        return (start..end).random()
    }

}