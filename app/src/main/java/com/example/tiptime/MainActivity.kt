package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.tiptime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: TipViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculate() }
        viewModel.formattedTip.observe(this) { newFormattedTip ->
            binding.tipResult.text = getString(R.string.tip_amount, newFormattedTip)
        }

        //if there is an invalid input in the textField
        viewModel.isServiceAmountInvalid.observe(this) {
            if (viewModel.isServiceAmountInvalid.value == true) {
                binding.costOfService.isErrorEnabled = true
                binding.costOfService.error = "Invalid amount!"
                binding.tipResult.isVisible = false
            } else {
                binding.costOfService.isErrorEnabled = false
                binding.tipResult.isVisible = true
            }
        }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }

    }

    private fun calculate() {
        val inputServiceAmount = binding.costOfServiceEditText.text.toString()
        val tipPercent = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 20
            R.id.option_eighteen_percent -> 18
            else -> 15
        }
        if (binding.roundUpSwitch.isChecked) {
            viewModel.calculateTip(inputServiceAmount.toDoubleOrNull(), tipPercent, true)
        } else {
            viewModel.calculateTip(inputServiceAmount.toDoubleOrNull(), tipPercent, false)
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}