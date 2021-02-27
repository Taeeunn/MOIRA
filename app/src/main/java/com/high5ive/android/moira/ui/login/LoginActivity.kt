package com.high5ive.android.moira.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.high5ive.android.moira.R
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.android.synthetic.main.activity_login.*
import com.kakao.sdk.common.util.Utility

class LoginActivity : AppCompatActivity() {

    lateinit var mOAuthLoginModule: OAuthLogin
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//
//        var spannableString = SpannableString("둘러볼게요")
//        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)
//        lookaround_text.text = spannableString


        KakaoSdk.init(this, getString(R.string.kakao_app_key))

//         네이버 아이디로 로그인
//        val naver_client_id = "Wb1bLmcaPiT1E6VW_xI3"
//        val naver_client_secret = "cW1A3BXG9Z"
//        val naver_client_name = "MOIRA"
//
//        var keyHash = Utility.getKeyHash(this)
//        Log.v("ttt", keyHash)
//
//        mOAuthLoginModule = OAuthLogin.getInstance()
//        mOAuthLoginModule.init(
//            this, getString(R.string.naver_client_id),
//            getString(R.string.naver_client_secret), getString(
//                R.string.naver_client_name
//            )
//        )
//        mOAuthLoginModule.showDevelopersLog(true)
//
//
//
//        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)
//        buttonOAuthLoginImg.setBgResourceId(R.drawable.ic_launcher_background)
//
//        naver_login_btn.setOnClickListener {
//            //NaverLoginHandler(this).startOAuthLoginActivity()
//
//            mOAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler);
//
//
////            val isSuccessDeleteToken = mOAuthLoginModule.logoutAndDeleteToken(this@LoginActivity)
////            mOAuthLoginModule.logout(this@LoginActivity)
//        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                //Login Fail
            }
            else if (token != null) {
                //Login Success
                //startMainActivity()
                Toast.makeText(
                    baseContext, "login success", LENGTH_SHORT
                ).show()
            }
        }

        kakao_login_btn.setOnClickListener{
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    loginWithKakaoTalk(this@LoginActivity, callback = callback)
                } else {
                    loginWithKakaoAccount(this@LoginActivity, callback = callback)
                }
            }
        }


    }

//    private val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
//    object : OAuthLoginHandler() {
//
//        override fun run(success: Boolean) {
//            if (success) {
//                val accessToken: String = mOAuthLoginModule.getAccessToken(baseContext)
//                val refreshToken: String = mOAuthLoginModule.getRefreshToken(baseContext)
//                val expiresAt: Long = mOAuthLoginModule.getExpiresAt(baseContext)
//                val tokenType: String = mOAuthLoginModule.getTokenType(baseContext)
//                Toast.makeText(
//                    baseContext, "login success", LENGTH_SHORT
//                ).show()
//            } else {
//                val errorCode: String =
//                    mOAuthLoginModule.getLastErrorCode(baseContext).code
//                val errorDesc: String = mOAuthLoginModule.getLastErrorDesc(baseContext)
//                Toast.makeText(
//                    baseContext, "errorCode:" + errorCode
//                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }


}