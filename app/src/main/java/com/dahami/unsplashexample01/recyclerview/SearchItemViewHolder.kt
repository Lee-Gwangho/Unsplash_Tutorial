package com.dahami.unsplashexample01.recyclerview

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dahami.unsplashexample01.model.SearchData
import com.dahami.unsplashexample01.utils.Constants.TAG
import kotlinx.android.synthetic.main.layout_search_item.view.*
import java.text.SimpleDateFormat

/**
 * Created by HOYADEV on 2021-03-09.
 * @desc
 */
class SearchItemViewHolder(itemView: View)
                        : RecyclerView.ViewHolder(itemView),
                         View.OnClickListener{

    // 뷰 가져오기
    private val searchTermTextView = itemView.search_term_text
    private val whenSearchedTextView = itemView.when_searched_text
    private val deleteSearchBtn = itemView.delete_search_button
    private val constraintSearchItem = itemView.constraint_search_item

    // 생성자 메서드
    init {
        // 리스너 연결 (꼭 해주어야 한다) 만약, 설정하지 않을 시, onClick() 함수가 실행되지 않는다.
        deleteSearchBtn.setOnClickListener(this)
        constraintSearchItem.setOnClickListener(this)
    }

    // 데이터와 뷰를 묶어준다.
    fun bindWithView(searchItem: SearchData) {
        Log.d(TAG, "SearchItemViewHolder - bindWithView() called")

//        val formatter = SimpleDateFormat("HH:mm:ss")
//        val outputTimeString = formatter.format(searchDataItem.timestamp)
//        searchTimeText.text = outputTimeString

        whenSearchedTextView.text = searchItem.timestamp
        searchTermTextView.text = searchItem.term
    }

    override fun onClick(view: View?) {
        Log.d(TAG, "SearchItemViewHolder - onClick() called ")

        when(view) {
            deleteSearchBtn -> {
                Log.d(TAG, "SearchItemViewHolder - 검색삭제 버튼 클릭")
            }
            constraintSearchItem -> {
                Log.d(TAG, "SearchItemViewHolder - 검색어 아이템 클릭")
           }
        }
    }

}