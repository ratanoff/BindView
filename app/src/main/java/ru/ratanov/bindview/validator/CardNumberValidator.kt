package ru.ratanov.bindview.validator

import android.view.View
import android.widget.EditText
import ru.pay.bisys.centralkass.entity.provider.mask.MaskWatcher

object CardNumberValidator {

    private val LENGTH = 19

    fun setup(input: EditText) {
        input.filters = arrayOf(android.text.InputFilter.LengthFilter(LENGTH))
        input.addTextChangedListener(MaskWatcher("0000 0000 0000 0000"))

        input.onFocusChangeListener = View.OnFocusChangeListener{view: View?, hasFocus: Boolean ->
            if (!hasFocus && input.length() != LENGTH) input.error = "Некорректный номер карты"
        }
    }

}