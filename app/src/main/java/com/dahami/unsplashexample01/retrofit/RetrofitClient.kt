package com.dahami.unsplashexample01.retrofit

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.dahami.unsplashexample01.App
import com.dahami.unsplashexample01.utils.API
import retrofit2.Retrofit
import com.dahami.unsplashexample01.utils.Constants.TAG
import com.dahami.unsplashexample01.utils.isJsonArray
import com.dahami.unsplashexample01.utils.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


// Singleton (메모리를 하나만 사용)
object RetrofitClient {

    // RetorfitClient 선언
    private var retrofitClient: Retrofit? = null
//    private lateinit var retrofitClient: Retrofit

    // RetrofitClient 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")

        // okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
//                Log.d(TAG, "RetrofitClient - log() called / message: $message")

                when {
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))

                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))

                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }
            }
        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가
        client.addInterceptor(loggingInterceptor)

        // 기본 파라미터 인터셉터 추가
        val baseParameterInterceptor: Interceptor = (object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG, "intercept: ")
                // 오리지널 request
                val originalRequest: Request = chain.request()

                // 쿼리 파라미터 추가
                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
                Log.d(TAG, "addedUrl : $addedUrl")

                val finalRequest = originalRequest.newBuilder()
                                                .url(addedUrl)
                                                .method(originalRequest.method, originalRequest.body)
                                                .build()

                val response = chain.proceed(finalRequest)

                if(response.code != 200) {

                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(App.instance, "${response.code} 에러 입니다.", Toast.LENGTH_SHORT).show()
                    }

                }
                return response
            }

        })

        // 위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가
        client.addInterceptor(baseParameterInterceptor)


        // 커넥션 타임아웃
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)

        if(retrofitClient == null) {
            // Retrofit Builder를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                // 위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정한다.
                .client(client.build())
                .build()
        }

        return retrofitClient
    }

}