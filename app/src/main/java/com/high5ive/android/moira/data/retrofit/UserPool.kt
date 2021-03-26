package com.high5ive.android.moira.data.retrofit

data class UserPool(
    val code: Int,
    val list: List<UserPoolItem>,
    val msg: String,
    val succeed: Boolean
)

data class UserPoolItem(
    val hashtagList: List<Hashtag>,
    val nickname: String,
    val positionName: String,
    val profileImage: String,
    val shortIntroduction: String,
    val userPoolId: Int
)