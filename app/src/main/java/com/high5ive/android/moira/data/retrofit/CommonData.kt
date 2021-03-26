package com.high5ive.android.moira.data.retrofit

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-26
 */

data class Hashtags(
    val code: Int,
    val list: List<Hashtag>,
    val msg: String,
    val succeed: Boolean
)

data class ResponseData(
    val code: Int,
    val msg: String,
    val succeed: Boolean
)

data class Hashtag(
    val hashtagId: Int,
    val hashtagName: String
)


data class UserReviewAddRequestDto(
    val complimentMarkIdList: List<Int>,
    val mannerPoint: Int,
    val reviewContent: String,
    val userProjectId: Int
)

data class TeamMember(
    val code: Int,
    val list: List<TeamMemberItem>,
    val msg: String,
    val succeed: Boolean
)

data class TeamMemberItem(
    val completedReview: Boolean,
    val nickname: String,
    val positionName: String,
    val userId: Int,
    val userProfileImage: String,
    val userProjectId: Int,
    val userProjectRole: String
)