package org.hyperskill.calculator.tip

import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.edit_text)
        val seekBar = findViewById<SeekBar>(R.id.seek_bar)
        val billValueTv = findViewById<TextView>(R.id.bill_value_tv)
        val tipPercentTv = findViewById<TextView>(R.id.tip_percent_tv)
        val tipAmountTv = findViewById<TextView>(R.id.tip_amount_tv)

        editText.doAfterTextChanged {
            updateValues(editText, seekBar, billValueTv, tipPercentTv, tipAmountTv)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateValues(editText, seekBar!!, billValueTv, tipPercentTv, tipAmountTv)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateValues(
        editText: EditText,
        seekBar: SeekBar,
        billValueTv: TextView,
        tipPercentTv: TextView,
        tipAmountTv: TextView,
    ) {
        val editTextToBigDecimal = editText.text.toString().toBigDecimalOrNull()
        val percentValue = "Tip: ${seekBar.progress}%"
        val billValue = "Bill Value: $${editTextToBigDecimal?.setScale(2)}"
        val tips =
            editTextToBigDecimal
                ?.multiply(seekBar.progress.toBigDecimal())
                ?.divide(BigDecimal(100))
                ?.setScale(2, RoundingMode.HALF_UP)
        val tipValue = "Tip Amount: $$tips"

        if (editTextToBigDecimal != null && editTextToBigDecimal > BigDecimal.ZERO) {
            tipPercentTv.text = percentValue
            billValueTv.text = billValue
            tipAmountTv.text = tipValue
        } else {
            tipPercentTv.text = ""
            billValueTv.text = ""
            tipAmountTv.text = ""
        }
    }
}

