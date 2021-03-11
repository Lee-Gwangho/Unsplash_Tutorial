package com.dahami.unsplashexample01.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import com.dahami.unsplashexample01.utils.Constants.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.onStart
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


// EditText 변경을 flow로 받기
@ExperimentalCoroutinesApi
fun EditText.textChangesToFlow(): Flow<CharSequence?> {
    // flow 콜백 받기 (callbackFlow : 자체적으로 suspend 기능을 제공)
    return callbackFlow<CharSequence?> {
        val listener = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun afterTextChanged(s: Editable?) = Unit

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "onTextChanged() called / textChangesToFlow() 에 달려있는 텍스트 와쳐 / text : $text")
                offer(text) // 값 내보내기
            }
        }
        // 위에서 설정한 listener를 달아주기
        addTextChangedListener(listener)

        // 콜백이 사라질 때 실행됨
        awaitClose {
            Log.d(TAG, "textChangesToFlow() 에 awaitClose 실행")
            removeTextChangedListener(listener)
        }

    }.onStart {
        Log.d(TAG, "textChangesToFlow() / onStart 발동")
        // Rx에서 onNext와 동일
        // emit으로 이벤트를 전달
        emit(text)
    }
}