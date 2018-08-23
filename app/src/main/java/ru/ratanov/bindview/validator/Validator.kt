package ru.ratanov.bindview.validator

import android.app.Activity
import android.widget.EditText
import ru.pay.bisys.centralkass.entity.provider.mask.MaskWatcher
import ru.ratanov.bindview.annotation.CardNumber

class Validator {

        fun init(activity: Activity) {
            activity.javaClass.declaredFields
                    .filter { it.isAnnotationPresent(CardNumber::class.java) }
                    .forEach {
                        val input: EditText = it.get(activity) as EditText
                        input.addTextChangedListener(MaskWatcher("000.00.0000"))
                    }
        }
}