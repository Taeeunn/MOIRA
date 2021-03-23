package com.high5ive.android.moira.data.retrofit

data class PositionCategory(
    val code: Int,
    val list: List<PositionItem>,
    val msg: String,
    val succeed: Boolean
)

data class PositionItem(
    val id: Int,
    val positionCategoryImage: String,
    val positionCategoryName: String
)

data class PositionDetail(
    val code: Int,
    val list: List<PositionDetailItem>,
    val msg: String,
    val succeed: Boolean
)

data class PositionDetailItem(
    val positionId: Int,
    val positionName: String
)