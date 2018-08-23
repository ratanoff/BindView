package ru.ratanov.bindview.validator

import android.app.Activity
import android.util.Log
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
        fields.forEach {field ->
            field.annotations.forEachIndexed { index, annotation ->
                Log.d("Annotation:", "$index. ${annotation.annotationClass.simpleName}")
            }
        }
    }
}