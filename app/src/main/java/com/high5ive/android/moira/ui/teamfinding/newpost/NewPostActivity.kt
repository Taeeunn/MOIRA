package com.high5ive.android.moira.ui.teamfinding.newpost

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.dialog_recruit_position.*
import kotlinx.android.synthetic.main.dialog_user_pool.view.*
import kotlinx.android.synthetic.main.make_recruit_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File


class NewPostActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var developer_position_count: String = "0"
    var planner_position_count: String = "0"
    var designer_position_count: String = "0"
    var duration: String = ""
    var localType: String = ""
    var title: String = ""
    var content: String = ""
    var hashtagList: ArrayList<String>? = null
    var imageUriList = ArrayList<Uri>()

    val durationList = listOf("한달_미만", "세달_미만", "여섯달_미만", "여섯달_이상")
    val regionList =
        listOf("서울_인천_경기", "대전_충북_충남_세종", "광주_전남_전북", "부산_울산_경남", "대구_경북", "강원", "제주", "온라인")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()

        picture_btn.setOnClickListener(this)
        register_button.setOnClickListener(this)
        project_duration_add.setOnClickListener(this)
        project_duration_delete.setOnClickListener(this)

        region_add.setOnClickListener(this)
        region_delete.setOnClickListener(this)


        recruit_position_add.setOnClickListener(this)

        recruit_position_developer_delete.setOnClickListener(this)
        recruit_position_planner_delete.setOnClickListener(this)
        recruit_position_designer_delete.setOnClickListener(this)

        tag_add.setOnClickListener(this)


    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {

            Activity.RESULT_OK -> {

                if (requestCode == 1) {
                    hashtagList = data?.getStringArrayListExtra("list")

                    if (hashtagList != null) {
                        setTag(hashtagList!!.toMutableList())
                    }


                }
            }
        }
    }

    private fun setTag(tagList: MutableList<String>) {
        for (index in tagList.indices) {
            val tagName = tagList[index]

            val chip = Chip(this)
            val drawable =
                ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChipsAction)
            chip.setChipDrawable(drawable)
            chip.text = tagName
            chip.setCloseIconSizeResource(R.dimen.tag_close_icon)
            chip.isCloseIconEnabled = true
            chip.setOnCloseIconClickListener {
                tagList.remove(tagName)
                tag_chip_group.removeView(chip)
                hashtagList!!.remove(tagName)

            }
            tag_chip_group.addView(chip)
        }
    }

    override fun onStop() {
        super.onStop()

        tag_chip_group.removeAllViews()
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


    private fun Int.toPx(context: Context) =
        this * context.resources.displayMetrics.densityDpi /
                DisplayMetrics.DENSITY_DEFAULT


    private fun showMultiImage(uriList: List<Uri>) {

        imageUriList.clear()
        for (uri in uriList) {
            imageUriList.add(uri)
            val newImage = ImageView(this)
            newImage.setImageURI(uri)

            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                205.toPx(applicationContext)
            )
            lp.setMargins(0, 0, 0, 20.toPx(applicationContext))
            newImage.layoutParams = lp
            picture_layout.addView(newImage)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.register_button -> {

                title = project_name_et.text.toString()
                content = project_content_et.text.toString()

                if (title == "") {
                    val msg = "프로젝트 제목을 입력해주세요!"
                    Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
                    return
                }

                if (content == "") {
                    val msg = "프로젝트 소개를 입력해주세요!"
                    Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
                    return
                }

                if (duration == "") {
                    val msg = "프로젝트 기간을 선택해주세요!"
                    Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
                    return
                }

                if (localType == "") {
                    val msg = "지역을 선택해주세요!"
                    Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
                    return
                }



                Runnable {


                    duration.let { Log.v("duration", it) }

                    val positionCategoryList = arrayListOf<PositionCategory>()
                    positionCategoryList.add(
                        PositionCategory(
                            developer_position_count.toInt(),
                            "개발자"
                        )
                    )
                    positionCategoryList.add(
                        PositionCategory(
                            designer_position_count.toInt(),
                            "디자이너"
                        )
                    )
                    positionCategoryList.add(
                        PositionCategory(
                            planner_position_count.toInt(),
                            "기획자"
                        )
                    )

                    val body_data = NewRecruitPost(
                        content,
                        duration,
                        hashtagList?.toList() ?: emptyList(),
                        localType,
                        positionCategoryList,
                        title
                    )



                    Log.v("body", body_data.toString())

                    myAPI.makeNewRecruitPost(token, body_data).enqueue(object :
                        Callback<NewRecruitPostResponse> {
                        override fun onFailure(call: Call<NewRecruitPostResponse>, t: Throwable) {
                            t.printStackTrace()
                        }

                        override fun onResponse(
                            call: Call<NewRecruitPostResponse>,
                            response: Response<NewRecruitPostResponse>
                        ) {
                            Log.v("realcode", response.code().toString())
                            val code: Int = response.body()?.code ?: 0

                            val msg: String = response.body()?.msg ?: "no msg"
                            val succeed: Boolean = response.body()?.succeed ?: false

                            Log.v("code", code.toString())
                            Log.v("success", succeed.toString())
                            Log.v("msg", msg)

                            Log.v("url", call.request().url().toString())
                            Log.v("body", call.request().body().toString())

                            if (succeed) {

                                val data: Int = response.body()?.data!!
                                Log.v("data", data.toString())


                                if (imageUriList.size==0){
                                    finish()
                                }else {
                                    addTeamImage(data)
                                }

                            }

                        }
                    })
                }.run()


            }

            R.id.picture_btn -> {

                MaterialDialog(this).show {
                    title(R.string.upload_picture)
                    cornerRadius(0f)
                    neutralButton(R.string.cancle)


                    negativeButton(R.string.select_gallery) {

                        TedImagePicker.with(this@NewPostActivity)
                            .title(R.string.select_picture)
                            .backButton(R.drawable.ic_baseline_arrow_back_24)
                            .buttonText(R.string.complete)
                            .buttonBackground(R.drawable.md_transparent)
                            .buttonTextColor(R.color.black)
                            .showCameraTile(false)
                            .startMultiImage { uriList -> showMultiImage(uriList) }
                    }
                }
            }

            R.id.project_duration_add -> {
                MaterialDialog(this).show {
                    title(R.string.project_duration)

                    negativeButton(R.string.cancle)
                    positiveButton(R.string.do_add)

                    listItemsSingleChoice(R.array.project_duration) { _, index, text ->

                        addProjectDuration(text.toString())

                        duration = durationList[index]
                    }
                }
            }

            R.id.project_duration_delete -> {
                deleteProjectDuration()
            }

            R.id.region_add -> {
                MaterialDialog(this).show {
                    title(R.string.region)
                    negativeButton(R.string.cancle)
                    positiveButton(R.string.do_add)

                    listItemsSingleChoice(R.array.region) { _, index, text ->

                        addRegion(text.toString())

                        localType = regionList[index]
                    }
                }
            }

            R.id.region_delete -> {
                deleteRegion()
            }

            R.id.recruit_position_add -> {

                addRecruitPosition()

            }

            R.id.recruit_position_developer_delete -> {
                deleteDeveloperPosition()
            }
            R.id.recruit_position_planner_delete -> {
                deletePlannerPosition()
            }
            R.id.recruit_position_designer_delete -> {
                deleteDesignerPosition()
            }

            R.id.tag_add -> {
                startActivityForResult(Intent(this, AddTagActivity::class.java), 1)
            }

        }
    }

    private fun addProjectDuration(text: String) {

        if (project_duration_layout.visibility == 8) {
            project_duration_layout.visibility = View.VISIBLE
        }

        project_duration_info.text = text

    }

    private fun deleteProjectDuration() {

        project_duration_layout.visibility = View.GONE

        duration = ""
    }


    private fun addRegion(text: String) {
        Log.v("visi", region_layout.visibility.toString())

        if (region_layout.visibility == 8) {
            region_layout.visibility = View.VISIBLE
        }

        region_info.text = text

    }

    private fun deleteRegion() {


        region_layout.visibility = View.GONE
        localType = ""
    }


    private fun addRecruitPosition() {

        // Dialog만들기
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_recruit_position, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()

        mAlertDialog.developer_count.setText(developer_position_count)
        mAlertDialog.planner_count.setText(planner_position_count)
        mAlertDialog.designer_count.setText(designer_position_count)

        mDialogView.positiveButton.setOnClickListener {

            developer_position_count = "" + mAlertDialog.developer_count.text.toString()
            planner_position_count = "" + mAlertDialog.planner_count.text.toString()
            designer_position_count = "" + mAlertDialog.designer_count.text.toString()

            mAlertDialog.dismiss()

            if (developer_position_count != "0") {

                if (recruit_position_developer_layout.visibility == 8) {
                    recruit_position_developer_layout.visibility = View.VISIBLE
                }


                recruit_position_developer_count.text = developer_position_count + "명"
            }



            if (planner_position_count != "0") {

                if (recruit_position_planner_layout.visibility == 8) {
                    recruit_position_planner_layout.visibility = View.VISIBLE
                }


                recruit_position_planner_count.text = planner_position_count + "명"
            }



            if (designer_position_count != "0") {

                if (recruit_position_designer_layout.visibility == 8) {
                    recruit_position_designer_layout.visibility = View.VISIBLE
                }


                recruit_position_designer_count.text = designer_position_count + "명"
            }

        }

        mDialogView.negativeButton.setOnClickListener {
            mAlertDialog.dismiss()
        }


    }

    private fun deleteDeveloperPosition() {
        developer_position_count = "0"

        recruit_position_developer_layout.visibility = View.GONE
    }

    private fun deletePlannerPosition() {
        planner_position_count = "0"

        recruit_position_planner_layout.visibility = View.GONE
    }

    private fun deleteDesignerPosition() {
        designer_position_count = "0"

        recruit_position_designer_layout.visibility = View.GONE
    }

    private fun addTeamImage(projectId: Int) {

        val imageList = ArrayList<MultipartBody.Part>()

        for (uri in imageUriList) {
            val filePath = getRealPathFromURI(uri)
            val file = File(filePath)
            var fileName = filePath
            fileName = "$fileName.png"

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
            val image: MultipartBody.Part =
                MultipartBody.Part.createFormData("files", fileName, requestBody)

            imageList.add(image)
        }




        myAPI.addTeamImageList(token, imageList, projectId).enqueue(object :
            Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                Log.v("realcode", response.code().toString())
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                Log.v("url", call.request().url().toString())
                Log.v("body", call.request().body().toString())


            }

        })
        finish()
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








