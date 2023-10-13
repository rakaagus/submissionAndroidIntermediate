package com.dicoding.submissionandroidintermediate.customeView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.dicoding.submissionandroidintermediate.R
import com.google.android.material.textfield.TextInputEditText

class EmailEditCustom: TextInputEditText {
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
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val string = context.getString(R.string.text_email_invalid)
                if(!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    setError(string, null)
                }else {
                    error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
}