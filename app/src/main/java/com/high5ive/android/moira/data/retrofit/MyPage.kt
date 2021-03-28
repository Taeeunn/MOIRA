package com.high5ive.android.moira.data.retrofit

data class MyPage(
    val code: Int,
    val data: MyPageData,
    val msg: String,
    val succeed: Boolean
)

data class MyPageData(
    val appliedPostCount: Int,
    val likedPostCount: Int,
    val nickname: String,
    val positionName: String,
    val profileImageUrl: String,
    val shortIntroduction: String,
    val writtenPostCount: Int
)

data class WrittenPost(
    val code: Int,
    val list: List<WrittenPostItem>,
    val msg: String,
    val succeed: Boolean
)

data class WrittenPostItem(
    val hitCount: Int,
    val projectApplyId: Int,
    val projectId: Int,
    val projectImageUrl: String,
    val projectTitle: String,
    val writtenTime: String
)


data class ApplyPost(
    val code: Int,
    val list: List<ApplyPostItem>,
    val msg: String,
    val succeed: Boolean
)

data class ApplyPostItem(
    val hitCount: Int,
    val projectApplyId: Int,
    val projectId: Int,
    val projectImageUrl: String,
    val projectTitle: String,
    val writtenTime: String
)

data class ScrapRecruitPost(
    val code: Int,
    val list: List<ScrapRecruitPostItem>,
    val msg: String,
    val succeed: Boolean
)

data class ScrapRecruitPostItem(
    val hashtagList: List<Hashtag>,
    val hitCount: Int,
    val projectId: Int,
    val projectImageUrl: String,
    val projectTitle: String,
    val writtenTime: String
)


data class ScrapUserPool(
    val code: Int,
    val list: List<ScrapUserPoolItem>,
    val msg: String,
    val succeed: Boolean
)

data class ScrapUserPoolItem(
    val hashtagList: List<Hashtag>,
    val nickname: String,
    val positionName: String,
    val profileImage: String,
    val shortIntroduction: String,
    val userPoolId: Int
)


// 수상 추가하기
data class MyProfileAwardAdd(
    val awardContent: String,
    val awardName: String
)

data class MyProfileAwardAddResponse(
    val code: Int,
    val list: List<MyProfileAwardAddResponseItem>,
    val msg: String,
    val succeed: Boolean
)

data class MyProfileAwardAddResponseItem(
    val userAwardContent: String,
    val userAwardId: Int,
    val userAwardName: String
)


// 경력 추가하기
data class MyProfileCareerAdd(
    val companyName: String,
    val endAt: String,
    val startAt: String,
    val task: String
)

data class MyProfileCareerAddResponse(
    val code: Int,
    val list: List<MyProfileCareerAddResponseItem>,
    val msg: String,
    val succeed: Boolean
)

data class MyProfileCareerAddResponseItem(
    val companyName: String,
    val endAt: String,
    val startAt: String,
    val task: String,
    val userCareerId: Int
)


// 자격증 추가하기
data class MyProfileLicenseAdd(
    val acquiredAt: String,
    val licenseName: String
)


data class MyProfileLicenseAddResponse(
    val code: Int,
    val list: List<MyProfileLicenseAddResponseItem>,
    val msg: String,
    val succeed: Boolean
)

data class MyProfileLicenseAddResponseItem(
    val acquiredAt: String,
    val licenseName: String,
    val userLicenseId: Int
)


// 링크 추가하기
data class MyProfileLinkAdd(
    val linkUrl: String
)

data class MyProfileLinkAddResponse(
    val code: Int,
    val list: List<MyProfileLinkAddResponseItem>,
    val msg: String,
    val succeed: Boolean
)

data class MyProfileLinkAddResponseItem(
    val userLinkId: Int,
    val userLinkUrl: String
)

// 학과 검색
data class MyProfileMajorSearchResponse(
    val code: Int,
    val list: List<MyProfileMajorSearchResponseItem>,
    val msg: String,
    val succeed: Boolean
)

data class MyProfileMajorSearchResponseItem(
    val majorId: Int,
    val majorName: String
)


// 학력 추가하기
data class MyProfileSchoolAdd(
    val endAt: String,
    val majorId: Int,
    val schoolId: Int,
    val staredAt: String,
    val userSchoolStatus: String
)

data class MyProfileSchoolAddResponse(
    val code: Int,
    val list: List<MyProfileSchoolAddResponseItem>,
    val msg: String,
    val succeed: Boolean
)

data class MyProfileSchoolAddResponseItem(
    val endedAt: String,
    val majorName: String,
    val schoolName: String,
    val schoolStatus: String,
    val startedAt: String,
    val userSchoolId: Int
)


// 학교 검색
data class MyProfileSchoolSearchResponse(
    val code: Int,
    val list: List<MyProfileSchoolSearchResponseItem>,
    val msg: String,
    val succeed: Boolean
)

data class MyProfileSchoolSearchResponseItem(
    val schoolId: Int,
    val schoolName: String
)


