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



