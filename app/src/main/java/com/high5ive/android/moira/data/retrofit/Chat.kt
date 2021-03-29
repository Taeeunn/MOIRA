package com.high5ive.android.moira.data.retrofit

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-29
 */

data class Chat(
    val code: Int,
    val list: List<ChatItem>,
    val msg: String,
    val succeed: Boolean
)

data class ChatItem(
    val chatRoomId: Int,
    val lastMessageContent: String,
    val opponentId: Int,
    val opponentNickname: String,
    val opponentProfileImage: String,
    val unReadCount: Int,
    val writtenDate: String
)
