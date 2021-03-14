package com.high5ive.android.moira.data.retrofit

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-14
 */
data class LoginUser(
    val code: Int,
    val data: String,
    val msg: String,
    val succeed: Boolean
)