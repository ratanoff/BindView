package ru.ratanov.bindview.validator

import android.app.Activity
import android.widget.EditText
import ru.pay.bisys.centralkass.entity.provider.mask.MaskWatcher
import ru.ratanov.bindview.annotation.CardNumber
import java.lang.reflect.Field
import java.lang.*

class Validator {

    fun init(activity: Activity) {
        val fields = activity.javaClass.declaredFields
        checkSingle(fields)
        fields
                .filter { it.isAnnotationPresent(CardNumber::class.java) }
                .forEach {
                    val input: EditText = it.get(activity) as EditText
                    input.addTextChangedListener(MaskWatcher("000.00.0000"))
                }
    }

    private fun checkSingle(fields: Array<Field>) {
        val annotations = fields.map { it.annotations.size }


        if (annotations.size != 2) {
            throw IllegalArgumentException("You can annotate ONLY ONE field with <@CardNumber>")
        }
    }
}