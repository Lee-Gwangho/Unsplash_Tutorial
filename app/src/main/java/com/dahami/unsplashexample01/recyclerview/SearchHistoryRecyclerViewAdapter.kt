package com.dahami.unsplashexample01.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dahami.unsplashexample01.R
import com.dahami.unsplashexample01.model.SearchData

/**
 * Created by HOYADEV on 2021-03-09.
 * @desc
 */
class SearchHistoryRecyclerViewAdapter : RecyclerView.Adapter<SearchItemViewHolder>(){

    private var searchHistoryList = ArrayList<SearchData>();
    // 뷰홀더와 레아아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val searchItemViewHolder = SearchItemViewHolder(LayoutInflater
                                                    .from(parent.context)
                                                    .inflate(R.layout.layout_search_item, parent, false))

        return searchItemViewHolder
    }

    // 데이터를 뷰홀더에 넘겨준다.
    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bindWithView(this.searchHistoryList[position])
    }

    // 보여줄 목록 갯수
    override fun getItemCount(): Int {
        return this.searchHistoryList.size
    }

    // 외부에서 어답터에 데이터배열을 넣어준다.
    fun submitList(searchHistoryList: ArrayList<SearchData>) {
        this.searchHistoryList = searchHistoryList
    }

}