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

data class Compliment(
    val code: Int,
    val list: List<ComplimentItem>,
    val msg: String,
    val succeed: Boolean
)

data class ComplimentItem(
    val complimentMarkContent: String,
    val complimentMarkId: Int,
    val complimentMarkName: String
)
