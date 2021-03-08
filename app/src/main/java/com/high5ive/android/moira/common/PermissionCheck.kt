package com.high5ive.android.moira.common

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-08
 */
class PermissionCheck(private val permissionActivity: Activity, private val requirePermissions: Array<String>){
    private val permissionRequestCode = 100

    // 권한 체크용
    init {
        var failReqeustPermissionList = ArrayList<String>()

        for (permission in requirePermissions) {
            if (ContextCompat.checkSelfPermission(permissionActivity.applicationContext, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                failReqeustPermissionList.add(permission)
            }
        }

        if (failReqeustPermissionList.isNotEmpty()) {
            val array = arrayOfNulls<String>(failReqeustPermissionList.size)
            ActivityCompat.requestPermissions(
                permissionActivity,
                failReqeustPermissionList.toArray(array),
                permissionRequestCode
            )
        }
    }
}