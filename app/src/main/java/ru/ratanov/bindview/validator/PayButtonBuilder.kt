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
        val sb = StringBuilder()

        fields.forEach {field ->
            if (field.isAnnotationPresent(CardNumber::class.java)) {
                val value = ButtonBuilder.getClassAnnotationValue(MainActivity::class.java, EditText::class.java, "text")
                sb.append("CardNumber = $value")
            }
            if (field.isAnnotationPresent(ExpDate::class.java)) {
                val value = (field.get(activity) as EditText).text
                sb.append("ExpDate = $value\n")
            }
            if (field.isAnnotationPresent(CVV::class.java)) {
                val value = (field.get(activity) as EditText).text
                sb.append("CVV = $value\n")
            }
            if (field.isAnnotationPresent(CardHolder::class.java)) {
                val value = (field.get(activity) as EditText).text
                sb.append("CardHolder = $value\n")
            }
        }

        button.setOnClickListener {
            Toast.makeText(activity, sb.toString(), Toast.LENGTH_SHORT).show()
        }
    }


}