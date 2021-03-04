package com.high5ive.android.moira.login

import android.app.Application
import com.high5ive.android.moira.R
import com.kakao.sdk.common.KakaoSdk

class KakaoLoginApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}