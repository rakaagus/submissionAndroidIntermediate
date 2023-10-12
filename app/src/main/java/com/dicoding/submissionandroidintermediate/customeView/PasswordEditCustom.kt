package com.dicoding.submissionandroidintermediate.customeView

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.dicoding.submissionandroidintermediate.R
import com.google.android.material.textfield.TextInputEditText

class PasswordEditCustom: TextInputEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val string = context.getString(R.string.text_error_custome_edittext)
        setRawInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    setError(string, null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }
}