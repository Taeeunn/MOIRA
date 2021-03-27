package com.high5ive.android.moira.network

import com.google.gson.annotations.SerializedName
import com.high5ive.android.moira.data.retrofit.*
import retrofit2.Call
import retrofit2.Response
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

    // 1. 로그인
    @POST("login")
    fun loginUser(
        @Body body: LoginInfo
    ): Call<LoginUser>


    // 2. 회원가입
    // 2-1. 닉네임 중복 검사
    @GET("signup/nickname")
    fun checkNickname(
        @Query("nickname") nickname : String
    ): Call<ResponseData>

    // 2-2. 회원 가입 시 포지션 카테고리 목록
    @GET("signup/categories")
    fun getPositionCategories(
    ): Call<PositionCategoryResponse>

    // 2-3. 선택한 포지션 카테고리의 상세 포지션 목록
    @GET("signup/positions")
    fun getPositionDetail(
        @Query("positionCategoryId") positionCategoryId : Int
    ): Call<PositionDetail>

    // 2-4. 모든 관심 태그 목록
    @GET("signup/hashtags")
    fun getHashTags(
    ): Call<Hashtags>

    // 2-5. 회원가입
    @POST("signup")
    fun signupUser(
        @Header("X-AUTH-TOKEN") token: String,
        @Body body: SignUpInfo
    ): Call<ResponseData>


    // 팀원 모집 - 모집글 리스트
    @GET("project")
    fun getRecruitPostList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("page") page : Int?,
        @Query("position") position : String?,
        @Query("sort") sort : String?,
        @Query("tag") tag : String?
    ): Call<RecruitPost>


    // 팀원 모집 - 모집글 리스트
    @GET("project/{projectId}")
    fun getRecruitPostDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<RecruitPostDetail>


    // 나의 팀 - 상세 페이지(팀장) - 팀 수정하기
    @PUT("project/{projectId}")
    fun editRecruitPostContent(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int,
        @Body projectModifyTitleRequestDTO : ProjectModifyTitleRequestDTO
    ): Call<ResponseData>

    // 팀 모집 - 팀 모집글 상세 - 좋아요
    @PUT("project/{projectId}/like")
    fun likeRecruitPost(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<ResponseData>


    // 팀원 모집 - 팀 만들기
    @POST("project")
    fun makeNewRecruitPost(
        @Header("X-AUTH-TOKEN") token: String,
        @Body projectRequestDTO: NewRecruitPost
    ): Call<NewRecruitPostResponse>



    //팀원 찾기 - 인재풀
    @GET("pool")
    fun getUserPoolList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("page") page : Int,
        @Query("positionCategory") positionCategory : String,
        @Query("sortby") sortby : String
    ): Call<UserPool>

    // 팀원 찾기 - 인재풀 - ON/OFF
    @POST("pool")
    fun registerUserPool(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<UserRegistration>

    // 팀원 찾기 - 인재풀 - 게시글 좋아요 ON/OFF
    @POST("pool/like/{userPoolId}")
    fun signupUser(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userPoolId") userPoolId : Int
    ): Call<UserLike>


    // 팀원 찾기 - 인재풀 - 게시글 상세(사용자정보)
    @GET("pool/profile/{userPoolId}")
    fun getUserPoolDetailInfo(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userPoolId") userPoolId : Int
    ): Call<UserPoolDetailInfo>

    // 팀원 찾기 - 인재풀 - 게시글 상세(사용자평가)
    @GET("pool/review/{userPoolId}")
    fun getUserPoolDetailReview(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userPoolId") userPoolId : Int
    ): Call<UserPoolDetailReview>

    // 팀원 찾기 - 인재풀 - 게시글 상세(사용자평가) - 모든 리뷰 조회
    @GET("pool/review/detail/{userPoolId}")
    fun getUserPoolDetailReviewAll(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("sort") sort : Int,
        @Query("userPoolId") userPoolId : Int
    ): Call<UserPoolDetailReviewAll>

    // 팀원 찾기 - 인재풀 - 검색
    @GET("pool/search")
    fun searchUserPool(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("keyword") keyword : String
    ): Call<UserPoolSearch>


    // 팀 목록 - 나의팀 리스트 조회
    @GET("myProject")
    fun getMyTeamList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("sort") sort : String,
        @Query("status") status : String
    ): Call<MyTeam>


    //팀 목록 - 나의팀 리스트 조회
    @GET("myProject/{projectId}")
    fun getMyTeamDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<MyTeamDetail>


    // 나의 팀 - 상세 페이지(팀장) - 프로젝트 완료하기(수정)
    @PUT("project/{projectId}/status")
    fun editProjectStatus(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int,
        @Query("projectModifyStatusRequestDTO") projectStatus : ProjectModifyStatusRequestDTO
    ): Call<ResponseData>


    //팀 목록 - 완료한 팀 - 팀원 평가하기 - 팀원 목록
    @GET("project/{projectId}/member")
    fun getTeamMemberList(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<TeamMember>

    //팀 목록 - 완료한 팀 - 팀원 평가하기 - 팀원 목록
    @GET("compliment")
    fun getComplimentList(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<Compliment>



    // 팀원 평가하기 - 특정 유저 평가하기
    @POST("review")
    fun reviewTeamMember(
        @Header("X-AUTH-TOKEN") token: String,
        @Body userReviewAddRequestDto: UserReviewAddRequestDto
    ): Call<TeamMemberReview>




    // 마이페이지
    @GET("mypage")
    fun getMyPage(
        @Header("X-AUTH-TOKEN") token: String): Call<MyPage>


    // 마이페이지 - 내가 작성한 글
    @GET("mypage/written")
    fun getWrittenPostList(
        @Header("X-AUTH-TOKEN") token: String): Call<WrittenPost>


    // 마이페이지 - 내가 지원한 글
    @GET("mypage/applied")
    fun getApplyPostList(
        @Header("X-AUTH-TOKEN") token: String): Call<ApplyPost>


    // 마이페이지 - 내가 스크랩한 글 - 모집글
    @GET("mypage/like/project")
    fun getScrapRecruitPostList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("positionCategory") positionCategory : String,
        @Query("sortby") sortby : String
    ): Call<ScrapRecruitPost>

    // 마이페이지 - 내가 스크랩한 글 - 모집글
    @GET("mypage/like/pool")
    fun getScrapUserPoolList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("positionCategory") positionCategory : String,
        @Query("sortby") sortby : String
    ): Call<ScrapUserPool>


    // 마이페이지 - 내 정보 수정하기 - 첫 화면
    @GET("mypage/edit")
    fun getMyProfileData(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<MyProfile>


    // 마이페이지 - 내 정보 수정하기 - 프로필 관련 정보 수정하기(프로필 사진 제외)
    @PUT("mypage/edit/profile")
    fun editMyProfile(
        @Header("X-AUTH-TOKEN") token: String,
        @Body myPageEditProfileUpdateRequestDto: MyPageEditProfileUpdateRequestDto
    ): Call<EditProfile>



//    @GET("/woof.json?ref=apilist.fun")
//    fun getInfo() : Call<DataClass>

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