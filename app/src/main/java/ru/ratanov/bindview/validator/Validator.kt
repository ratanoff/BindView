package ru.ratanov.bindview.validator

import android.app.Activity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import ru.pay.bisys.centralkass.entity.provider.mask.MaskWatcher
import ru.ratanov.bindview.annotation.*
import java.lang.reflect.Field
import java.lang.*

class Validator {

    fun init(activity: Activity) {
        val fields = activity.javaClass.declaredFields
        checkSingle(fields)
        fields.forEach {field ->
            field.isAccessible = true
            if (field.isAnnotationPresent(CardNumber::class.java)) {
                CardNumberValidator.setup(field.get(activity) as EditText)
            }
            if (field.isAnnotationPresent(ExpDate::class.java)) {
                ExpDateValidator.setup(field.get(activity) as EditText)
            }
            if (field.isAnnotationPresent(CVV::class.java)) {
                CvvValidator.setup(field.get(activity) as EditText)
            }
            if (field.isAnnotationPresent(CardHolder::class.java)) {
                CardHolderValidator.setup(field.get(activity) as EditText)
            }
            if (field.isAnnotationPresent(PayButtton::class.java)) {
                PayButtonBuilder.setup(field.get(activity) as Button, fields, activity)
            }
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