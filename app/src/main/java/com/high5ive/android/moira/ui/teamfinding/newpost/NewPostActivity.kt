package com.high5ive.android.moira.ui.teamfinding.newpost

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.high5ive.android.moira.R
import com.high5ive.android.moira.common.PermissionCheck
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_new_post.*


class NewPostActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        var requestPermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)


        PermissionCheck(this@NewPostActivity, requestPermissions)

        picture_btn.setOnClickListener {

            TedBottomPicker.with(this@NewPostActivity)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .showMultiImage { uriList ->

                    for (uri in uriList) {
                        val newImage = ImageView(this)
                        newImage.setImageURI(uri)


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
    }

//            MaterialDialog(this).show {
//                title(R.string.upload_picture)
//                cornerRadius(0f)
//                neutralButton(R.string.cancle)
//
//                positiveButton(R.string.take_picture) {
//
////                    ImagePicker.with(this@NewPostActivity)
////                        .cameraOnly()    //User can only capture image using Camera
////                        .start()
//
//                }
//
//                negativeButton(R.string.select_gallery) {
////                    ImagePicker.create(this@NewPostActivity) // Activity or Fragment
////                        .start();
//
//                    TedImagePicker.with(this@NewPostActivity)
//                        .title(R.string.select_gallery)
//                        .buttonBackground(R.drawable.md_transparent)
//                        .buttonTextColor(R.color.black)
//                        .startMultiImage { uriList ->
//
////
//                            for (uri in uriList) {
//
//                                Log.v("uriList", convertContentToFileUri(context, uri).toString())
//                                val newImage = ImageView(context)
//                                newImage.setImageURI(convertContentToFileUri(context, uri))
//
//
//                                val lp = LinearLayout.LayoutParams(
//                                    LinearLayout.LayoutParams.MATCH_PARENT,
//                                    205.toPx(ToastUtil.context)
//                                )
//                                lp.setMargins(0, 0, 0, 20.toPx(ToastUtil.context))
//                                newImage.layoutParams = lp
//                                picture_layout.addView(newImage)
//
//                            }
//                        }
//                        }
//                }
//            }


//        register_button.setOnClickListener{
//            startActivity(Intent(this@NewPostActivity, ApplyCompleteActivity::class.java))
//        }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.v("fileuri", data?.data.toString())
//        when (resultCode) {
//            Activity.RESULT_OK -> {
//                //Image Uri will not be null for RESULT_OK
//                val fileUri = data?.data
//                Log.v("fileuri", fileUri.toString())
//                //You can get File object from intent
////                val file: File = ImagePicker.getFile(data)!!
////                //You can also get File Path from intent
////                val filePath: String = ImagePicker.getFilePath(data)!!
//
//                val newImage = ImageView(this)
//                newImage.setImageURI(fileUri)
//
////            val imageHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
////                205f, resources.displayMetrics
////            ).toInt()
//
//                val lp = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    205.toPx(context)
//                )
//                lp.setMargins(0, 0, 0, 20.toPx(context))
//                newImage.layoutParams = lp
//                picture_layout.addView(newImage)
//            }
//
////            ImagePicker.RESULT_ERROR -> {
////                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
////            }
////            else -> {
////                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
////            }
//        }
//    }


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


}