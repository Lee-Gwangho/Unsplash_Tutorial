package com.dahami.unsplashexample01.retrofit

import android.util.Log
import com.dahami.unsplashexample01.model.Photo
import com.dahami.unsplashexample01.utils.API
import com.dahami.unsplashexample01.utils.Constants.TAG
import com.dahami.unsplashexample01.utils.RESPONSE_STATUS
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat


class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    // Retrofit Interface 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // 사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATUS, ArrayList<Photo>?) -> Unit){

        // unwrapping
        var term = searchTerm.let {
            it
        }?: ""
//        var term = searchTerm ?: ""

        val call = iRetrofit?.searchPhotos(searchTerm = term).let {
            it
        }?: return
//        val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                completion(RESPONSE_STATUS.FAIL, null)
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                Log.d(TAG, "RetrofitManager - onResponse() called / response : ${response.body()}")

                when(response.code()) {
                    200 -> {

                        response.body()?.let {
                            var parsePhotoDataArray = ArrayList<Photo>()

                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("results")
                            val total = body.get("total").asInt

//                            Log.d(TAG, "RetrofitManager - onResponse() called / total : ${total}")

                            // 데이터가 없으면 no_content 로 보낸다.
                            if(total == 0) {
                                completion(RESPONSE_STATUS.NO_CONTENT, null)
                            } else { // 데이터가 있다면
                                results.forEach { resultItem ->
                                    val resultItemObject = resultItem.asJsonObject
                                    val user = resultItemObject.get("user").asJsonObject
                                    val username: String = user.get("username").asString

                                    val likesCount = resultItemObject.get("likes").asInt
                                    val thumbnailLink = resultItemObject.get("urls").asJsonObject.get("thumb").asString
                                    var createdAt = resultItemObject.get("created_at").asString

                                    // 날짜 형식변경
                                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                    val formatter = SimpleDateFormat("yyyy년\n MM월 dd일")

                                    val outputDateString = formatter.format(parser.parse(createdAt))

    //                                Log.d(TAG, "outputDateString : $outputDateString")

                                    val photoItem = Photo(
                                                    author = username,
                                                    likesCount = likesCount,
                                                    thumbnail = thumbnailLink,
                                                    createdAt = outputDateString
                                                )

                                    parsePhotoDataArray.add(photoItem)

                                }
                                completion(RESPONSE_STATUS.OKAY, parsePhotoDataArray)
                            }
                        }

                    }
                }

            }

        })
    }
}