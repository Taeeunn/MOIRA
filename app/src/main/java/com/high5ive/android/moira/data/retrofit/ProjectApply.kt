package com.high5ive.android.moira.data.retrofit

// 지원하기
data class ProjectApply(
    val projectId: Int,
    val userPortfolioTypeList: List<String>
)


// 지원내역
data class ProjectApplyDetail(
    val code: Int,
    val data: ProjectApplyDetailData,
    val msg: String,
    val succeed: Boolean
)

data class ProjectApplyDetailData(
    val hashtagResponseDtoList: List<HashtagResponseDto>,
    val imageUrl: String,
    val nickname: String,
    val shortIntroduction: String,
    val userAwardResponseDtoList: List<UserAwardResponseDto>,
    val userCareerResponseDtoList: List<UserCareerResponseDto>,
    val userId: Int,
    val userLicenseResponseDtoList: List<UserLicenseResponseDto>,
    val userLinkResponseDtoList: List<UserLinkResponseDto>,
    val userSchoolResponseDtoList: List<UserSchoolResponseDto>
)

// 지원서 상태 변경 (팀장)
data class ProjectApplyModifyStatus(
    val status: String
)


// 팀에 지원한 유저들 조회
data class ProjectApplyUser(
    val code: Int,
    val list: List<ProjectApplyUserItem>,
    val msg: String,
    val succeed: Boolean
)

data class ProjectApplyUserItem(
    val imageUrl: String,
    val nickname: String,
    val position: String,
    val projectApplyId: Int,
    val shortIntroduction: String,
    val userId: Int
)


// 지원한 유저의 '사용자 평가' 조회
data class ApplyUserReview(
    val code: Int,
    val data: ApplyUserReviewData,
    val msg: String,
    val succeed: Boolean
)

data class ApplyUserReviewData(
    val avgMannerPoint: Int,
    val complimentMarkWithCountDtoList: List<ComplimentMarkWithCountDto>,
    val nickname: String,
    val recentReviewContent: String
)

// 유저의 '모든 리뷰 내용' 조회
data class ApplyUserReviewAll(
    val code: Int,
    val list: List<ApplyUserReviewAllItem>,
    val msg: String,
    val succeed: Boolean
)

data class ApplyUserReviewAllItem(
    val mannerPoint: Float,
    val nickname: String,
    val reviewContent: String,
    val userProfileUrl: String,
    val writtenDate: String
)