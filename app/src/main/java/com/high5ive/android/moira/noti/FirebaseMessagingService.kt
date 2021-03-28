package com.high5ive.android.moira.noti


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-28
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FirebaseService"

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.v(TAG, "new Token: $p0")

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        preferences.edit().putString("fcm_token", p0).apply()

    }


    /**
     * this method will be triggered every time there is new FCM Message.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.v(TAG, "From: " + remoteMessage.from)

        if(remoteMessage.notification != null) {
            Log.v(TAG, "Notification Message Body: ${remoteMessage.notification?.body}")
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification!!.body!!)

//            val intent = Intent()
//            intent.action = "com.package.notification"
//            sendBroadcast(intent)
        }
    }

    private fun sendNotification(title: String?, body: String) {
//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            putExtra("Notification", body)
//        }
//
//        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//
//        var notificationBuilder = NotificationCompat.Builder(this,"Notification")
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle("Push Notification FCM")
//            .setContentText(body)
//            .setAutoCancel(true)
//            .setSound(notificationSound)
//            .setContentIntent(pendingIntent)
//
//        var notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0, notificationBuilder.build())


        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

}
