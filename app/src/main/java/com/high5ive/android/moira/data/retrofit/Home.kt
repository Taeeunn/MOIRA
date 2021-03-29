package com.high5ive.android.moira.data.retrofit

data class HomeResponse(
    val code: Int,
    val data: HomeResponseData,
    val msg: String,
    val succeed: Boolean
)


data class HomeResponseData(
    val hasUnreadAlarm: Boolean,
    val hasUnreadMessage: Boolean
)


data class Alarm(
    val code: Int,
    val list: List<AlarmItem>,
    val msg: String,
    val succeed: Boolean
)


data class AlarmItem(
    val alarmContent: String,
    val alarmTargetId: Int,
    val alarmType: String,
    val read: Boolean
)