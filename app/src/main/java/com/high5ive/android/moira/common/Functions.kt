package com.high5ive.android.moira.common

import com.kakao.sdk.auth.AuthApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-14
 */
class Functions {

    fun BackgroundTask () {
//onPreExcute


        CoroutineScope(Dispatchers.Main).launch {
//doInBackground
            withContext(Dispatchers.Default) {
                //                Log.v("tag", to.getToken().toString())
                AuthApiClient.instance.refreshAccessToken()

            }
//onPostExecute

        }

    }
}