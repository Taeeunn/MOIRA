package com.high5ive.android.moira.ui.teamfinding.newpost

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.afollestad.materialdialogs.MaterialDialog
import com.github.dhaval2404.imagepicker.ImagePicker
import com.high5ive.android.moira.R
import com.high5ive.android.moira.common.PermissionCheck
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.activity_new_post.*
import java.io.File


class NewPostActivity : AppCompatActivity(), View.OnClickListener {

//    var requestPermissions = arrayOf(Manifest.permission.CAMERA,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)



        picture_btn.setOnClickListener(this)
        register_button.setOnClickListener(this)

//            TedBottomPicker.with(this@NewPostActivity)
//                .setPeekHeight(1600)
//                .showTitle(false)
//                .setCompleteButtonText("Done")
//                .setEmptySelectionText("No Select")
//                .showMultiImage { uriList ->
//
//                    for (uri in uriList) {
//                        val newImage = ImageView(this)
//                        newImage.setImageURI(uri)
//
//
//                        val lp = LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            205.toPx(this)
//                        )
//                        lp.setMargins(0, 0, 0, 20.toPx(this))
//                        newImage.layoutParams = lp
//                        picture_layout.addView(newImage)
//                    }
//                }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {

                val fileUri = data?.data
                val file: File = ImagePicker.getFile(data)!!
                val filePath: String = ImagePicker.getFilePath(data)!!

                val newImage = ImageView(this)
                newImage.setImageURI(fileUri)


                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    205.toPx(this)
                )
                lp.setMargins(0, 0, 0, 20.toPx(this))
                newImage.layoutParams = lp
                picture_layout.addView(newImage)
            }
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


    private fun Int.toPx(context: Context) =
        this * context.resources.displayMetrics.densityDpi /
                DisplayMetrics.DENSITY_DEFAULT


    private fun showMultiImage(uriList: List<Uri>) {

        for (uri in uriList) {

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
            R.id.register_button -> finish()

            R.id.picture_btn -> {

                MaterialDialog(this).show {
                    title(R.string.upload_picture)
                    cornerRadius(0f)
                    neutralButton(R.string.cancle)

                    positiveButton(R.string.take_picture) {

                        ImagePicker.with(this@NewPostActivity)
                            .cameraOnly()    //User can only capture image using Camera
                            .start()

                    }

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
        }
    }
}








