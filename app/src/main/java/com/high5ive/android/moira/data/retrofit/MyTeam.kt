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
    val projectId: Int,
    val title: String
)