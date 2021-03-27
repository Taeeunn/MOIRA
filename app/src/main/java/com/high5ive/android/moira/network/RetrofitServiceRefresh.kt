package com.high5ive.android.moira.network

import com.google.gson.JsonObject
import com.high5ive.android.moira.data.retrofit.MyProfileLicenseAdd
import com.high5ive.android.moira.data.retrofit.MyProfileLicenseAddResponse
import com.high5ive.android.moira.data.retrofit.RefreshToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-27
 */
interface RetrofitServiceRefresh {

    @POST("token")
    fun refreshKakaoLoginToken(
        @Body data: RefreshToken
    ): Call<JsonObject>
}