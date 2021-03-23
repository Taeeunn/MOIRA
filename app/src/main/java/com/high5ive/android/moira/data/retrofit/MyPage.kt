package com.high5ive.android.moira.data.retrofit

data class MyPage(
    val code: Int,
    val data: MyPageData,
    val msg: String,
    val succeed: Boolean
)

data class MyPageData(
    val appliedPostCount: Int,
    val likedPostCount: Int,
    val nickname: String,
    val positionName: String,
    val profileImageUrl: String,
    val shortIntroduction: String,
    val writtenPostCount: Int
)