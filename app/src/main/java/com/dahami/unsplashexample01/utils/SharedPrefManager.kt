package com.dahami.unsplashexample01.utils

import android.content.Context
import android.util.Log
import com.dahami.unsplashexample01.App
import com.dahami.unsplashexample01.model.SearchData
import com.dahami.unsplashexample01.utils.Constants.TAG
import com.google.gson.Gson

object SharedPrefManager {
    private const val SHARED_SEARCH_HISTORY = "shared_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"

    private const val SHARED_SEARCH_HISTORY_MODE = "shared_search_history_mode"
    private const val KEY_SEARCH_HISTORY_MODE = "key_search_history_mode"
    
    // 검색어 저장모드 설정하기
    fun setSearchHistoryMode(isActivated: Boolean) {
        Log.d(TAG, "SharedPrefManager - setSearchHistoryMode() called / isActivated : $isActivated")

        // Shared 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY_MODE, Context.MODE_PRIVATE)

        // Shared Editor 가져오기
        val editor = shared.edit()

        editor.putBoolean(KEY_SEARCH_HISTORY_MODE, isActivated)
        editor.apply()
    }

    // 검색어 저장 모드 확인하기
    fun checkSearchHistoryMode() : Boolean {
        // Shared 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY_MODE, Context.MODE_PRIVATE)
        val isSearchHistoryMode = shared.getBoolean(KEY_SEARCH_HISTORY_MODE, false)

        return isSearchHistoryMode
    }

    // 검색목록을 저장
    fun storeSearchHistoryList(searchHistoryList: MutableList<SearchData>) {
        Log.d(TAG, "SharedPrefManager - storeSearchHistoryList() called")

        // 매개변수로 들어온 배열을 -> 문자열로 변환
        val searchHistoryListString: String = Gson().toJson(searchHistoryList)
        Log.d(TAG, "SharedPrefManager - searchHistoryListString : $searchHistoryListString")

        // Shared 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        // Shared Editor 가져오기
        val editor = shared.edit()

        editor.putString(KEY_SEARCH_HISTORY, searchHistoryListString)
        editor.apply()
    }

    // 검색목록 가져오기
    fun getSearchHistoryList() : MutableList<SearchData> {
        // Shared 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)
        val storedSearchHistoryListString = shared.getString(KEY_SEARCH_HISTORY, "")!!

        var storeSearchHistoryList = ArrayList<SearchData>()

        // 검색 목록 값이 있다면
        if(storedSearchHistoryListString.isNotEmpty()) {

            // 저장된 운자열 -> 객체 배열로
            storeSearchHistoryList = Gson().
                                    fromJson(storedSearchHistoryListString, Array<SearchData>::class.java)
                                    .toMutableList() as ArrayList<SearchData>
        }

        return storeSearchHistoryList
    }
    
    // 검색목록 지우기
    fun clearSearchHistoryList() {
        Log.d(TAG, "SharedPrefManager - clearSearchHistoryList() called ")

        // Shared 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        // Shared Editor 가져오기
        val editor = shared.edit()
        editor.clear() // 해당 데이터 지우기 (초기화)
        editor.apply() // 변경사항 적용
    }
}