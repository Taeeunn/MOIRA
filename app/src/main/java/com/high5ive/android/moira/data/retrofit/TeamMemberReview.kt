package com.high5ive.android.moira.data.retrofit

data class TeamMemberReview(
    val code: Int,
    val data: TeamMemberReviewData,
    val msg: String,
    val succeed: Boolean
)

data class TeamMemberReviewData(
    val complimentMarkInfoList: List<ComplimentMarkInfo>,
    val mannerPoint: Int,
    val reviewContent: String,
    val userProjectId: Int
)

data class ComplimentMarkInfo(
    val complimentMarkId: Int,
    val complimentMarkName: String
)
