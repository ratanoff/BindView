package ru.ratanov.bindview.validator

import android.text.InputType
import android.view.View
import android.widget.EditText

object CvvValidator {

    private val LENGTH = 3

    fun setup(input: EditText) {
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        input.filters = arrayOf(android.text.InputFilter.LengthFilter(LENGTH))

        input.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus && input.length() != LENGTH) {
                input.error = "$LENGTH цифры"
            }
        }
    }

}