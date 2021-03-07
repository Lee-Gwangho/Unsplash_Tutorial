package com.dahami.unsplashexample01

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.dahami.unsplashexample01.model.Photo
import com.dahami.unsplashexample01.recyclerview.PhotoGridRecyclerViewAdapter
import com.dahami.unsplashexample01.utils.Constants.TAG
import kotlinx.android.synthetic.main.activity_photo_collection.*

class PhotoCollectionActivity: AppCompatActivity(),
                            SearchView.OnQueryTextListener,
                            CompoundButton.OnCheckedChangeListener,
                            View.OnClickListener
{

    // 데이터
    private var photoList = ArrayList<Photo>()

    // 어답터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter

    // 서치뷰
    private lateinit var mySearchView: SearchView

    // 서치뷰 에디트 텍스트
    private lateinit var mySearchViewEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)

        Log.d(TAG, "PhotoCollectionActivity - onCreate() called")

        val bundle = intent.getBundleExtra("array_bundle")
        var searchTerm = intent.getStringExtra("search_term")
        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        Log.d(TAG, "PhotoCollectionActivity - onCreate: / searchTerm : $searchTerm, photoList.count() : ${photoList.count()}")

        // 클릭리스너를 연결해주어야 한다.
        search_history_mode_switch.setOnCheckedChangeListener(this)
        clear_search_history_button.setOnClickListener(this)

        top_app_bar.title = searchTerm

        // Activity에서 어떤 ActionBar를 사용할지 설정한다.
        setSupportActionBar(top_app_bar)

        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)

        my_photo_recycler_view.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        my_photo_recycler_view.adapter = this.photoGridRecyclerViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionsMenu() called")

        var inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        this.mySearchView = menu?.findItem(R.id.search_menu_item)?.actionView as SearchView

        // 서치뷰에 다양한 속성을 지정 (apply)
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력해주세요."
            this.setOnQueryTextListener(this@PhotoCollectionActivity)
            this.setOnQueryTextFocusChangeListener { _, hasExpanded ->
                when(hasExpanded) {
                    true -> {
                        Log.d(TAG, "서치뷰 열림")
                        linear_search_history_view.visibility = View.VISIBLE
                    }
                    false -> {
                        Log.d(TAG, "서치뷰 닫힘")
                        linear_search_history_view.visibility = View.INVISIBLE
                    }
                }
            }
            // 서치뷰에서 에디트 덱스트를 가져온다.
            mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }

        this.mySearchViewEditText.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(12))
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)
        }

        (menu?.findItem(R.id.search_menu_item)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }

    // 서치뷰 검색어 입력 이벤트
    // 검색버튼 클릭되었을 때
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "onQueryTextSubmit() called / query : $query")

        if(!query.isNullOrEmpty()) {
            this.top_app_bar.title = query

            //TODO:: api 호출
            //TODO:: 검색어 저장
        }
//        this.mySearchView.setQuery("", false)
//        this.mySearchView.clearFocus()
        this.top_app_bar.collapseActionView()
        return true
    }

    // 서치뷰 검색어 변경 이벤트
    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "onQueryTextChange() called / newText : $newText")

        val userInputText = newText ?: ""

//        val userInputText = newText.let {
//            it
//        }?: ""

        if(userInputText.count() == 12) {
            Toast.makeText(this, "검색어는 12자 까지만 입력가능합니다", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
        when(switch) {
            search_history_mode_switch -> {
                if(isChecked == true) {
                    Log.d(TAG, "검색어 저장 기능 ON")
                } else {
                    Log.d(TAG, "검색어 저장 기능 OFF")
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view){
            clear_search_history_button -> {
                Log.d(TAG, "검색 기록 삭제버튼이 클릭되었다")
            }
        }
    }
}