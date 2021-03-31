package com.high5ive.android.moira.data.retrofit

data class RecruitPost(
    val code: Int,
    val list: List<RecruitPostItem>,
    val msg: String,
    val succeed: Boolean
)

data class RecruitPostItem(
    val hashtagList: List<String>,
    val hitCount: Int,
    val id: Int,
    val imageUrl: String,
    val time: String,
    val title: String,
    val writer: String
)

data class RecruitPostDetail(
    val code: Int,
    val data: RecruitPostDetailData,
    val msg: String,
    val succeed: Boolean
)

data class RecruitPostDetailData(
    val content: String,
    val duration: String,
    val hashtagList: List<String>,
    val hitCount: Int,
    val imageUrlList: List<String>?,
    val isLike: Boolean,
    val likeCount: Int,
    val location: String,
    val positionCategoryList: List<PositionCategory>,
    val time: String,
    val title: String,
    val writer: String
)




data class ProjectModifyTitleRequestDTO(
    val content: String,
    val title: String
)



data class NewRecruitPost(
    val content: String,
    val duration: String,
    val hashtagList: List<String>,
    val localType: String,
    val positionCategoryList: List<PositionCategory>,
    val title: String
)


data class NewRecruitPostResponse(
    val code: Int,
    val data: Int,
    val msg: String,
    val succeed: Boolean
)


data class Comment(
    val code: Int,
    val list: List<CommentItem>,
    val msg: String,
    val succeed: Boolean
)

data class CommentItem(
    val content: String,
    val deletable: Boolean,
    val id: Int,
    val imageUrl: String,
    val nickname: String,
    val parentId: Int,
    val time: String,
    val userId: String
)

data class CommentAdd(
    val content: String
)

data class CommentAddResponse(
    val code: Int,
    val data: Int,
    val msg: String,
    val succeed: Boolean
)


data class Report(
    val reportType: String,
    val targetId: Int,
    val targetType: String
)



