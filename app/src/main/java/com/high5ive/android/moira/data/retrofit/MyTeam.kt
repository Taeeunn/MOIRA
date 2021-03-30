package com.high5ive.android.moira.data.retrofit

data class MyTeam(
    val code: Int,
    val list: List<MyTeamItem>,
    val msg: String,
    val succeed: Boolean
)

data class MyTeamItem(
    val imageUrl: String,
    val memberCount: Int,
    val membersReviewed: Boolean,
    val projectId: Int,
    val title: String
)

data class MyTeamDetail(
    val code: Int,
    val data: MyTeamDetailData,
    val msg: String,
    val succeed: Boolean
)

data class MyTeamDetailData(
    val content: String,
    val imageUrl: List<String>?,
    val leader: Boolean,
    val memberCount: Int,
    val myProjectTeammateResponseDTOList: List<MyProjectTeammateResponseDTO>,
    val title: String
)

data class MyProjectTeammateResponseDTO(
    val imageUrl: String?,
    val leader: Boolean,
    val nickname: String,
    val position: String,
    val projectApplyId: Int,
    val userId: Int
)

data class ProjectModifyStatusRequestDTO(
    val status: String
)