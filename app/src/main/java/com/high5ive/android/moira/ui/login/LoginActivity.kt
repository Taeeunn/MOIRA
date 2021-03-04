package com.high5ive.android.moira.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R
import com.high5ive.android.moira.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mOAuthLoginModule: OAuthLogin
    lateinit var mContext: Context

    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)

//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_login)

//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this



        // The returned binding has references to all the Views with an ID.
        binding.lookaroundText.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("login", "로그인 실패", error)
            }
            else if (token != null) {
                Log.i("login", "로그인 성공 ${token.accessToken}")

                showTokenInfo()
                getUserInfo()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }


        kakao_login_btn.setOnClickListener{
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

    }

    private fun showTokenInfo(){
        // 토큰 정보 보기
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e("token", "토큰 정보 보기 실패", error)
            }
            else if (tokenInfo != null) {
                Log.e("token", "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")
            }
        }
    }

    private fun getUserInfo(){
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("user", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("user", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
            }
        }
    }

}