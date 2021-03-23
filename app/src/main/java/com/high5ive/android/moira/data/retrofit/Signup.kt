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