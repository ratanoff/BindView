package ru.ratanov.bindview.validator

import android.view.View
import android.widget.EditText
import ru.pay.bisys.centralkass.entity.provider.mask.MaskWatcher

object ExpDateValidator {

    private val LENGTH = 5

    fun setup(input: EditText) {
        input.filters = arrayOf(android.text.InputFilter.LengthFilter(LENGTH))
        input.addTextChangedListener(MaskWatcher("00/00"))
        input.onFocusChangeListener = View.OnFocusChangeListener{view: View?, hasFocus: Boolean ->
            if (!hasFocus && input.length() != LENGTH) input.error = "Некорректная дата"
        }
    }

}