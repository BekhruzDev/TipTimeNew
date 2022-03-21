package com.example.tiptime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import kotlin.math.ceil

class TipViewModel : ViewModel() {
    private val _formattedTip = MutableLiveData<String>()
    val formattedTip: LiveData<String>
        get() = _formattedTip

    fun calculateTip(amount: Double, percent:Int, isRounded: Boolean) {
        var tipResult = amount * percent / 100
        if(isRounded){
            tipResult = ceil(tipResult)
            _formattedTip.value = NumberFormat.getCurrencyInstance().format(tipResult)
        }else
         _formattedTip.value = NumberFormat.getCurrencyInstance().format(tipResult)
    }
}