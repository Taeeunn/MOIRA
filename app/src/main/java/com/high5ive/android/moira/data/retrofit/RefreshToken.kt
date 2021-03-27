package com.high5ive.android.moira.data.retrofit

data class RefreshToken(
    val grant_type: String,
    val client_id: String,
    val refresh_token: String
)