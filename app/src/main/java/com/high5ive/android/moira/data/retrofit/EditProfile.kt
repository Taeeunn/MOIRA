package com.high5ive.android.moira.data.retrofit

data class EditProfile(
    val code: Int,
    val data: EditProfileData,
    val msg: String,
    val succeed: Boolean
)

data class EditProfileData(
    val hashtagList: List<Hashtag>,
    val nickname: String,
    val position: String,
    val positionCategory: String,
    val shortIntroduction: String
)

data class MyPageEditProfileUpdateRequestDto(
    val hashtagIdList: List<Int>,
    val nickname: String,
    val positionId: Int,
    val shortIntroduction: String
)


data class MyProfile(
    val code: Int,
    val data: MyProfileData,
    val msg: String,
    val succeed: Boolean
)

data class MyProfileData(
    val hashtagResponseDtoList: List<HashtagResponseDto>,
    val nickname: String,
    val positionName: String,
    val profileImageUrl: String,
    val shortIntroduction: String,
    val userAwardResponseDtoList: List<UserAwardResponseDto>,
    val userCareerResponseDtoList: List<UserCareerResponseDto>,
    val userLicenseResponseDtoList: List<UserLicenseResponseDto>,
    val userLinkResponseDtoList: List<UserLinkResponseDto>,
    val userSchoolResponseDtoList: List<UserSchoolResponseDto>
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

data class HashtagResponseDto(
    val hashtagId: Int,
    val hashtagName: String
)