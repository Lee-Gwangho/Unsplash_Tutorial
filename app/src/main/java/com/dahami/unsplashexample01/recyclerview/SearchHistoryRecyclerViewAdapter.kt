package com.dahami.unsplashexample01.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dahami.unsplashexample01.utils.Constants.TAG
import com.dahami.unsplashexample01.R
import com.dahami.unsplashexample01.model.SearchData

/**
 * Created by HOYADEV on 2021-03-09.
 * @desc
 */

// 매개변수로 interface를 넘겨준다.
// 이유는, [검색어 항목] 정보를 Activity에 전달하기 위해서 Adapter에서 interface로 넘겨준다.
class SearchHistoryRecyclerViewAdapter(searchHistoryRecyclerViewInterface: ISearchHistoryRecyclerView)
                            : RecyclerView.Adapter<SearchItemViewHolder>(){

    private var searchHistoryList = ArrayList<SearchData>();
    private var iSearchHistoryRecyclerView: ISearchHistoryRecyclerView? = null

    // adapter를 통해서 interface를 가져왔다.
    //
    init {
        Log.d(TAG, "SearchHistoryRecyclerViewAdapter - init() called")
        this.iSearchHistoryRecyclerView = searchHistoryRecyclerViewInterface
    }

    // 뷰홀더와 레아아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val searchItemViewHolder = SearchItemViewHolder(LayoutInflater
                                                    .from(parent.context)
                                                    .inflate(R.layout.layout_search_item, parent, false)
            , this.iSearchHistoryRecyclerView!!
        )

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