package com.example.tiptime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import kotlin.math.ceil

class TipViewModel : ViewModel() {
    private val _isServiceAmountInvalid = MutableLiveData<Boolean>()
    val isServiceAmountInvalid: LiveData<Boolean>
        get() = _isServiceAmountInvalid
    private val _formattedTip = MutableLiveData<String>()
    val formattedTip: LiveData<String>
        get() = _formattedTip

    fun calculateTip(serviceAmount: Double?, percent: Int, isRounded: Boolean) {
        if (serviceAmount == null || serviceAmount == 0.0) {
            _isServiceAmountInvalid.value = true
        } else {
            _isServiceAmountInvalid.value = false
            var tipResult = serviceAmount * percent / 100
            if (isRounded) {
                tipResult = ceil(tipResult)
                _formattedTip.value = NumberFormat.getCurrencyInstance().format(tipResult)
            } else
                _formattedTip.value = NumberFormat.getCurrencyInstance().format(tipResult)
        }
    }
}