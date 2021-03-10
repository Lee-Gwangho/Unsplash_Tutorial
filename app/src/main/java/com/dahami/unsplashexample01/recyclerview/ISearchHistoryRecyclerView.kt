package com.dahami.unsplashexample01.recyclerview

/**
 * Created by HOYADEV on 2021-03-10.
 * @desc
 */
interface ISearchHistoryRecyclerView {

    // 검색 아이템 삭제 버튼 클릭
    fun onSearchItemDeleteClicked(position: Int)

    // 검색 버튼 클릭
    fun onSearchItemClicked(position: Int)

}