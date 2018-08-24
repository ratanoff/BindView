package ru.pay.bisys.centralkass.entity.provider.mask

import android.text.Editable
import android.text.TextWatcher

class MaskWatcher(private var mask: String) : TextWatcher {

    private var isRunning = false
    private var isDeleting = false

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {

        mask = mask.replace("9", "0")

        val editableLength = editable.length

        if (editableLength == mask.length) return

        if (isRunning || isDeleting) {
            return
        }

        isRunning = true

        if (editableLength < mask.length) {

            if (mask[editableLength] != '0') {
                editable.append(mask[editableLength])
            } else if (mask[editableLength - 1] != '0') {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }

        }

        isRunning = false
    }


}
