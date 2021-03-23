package com.high5ive.android.moira.data.retrofit

data class HashTags(
    val code: Int,
    val list: List<HashTagItem>,
    val msg: String,
    val succeed: Boolean
)

data class HashTagItem(
    val hashtagId: Int,
    val hashtagName: String
)