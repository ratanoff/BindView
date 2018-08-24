package ru.ratanov.bindview.validator

import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ru.ratanov.bindview.MainActivity
import ru.ratanov.bindview.annotation.*
import java.lang.StringBuilder
import java.lang.reflect.Field

object PayButtonBuilder {

    fun setup(button: Button, fields: Array<Field>, activity: Activity) {
        button.setOnClickListener {
            val sb = StringBuilder()

            fields.forEach { field ->
                if (field.isAnnotationPresent(CardNumber::class.java)) {
                    val value = (field.get(activity) as EditText).text.toString()
                    sb.append("CardNumber = $value\n")
                }
                if (field.isAnnotationPresent(ExpDate::class.java)) {
                    val value = (field.get(activity) as EditText).text.toString()
                    sb.append("ExpDate = $value\n")
                }
                if (field.isAnnotationPresent(CVV::class.java)) {
                    val value = (field.get(activity) as EditText).text.toString()
                    sb.append("CVV = $value\n")
                }
                if (field.isAnnotationPresent(CardHolder::class.java)) {
                    val value = (field.get(activity) as EditText).text.toString()
                    sb.append("CardHolder = $value\n")
                }
            }

            Toast.makeText(activity, sb.toString(), Toast.LENGTH_SHORT).show()
        }
    }


}