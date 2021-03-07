package com.dahami.unsplashexample01.utils

object Constants {
    // 전체적으로 사용될 변수를 별도로 정
    const val TAG: String = "로그"
}

enum class SEARCH_TYPE {
    PHOTO,
    USER
}

enum class RESPONSE_STATUS {
    OKAY,
    FAIL,
    NO_CONTENT
}

object API {
    const val BASE_URL: String = "https://api.unsplash.com/"
    const val CLIENT_ID: String = "URIsBgmiV5gPunjAXCjr-tHxLpA9nMAT0i0PQpCV0rw"
    const val SEARCH_PHOTO = "search/photos"
    const val SEARCH_USERS = "search/users"
}