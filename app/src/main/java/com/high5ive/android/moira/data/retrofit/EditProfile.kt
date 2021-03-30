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

data class HashtagEdit(
    val hashtagIdList: List<Int>
)

data class HashtagEditResponse(
    val code: Int,
    val data: HashtagData,
    val msg: String,
    val succeed: Boolean
)

data class HashtagData(
    val hashtagList: List<Hashtag>,
    val userId: Int
)

data class ProfileImageEditResponse(
    val code: Int,
    val data: String,
    val msg: String,
    val succeed: Boolean
)

data class IntroEdit(
    val shortIntroduction: String
)

data class IntroEditResponse(
    val code: Int,
    val data: IntroEditResponseData,
    val msg: String,
    val succeed: Boolean
)

data class IntroEditResponseData(
    val shortIntroduction: String,
    val userId: Int
)

data class NicknameEdit(
    val nickname: String
)

data class NicknameEditResponse(
    val code: Int,
    val data: NicknameEditResponseData,
    val msg: String,
    val succeed: Boolean
)

data class NicknameEditResponseData(
    val nickname: String,
    val userId: Int
)


data class PositionResponse(
    val code: Int,
    val list: List<PositionResponseItem>,
    val msg: String,
    val succeed: Boolean
)

data class PositionResponseItem(
    val positionCategoryId: Int,
    val positionCategoryName: String,
    val positionId: Int,
    val positionName: String
)

data class PositionEdit(
    val positionId: Int
)

data class PositionEditResponse(
    val code: Int,
    val data: PositionEditResponseData,
    val msg: String,
    val succeed: Boolean
)

data class PositionEditResponseData(
    val positionId: Int,
    val positionName: String,
    val userId: Int
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



