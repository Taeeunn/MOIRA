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


data class HashtagResponseDto(
    val hashtagId: Int,
    val hashtagName: String
)


data class UserAwardResponseDto(
    val userAwardContent: String,
    val userAwardId: Int,
    val userAwardName: String
)

data class UserCareerResponseDto(
    val companyName: String,
    val endAt: String,
    val startAt: String,
    val task: String,
    val userCareerId: Int
)

data class UserLicenseResponseDto(
    val acquiredAt: String,
    val licenseName: String,
    val userLicenseId: Int
)

data class UserLinkResponseDto(
    val userLinkId: Int,
    val userLinkUrl: String
)

data class UserSchoolResponseDto(
    val endedAt: String,
    val majorName: String,
    val schoolName: String,
    val schoolStatus: String,
    val startedAt: String,
    val userSchoolId: Int
)


data class PositionCategory(
    val count: Int,
    val positionCategoryName: String
)


data class UserTag(
    val code: Int,
    val list: List<String>,
    val msg: String,
    val succeed: Boolean
)


data class ComplimentMarkWithCountDto(
    val complimentMarkContent: String,
    val complimentMarkCount: Int,
    val complimentMarkId: Int,
    val complimentMarkName: String
)


data class LinkItem(
    val linkUrl: String
)

data class AwardItem(
    val awardContent: String,
    val awardName: String
)

data class CertificateItem(
    val acquiredAt: String,
    val licenseName: String
)

data class CareerItem(
    val companyName: String,
    val endAt: String,
    val startAt: String,
    val task: String
)