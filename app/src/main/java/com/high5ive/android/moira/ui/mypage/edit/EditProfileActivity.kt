package com.high5ive.android.moira.ui.mypage.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.AwardAdapter
import com.high5ive.android.moira.adapter.CareerAdapter
import com.high5ive.android.moira.adapter.CertificateAdapter
import com.high5ive.android.moira.adapter.LinkAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.databinding.ActivityEditProfileBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.mypage.edit.addinfo.award.AddAwardHistoryActivity
import com.high5ive.android.moira.ui.mypage.edit.addinfo.career.AddCareerActivity
import com.high5ive.android.moira.ui.mypage.edit.addinfo.certificate.AddCertificateActivity
import com.high5ive.android.moira.ui.mypage.edit.addinfo.link.AddLinkActivity
import com.high5ive.android.moira.ui.mypage.edit.editinfo.EditIntroActivity
import com.high5ive.android.moira.ui.mypage.edit.editinfo.EditNicknameActivity
import com.high5ive.android.moira.ui.mypage.edit.editinfo.EditPositionActivity
import com.high5ive.android.moira.ui.mypage.edit.editinfo.EditTagActivity
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.edit_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File


class EditProfileActivity : AppCompatActivity(), View.OnClickListener {

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

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()


        initRetrofit()
        getMyProfileData()


        add_career_btn.setOnClickListener(this)
        add_certificate_btn.setOnClickListener(this)
        add_award_btn.setOnClickListener(this)
        add_link_btn.setOnClickListener(this)
        set_image_btn.setOnClickListener(this)
        nickname_btn.setOnClickListener(this)
        position_btn.setOnClickListener(this)
        intro_btn.setOnClickListener(this)
        tag_btn.setOnClickListener(this)
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
        when (v?.id) {

            R.id.add_career_btn -> startActivityForResult(Intent(this, AddCareerActivity::class.java), 4)

            R.id.add_certificate_btn -> startActivityForResult(
                Intent(
                    this,
                    AddCertificateActivity::class.java
                ), 5
            )

            R.id.add_award_btn -> startActivityForResult(Intent(this, AddAwardHistoryActivity::class.java), 6)

            R.id.add_link_btn -> startActivityForResult(
                Intent(this, AddLinkActivity::class.java),
                3
            )

//            R.id.add_tag_btn -> startActivity(Intent(this, AddTagActivity::class.java))

            R.id.set_image_btn -> {
                TedImagePicker.with(this@EditProfileActivity)
                    .title(R.string.select_picture)
                    .backButton(R.drawable.ic_baseline_arrow_back_24)
                    .buttonText(R.string.complete)
                    .buttonBackground(R.drawable.md_transparent)
                    .buttonTextColor(R.color.black)
                    .showCameraTile(false)
                    .start { uri -> showSingleImage(uri) }

            }

            R.id.nickname_btn -> {
                startActivityForResult(Intent(this, EditNicknameActivity::class.java), 0)
            }

            R.id.position_btn -> {
                startActivityForResult(Intent(this, EditPositionActivity::class.java), 1)
            }

            R.id.intro_btn -> {
                startActivityForResult(Intent(this, EditIntroActivity::class.java), 1)
            }

            R.id.tag_btn -> {
                startActivityForResult(Intent(this, EditTagActivity::class.java), 2)
            }


        }
    }


    private fun showSingleImage(uri: Uri) {

        editProfileImage(uri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {

            Activity.RESULT_OK -> {

                getMyProfileData()

            }
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

                    if (succeed) {

                        val data: MyProfileData = response.body()?.data!!
                        Log.v("data", data.toString())

                        binding.myprofile = data

                        setTag(data.hashtagResponseDtoList.toMutableList())

                        setTag2(data.positionName)


                        Glide.with(this@EditProfileActivity)
                            .load(data.profileImageUrl)
                            .override(50, 50)
                            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
                            .into(binding.memberImage)

                        career_recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                CareerAdapter(data.userCareerResponseDtoList)
                        }

                        link_recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                LinkAdapter(data.userLinkResponseDtoList)
                        }

                        certificate_recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                CertificateAdapter(data.userLicenseResponseDtoList)
                        }

                        award_recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                AwardAdapter(data.userAwardResponseDtoList)
                        }

                    }

                }
            })
        }.run()
    }

    private fun setTag(tagList: MutableList<HashtagResponseDto>) {

        interest_tag_group.removeAllViews()


        for (index in tagList.indices) {
            val tagName = tagList[index].hashtagName

            val chip = Chip(this)
            val drawable =
                ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChipsAction)
            chip.setChipDrawable(drawable)

            chip.text = tagName

            interest_tag_group.addView(chip)
        }
    }


    private fun setTag2(tag: String) {
        position_group.removeAllViews()

        val chip = Chip(this)
        val drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChipsAction)
        chip.setChipDrawable(drawable)

        chip.text = tag

        position_group.addView(chip)
    }

    private fun editProfileImage(uri: Uri) {

        val filePath = getRealPathFromURI(uri)
        val file = File(filePath)
        var fileName = nickname_et.text.toString()
        fileName = "$fileName.png"


        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val image: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", fileName, requestBody)


        myAPI.editProfileImage(token, image).enqueue(object :
            Callback<ProfileImageEditResponse> {
            override fun onFailure(call: Call<ProfileImageEditResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ProfileImageEditResponse>,
                response: Response<ProfileImageEditResponse>
            ) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if (succeed) {

                    val data: String = response.body()?.data!!
                    Log.v("data", data)
                    getMyProfileData()

                }

            }
        })
    }

    fun getRealPathFromURI(contentUri: Uri?): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri!!, proj, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
        cursor.close()
        return path
    }

}