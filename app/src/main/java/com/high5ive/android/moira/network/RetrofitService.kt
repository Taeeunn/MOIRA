package com.high5ive.android.moira.network

import com.high5ive.android.moira.data.retrofit.LoginUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Taeeun Kim
 * @email xodms8713@gmail.com
 * @created 2021-03-14
 */
interface RetrofitService {

//    @GET("/woof.json?ref=apilist.fun")
//    fun getInfo() : Call<DataClass>
//
//    @Multipart
//    @POST("user")
//    fun registerUser(
//        @Part("email") email: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("nickname") nickname: RequestBody,
//        @Part img : MultipartBody.Part): Call<DataClass>

    @POST("signup")
    @FormUrlEncoded
    fun loginUser(
        @Field("provider") provider : String,
        @Field("token") token : String): Call<LoginUser>


//    //B1 -> ok
//    @POST("notice")
//    @FormUrlEncoded
//    fun uploadTalk(
//        @Header("access-token") token: String,
//        @Field("content") content : String): Call<DataClass>
//
//
//
//    //M1 ->
//    @GET("mypage")
//    fun myPage_auth(
//        @Header("access-token") token: String): Call<UserAuth>
//
//
//    //M2
//    @GET("/mypage/notice")
//    fun myPage_talk(
//        @Header("access-token") token: String): Call<UserTalk>
//
//
//    //S1
//    @GET("users")
//    fun getUserInfo_register_symptom(
//        @Header("access-token") token: String): Call<UserInfo>
//
//
//    @Multipart
//    @POST("auth")
//    fun ocrAuth(
//        @Header("access-token") token: String,
//        @Part img : MultipartBody.Part): Call<DataClass>
//
//
//    @Multipart
//    @POST("word_extraction")
//    fun ocrdirect(
//        @Part("language") language: String,
//        @Part base_image : MultipartBody.Part): Call<DataClass>


//    @Query("Type")Type:String,
//    @Query("KEY")KEY:String,
//    @Query("pIndex")pIndex:Int? = null,
//    @Query("pSize")pSize:Int? = null


//    @GET("users/{user}/repos")
//    fun getListRepos(
//        @Query("user") user : String,
//    ): Call<RawResponseData>
//
//    @GET("users/current/durations")
//    fun getCodingTime(
//        @Query("date") date : String,
//        @Query("api_key") string : String
//    ): Call<RawResponseData>
//
//
//    @POST("signup")
//    @FormUrlEncoded
//    fun signup(@Field("email") email : String,
//               @Field("plain_password") password : String,
//               @Field("name") name : String
//    ): Call<DataModel.SignUpResponse>
//
//
//    @PUT("user/basic")
//    fun uploadBasicInfo(@Query("email") email : String,
//                        @Query("age") age : Int,
//                        @Query("gender") gender : Int,
//                        @Query("one_line") one_line : String?
//    ): Call<DataModel.PutResponse>

}