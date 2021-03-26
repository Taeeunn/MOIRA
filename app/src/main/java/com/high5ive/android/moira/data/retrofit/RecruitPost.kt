package com.high5ive.android.moira.data.retrofit

data class RecruitPost(
    val code: Int,
    val list: List<RecruitPostItem>,
    val msg: String,
    val succeed: Boolean
)

data class RecruitPostItem(
    val hashtagList: List<Hashtag>,
    val hitCount: Int,
    val projectId: Int,
    val projectImageUrl: String,
    val projectTitle: String,
    val writtenTime: String
)

