package io.github.wottrich.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.wottrich.R
import io.github.wottrich.extensions.appendItems
import io.github.wottrich.models.ControlNumber
import io.github.wottrich.util.DefaultNumbers
import io.github.wottrich.util.RandomNumber

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 16/10/2020
 *
 * Copyright © 2020 SuperGenerator. All rights reserved.
 *
 */

class OverviewViewModel : ViewModel() {

    //Public variables
    val isValidToGenerateNumbers: Boolean
        get() = minIsNotBiggerThenMax && isValidTotalNumberToGenerate

    //LiveData's
    val minNumber = MutableLiveData<String>(DefaultNumbers.defaultMinNumberGenerated.toString())
    val maxNumber = MutableLiveData<String>(DefaultNumbers.defaultMaxNumberGenerated.toString())
    val numberToGenerate = MutableLiveData<String>(DefaultNumbers.defaultNumberToGenerate.toString())

    private val _finalNumbers = MutableLiveData<String>()
    val finalNumbers: LiveData<String> = _finalNumbers
    private val _imLuckyNumbers = MutableLiveData<String>()
    val imLuckyNumbers: LiveData<String> = _imLuckyNumbers

    //Private variables
    private val numbers = mutableListOf<ControlNumber>()

    private val numbersBetweenMinAndMax
        get() = maxNumberOrZero - minNumberOrZero

    private val isValidTotalNumberToGenerate
        get() = numbersBetweenMinAndMax >= _numberToGenerate

    private val minIsNotBiggerThenMax
        get() = numbersBetweenMinAndMax > 0

    private val _numberToGenerate
        get() = (numberToGenerate.value ?: "0").toInt()

    private val minNumberOrZero
        get() = minNumber.value.takeIf { it != null && it.isNotEmpty() }?.toInt() ?: 0

    private val maxNumberOrZero
        get() = maxNumber.value.takeIf { it != null && it.isNotEmpty() }?.toInt() ?: 0

    //Public functions
    fun imLucky() {
        handleNumbers()
        _imLuckyNumbers.value = formatNumbers()
    }

    fun generateNumbers() {
        handleNumbers()
        _finalNumbers.value = formatNumbers()
    }

    @StringRes
    fun invalidGenerateNumberMessage(): Int {
        if (!minIsNotBiggerThenMax) {
            return R.string.error_min_is_bigger_then_max
        }

        if (!isValidTotalNumberToGenerate) {
            return R.string.error_number_to_generate_is_smaller_then_numbers_between_min_max
        }

        return R.string.error_unknown
    }

    //Private functions
    private fun handleNumbers() {
        clearNumbers()
        buildRandomNumbers()
    }

    private fun clearNumbers() {
        numbers.clear()
    }

    private fun formatNumbers() = numbers.appendItems("-") { it.number.toString() }

    private fun buildRandomNumbers() {
        numbers.addRandomNumberIfNotContains()
        if (numbers.size != _numberToGenerate) {
            buildRandomNumbers()
        }
    }

    private fun MutableList<ControlNumber>.addRandomNumberIfNotContains() {
        val generatedNumber = RandomNumber.random(minNumberOrZero, maxNumberOrZero)
        if (this.map { it.number }.contains(generatedNumber)) {
            addRandomNumberIfNotContains()
        } else {
            add(ControlNumber(number = generatedNumber))
        }
    }

}