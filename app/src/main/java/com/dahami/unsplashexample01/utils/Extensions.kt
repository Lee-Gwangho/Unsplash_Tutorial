package com.dahami.unsplashexample01.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

// 문자열이 JSON 형태인지
fun String?.isJsonObject(): Boolean {
    if(this?.startsWith("{") == true && this.endsWith("}")) {
        return true
    }else {
        return false
    }

//    return this?.startsWith("{") == true && this.endsWith("}")
}

// 문자열이 JSON 배열인지
fun String?.isJsonArray(): Boolean {
    if(this?.startsWith("[") == true && this.endsWith("]")) {
        return true
    }else {
        return false
    }
}

// 날짜 포맷
fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("HH:mm:ss")
    return format.format(this)
}
// 초간단 버전
//fun String?.isJsonObjectSimple(): Boolean = this?.startsWith("{") == true && this.endsWith("}")

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