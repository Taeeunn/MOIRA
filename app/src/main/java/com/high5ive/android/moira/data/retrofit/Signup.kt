package com.high5ive.android.moira.data.retrofit

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-23
 */


data class SignUpInfo(
    val hashtagIdList: List<Int>,
    val nickname: String,
    val positionId: Int
)

data class PositionCategoryResponse(
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