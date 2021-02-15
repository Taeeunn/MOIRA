package com.high5ive.android.moira

import android.app.Activity
import android.util.Log
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import java.lang.ref.WeakReference

class NaverLoginHandler(activity: Activity):
    OAuthLoginHandler() {
    private val weakReference: WeakReference<Activity> by lazy { WeakReference(activity) }

    private val oAuthLoginModule by lazy {
        OAuthLogin.getInstance().apply {
            init(
                weakReference.get(),
                OAUTH_CLIENT_ID,
                OAUTH_CLIENT_SECRET,
                OAUTH_CLIENT_NAME
            )

            showDevelopersLog(true)
        }
    }


    override fun run(success: Boolean) {

        if (success) {
            val accessToken: String = oAuthLoginModule.getAccessToken(weakReference.get())
            val refreshToken: String = oAuthLoginModule.getRefreshToken(weakReference.get())
            val expiresAt: Long = oAuthLoginModule.getExpiresAt(weakReference.get())
            val tokenType: String = oAuthLoginModule.getTokenType(weakReference.get())
//            val accessToken = oAuthLoginModule.getAccessToken(weakReference.get())
//            callback.invoke("Bearer $accessToken")

            Log.d("Login", "success")
        } else {
            val errorCode = oAuthLoginModule.getLastErrorCode(weakReference.get())
            val errorDesc = oAuthLoginModule.getLastErrorDesc(weakReference.get())

            Log.i(TAG, "errorCode: $errorCode, errorDesc: $errorDesc")
        }
    }

    fun startOAuthLoginActivity() {
        oAuthLoginModule.startOauthLoginActivity(weakReference.get(), this)
    }

    companion object {
        private const val TAG = "NaverLoginHandler"
        private const val OAUTH_CLIENT_ID = "Wb1bLmcaPiT1E6VW_xI3"
        private const val OAUTH_CLIENT_SECRET = "cW1A3BXG9Z"
        private const val OAUTH_CLIENT_NAME = "MOIRA"
    }
}
