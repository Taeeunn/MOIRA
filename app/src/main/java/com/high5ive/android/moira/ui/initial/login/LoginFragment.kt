package com.high5ive.android.moira.ui.initial.login

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.initial.OnTransitionListener
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    lateinit var navController: NavController
    private var onTransitionListener: OnTransitionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        lookaround_text.setOnClickListener(this)
        kakao_login_btn.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onTransitionListener = context as OnTransitionListener
    }

    override fun onDetach() {
        super.onDetach()
        onTransitionListener = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.lookaround_text -> {
//                startActivity(Intent(activity, MainActivity::class.java))
                onTransitionListener?.OnTransitionListener()
            }

            R.id.kakao_login_btn -> {


                // 로그인 공통 callback 구성
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        Log.e("login", "로그인 실패", error)
                    } else if (token != null) {
                        Log.i("login", "로그인 성공 ${token.accessToken}")


                        showTokenInfo()
                        getUserInfo()
                        navController.navigate(R.id.action_loginFragment_to_setNicknameFragment)
                    }
                }

                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                    UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
                }
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
