package com.dahami.unsplashexample01

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dahami.unsplashexample01.model.Photo
import com.dahami.unsplashexample01.recyclerview.PhotoGridRecyclerViewAdapter
import com.dahami.unsplashexample01.utils.Constants.TAG
import kotlinx.android.synthetic.main.activity_photo_collection.*

class PhotoCollectionActivity: AppCompatActivity() {

    // 데이터
    private var photoList = ArrayList<Photo>()

    // 어답터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)

        Log.d(TAG, "PhotoCollectionActivity - onCreate() called")

        val bundle = intent.getBundleExtra("array_bundle")
        var searchTerm = intent.getStringExtra("search_term")
        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        Log.d(TAG, "PhotoCollectionActivity - onCreate: / searchTerm : $searchTerm, photoList.count() : ${photoList.count()}")

        top_app_bar.title = searchTerm

        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)

        my_photo_recycler_view.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        my_photo_recycler_view.adapter = this.photoGridRecyclerViewAdapter
    }
}