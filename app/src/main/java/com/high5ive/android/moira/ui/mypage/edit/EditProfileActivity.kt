package com.high5ive.android.moira.ui.mypage.edit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.databinding.ActivityEditProfileBinding
import com.high5ive.android.moira.databinding.ActivityTeamDetailBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.mypage.edit.addinfo.award.AddAwardHistoryActivity
import com.high5ive.android.moira.ui.mypage.edit.addinfo.career.AddCareerActivity
import com.high5ive.android.moira.ui.mypage.edit.addinfo.certificate.AddCertificateActivity
import com.high5ive.android.moira.ui.mypage.edit.addinfo.education.AddEducationActivity
import com.high5ive.android.moira.ui.mypage.edit.addinfo.link.AddLinkActivity
import com.high5ive.android.moira.ui.mypage.edit.addinfo.tag.AddTagActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.edit_info.*
import kotlinx.android.synthetic.main.edit_info.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class EditProfileActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityEditProfileBinding
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_edit_profile
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()


        initRetrofit()

        getMyProfileData()

        var tagList = mutableListOf<String>()
        tagList.add("관련태그1")
        tagList.add("관련태그2")
        tagList.add("관련태그3")
        setTag(tagList);



        register_button.setOnClickListener(this)
        add_education_btn.setOnClickListener(this)
        add_career_btn.setOnClickListener(this)
        add_certificate_btn.setOnClickListener(this)
        add_award_btn.setOnClickListener(this)
        add_link_btn.setOnClickListener(this)
        add_tag_btn.setOnClickListener(this)
    }

    private fun setTag(tagList: MutableList<String>) {
        for (index in tagList.indices) {
            val tagName = tagList[index]
            //val chip = Chip(ContextThemeWrapper(context, R.style.MaterialChipsAction))
            val chip = Chip(this)
            val drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChipsAction)
            chip.setChipDrawable(drawable)

            chip.text = tagName
            chip.setTextAppearance(R.style.tag_text)
            chip.setCloseIconSizeResource(R.dimen.tag_close_icon)
            chip.isCloseIconEnabled = true
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener {
                tagList.remove(tagName)
                edit_info.interest_tag_group.removeView(chip)
            }
            edit_info.interest_tag_group.addView(chip)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.register_button -> {
                editMyProfile()
            }

            R.id.add_education_btn -> startActivity(Intent(this, AddEducationActivity::class.java))

            R.id.add_career_btn -> startActivity(Intent(this, AddCareerActivity::class.java))

            R.id.add_certificate_btn -> startActivity(Intent(this, AddCertificateActivity::class.java))

            R.id.add_award_btn -> startActivity(Intent(this, AddAwardHistoryActivity::class.java))

            R.id.add_link_btn -> startActivity(Intent(this, AddLinkActivity::class.java))

            R.id.add_tag_btn -> startActivity(Intent(this, AddTagActivity::class.java))
        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getMyProfileData() {
        Runnable {

            myAPI.getMyProfileData(token).enqueue(object :
                Callback<MyProfile> {
                override fun onFailure(call: Call<MyProfile>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<MyProfile>, response: Response<MyProfile>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val data: MyProfileData = response.body()?.data!!
                        Log.v("data", data.toString())

                        binding.myprofile = data

                    }

                }
            })
        }.run()
    }

    private fun editMyProfile(){
        Runnable {

            val hashtagIdList: List<Int> = listOf(1, 2)
            val nickname: String = "moiraa"
            val positionId: Int = 1
            val shortIntroduction: String = "개발자 김돌돌입니다555."

            val body_data = MyPageEditProfileUpdateRequestDto(hashtagIdList, nickname, positionId, shortIntroduction)
            myAPI.editMyProfile(token, body_data).enqueue(object :
                Callback<EditProfile> {
                override fun onFailure(call: Call<EditProfile>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<EditProfile>, response: Response<EditProfile>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val data: EditProfileData = response.body()?.data!!
                        Log.v("data", data.toString())


                        finish()

                    }

                }
            })
        }.run()
    }
}