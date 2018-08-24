package ru.ratanov.bindview.validator

import android.text.InputType
import android.widget.EditText

object CardHolderValidator {

    fun setup(input: EditText) {
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
    }

}