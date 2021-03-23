package com.high5ive.android.moira.data.retrofit

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-23
 */


data class LoginUser(
    val code: Int,
    val data: LoginData,
    val msg: String,
    val succeed: Boolean
)


data class LoginData(
    val firstLogin: Boolean,
    val jwtToken: String
)




