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