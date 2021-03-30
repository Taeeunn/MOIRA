package com.high5ive.android.moira.data.retrofit

data class UserPool(
    val code: Int,
    val list: List<UserPoolItem>,
    val msg: String,
    val succeed: Boolean
)

data class UserPoolItem(
    val hashtagList: List<String>,
    var likedByUser: Boolean,
    val nickname: String,
    val positionName: String,
    val profileImage: String,
    val shortIntroduction: String,
    val userPoolId: Int
)


data class UserRegistration(
    val code: Int,
    val data: UserRegistrationData,
    val msg: String,
    val succeed: Boolean
)

data class UserRegistrationData(
    val userId: Int,
    val userPoolId: Int,
    val visible: Boolean
)


data class UserLike(
    val code: Int,
    val data: UserLikeData,
    val msg: String,
    val succeed: Boolean
)

data class UserLikeData(
    val liked: Boolean,
    val userId: Int,
    val userPoolId: Int
)


data class UserPoolDetailInfo(
    val code: Int,
    val data: UserPoolDetailInfoData,
    val msg: String,
    val succeed: Boolean
)

data class UserPoolDetailInfoData(
    val hashtagResponseDtoList: List<HashtagResponseDto>,
    val userAwardResponseDtoList: List<UserAwardResponseDto>,
    val userCareerResponseDtoList: List<UserCareerResponseDto>,
    val userId: Int,
    val userLicenseResponseDtoList: List<UserLicenseResponseDto>,
    val userLinkResponseDtoList: List<UserLinkResponseDto>,
    val userSchoolResponseDtoList: List<UserSchoolResponseDto>
)

data class UserPoolDetailReview(
    val code: Int,
    val data: UserPoolDetailReviewData,
    val msg: String,
    val succeed: Boolean
)

data class UserPoolDetailReviewData(
    val avgMannerPoint: Float,
    val complimentMarkWithCountDtoList: List<ComplimentMarkWithCountDto>,
    val maxComplimentMarkId: Int,
    val recentReviewContent: String
)




data class UserPoolDetailReviewAll(
    val code: Int,
    val list: List<UserPoolDetailReviewItem>,
    val msg: String,
    val succeed: Boolean
)

data class UserPoolDetailReviewItem(
    val mannerPoint: Int,
    val nickname: String,
    val reviewContent: String,
    val userProfileUrl: String,
    val writtenDate: String
)


data class UserPoolSearch(
    val code: Int,
    val list: List<UserPoolItem>,
    val msg: String,
    val succeed: Boolean
)

