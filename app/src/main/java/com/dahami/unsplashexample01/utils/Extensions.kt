package com.dahami.unsplashexample01.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

// EditText에 대한 확장 - 변경 이벤트를 미리 정의
// TODO: completion 사용 방법과 원리를 이해하기!
fun EditText.onMyTextChanged(completion: (Editable?) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {

        override fun afterTextChanged(editable: Editable?) {
            completion(editable)

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    })
}