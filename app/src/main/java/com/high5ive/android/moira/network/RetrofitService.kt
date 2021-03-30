package com.high5ive.android.moira.network

import com.google.gson.annotations.SerializedName
import com.high5ive.android.moira.data.retrofit.*
import okhttp3.MultipartBody
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

    // FCM
    @POST("register")
    fun registerUser(
        @Header("X-AUTH-TOKEN") token: String,
        @Body body: FCMRegister
    ): Call<ResponseData>

    // FCM push test
    @POST("push")
    fun pushFCM(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<ResponseData>



    // 1. 로그인
    @POST("login")
    fun loginUser(
        @Body body: LoginInfo
    ): Call<LoginUser>


    // 1. 홈화면

    // 1-1. 홈
    @GET("home")
    fun getHome(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<HomeResponse>

    // 1-2. 홈화면 - 알람 목록
    @GET("home/alarm")
    fun getAlarm(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("page") page: Int
    ): Call<Alarm>


    // 2. 회원가입

    // 2-1. 회원가입
    @POST("signup")
    fun signupUser(
        @Header("X-AUTH-TOKEN") token: String,
        @Body body: SignUpInfo
    ): Call<ResponseData>

    // 2-2. 회원 가입 시 포지션 카테고리 목록
    @GET("signup/categories")
    fun getPositionCategories(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<PositionCategoryResponse>

    // 2-3. 모든 관심 태그 목록
    @GET("signup/hashtags")
    fun getHashTags(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<Hashtags>

    // 2-4. 닉네임 중복 검사
    @GET("signup/nickname")
    fun checkNickname(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("nickname") nickname : String
    ): Call<ResponseData>

    // 2-5. 선택한 포지션 카테고리의 상세 포지션 목록
    @GET("signup/positions")
    fun getPositionDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("positionCategoryId") positionCategoryId : Int
    ): Call<PositionDetail>




    // 3-1. 프로젝트(팀)

    // 3-1-1. 팀원 모집 - 모집글 리스트
    @GET("project")
    fun getRecruitPostList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("keyword") keyword : String?,
        @Query("page") page : Int?,
        @Query("position") position : String?,
        @Query("sort") sort : String?,
        @Query("tag") tag : String?
    ): Call<RecruitPost>

    // 3-1-2. 팀원 모집 - 팀 만들기
    @POST("project")
    fun makeNewRecruitPost(
        @Header("X-AUTH-TOKEN") token: String,
        @Body projectRequestDTO: NewRecruitPost
    ): Call<NewRecruitPostResponse>

    // 3-1-3. 팀원 모집 - 팀 모집글 상세
    @GET("project/{projectId}")
    fun getRecruitPostDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<RecruitPostDetail>

    // 3-1-4. 나의 팀 - 상세 페이지(팀장) - 팀 수정하기
    @PUT("project/{projectId}")
    fun editRecruitPostContent(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int,
        @Body projectModifyTitleRequestDTO : ProjectModifyTitleRequestDTO
    ): Call<ResponseData>


    // 3-1.5. 팀 만들기 - 이미지 리스트 추가
    @Multipart
    @POST("project/{projectId}/image")
    fun addTeamImageList(
        @Header("X-AUTH-TOKEN") token: String,
        @Part files : List<MultipartBody.Part>,
        @Path("projectId") projectId: Int
    ): Call<ResponseData>


    // 3-1-6. 팀 모집 - 팀 모집글 상세 - 좋아요
    @PUT("project/{projectId}/like")
    fun likeRecruitPost(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<ResponseData>

    //3-1-7. 팀 목록 - 완료한 팀 - 팀원 평가하기 - 팀원 목록
    @GET("project/{projectId}/member")
    fun getTeamMemberList(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<TeamMember>

    // 3-1-8. 나의 팀 - 상세 페이지(팀장) - 프로젝트 완료하기(수정)
    @PUT("project/{projectId}/status")
    fun editProjectStatus(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int,
        @Body projectModifyStatusRequestDTO : ProjectModifyStatusRequestDTO
    ): Call<ResponseData>



    // 3-2. 프로젝트(팀) 댓글

    // 3-2-1. 댓글창 - 댓글 삭제
    @DELETE("comment/{commentId}")
    fun deleteComment(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("commentId") commentId : Int
    ): Call<ResponseData>

    // 3-2-2. 댓글창 - 댓글 조회
    @GET ("comment/{projectId}")
    fun getComment(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<Comment>

    // 3-2-3. 댓글창 - 댓글 쓰기
    @POST ("comment/{projectId}")
    fun addComment(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("parentId") parentId : Int?,
        @Body projectCommentRequestDTO : CommentAdd,
        @Path("projectId") projectId : Int
    ): Call<CommentAddResponse>


    // 3-3. 프로젝트(팀) 지원

    // 3-3-1. 모집글 - 지원하기
    @POST ("apply")
    fun applyProject(
        @Header("X-AUTH-TOKEN") token: String,
        @Body projectApplyRequestDTO : ProjectApply
    ): Call<ResponseData>

    // 3-3-2. 마이페이지 - 지원한 글 - 지원 목록 - 지원 내역
    @GET("apply/{projectApplyId}")
    fun getProjectApplyDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectApplyId") projectApplyId: Int
    ): Call<ProjectApplyDetail>

    // 3-3-3. 지원서의 상태를 변경
    @PUT("apply/{projectApplyId}")
    fun modifyProjectApplyStatus(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectApplyId") projectApplyId: Int,
        @Body projectApplyModifyStatusRequestDTO: ProjectApplyModifyStatus
    ): Call<ResponseData>

    // 3-3-4. 프로젝트(팀) 지원 취소
    @DELETE("apply/{projectApplyId}")
    fun cancelProjectApply(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectApplyId") projectApplyId: Int
    ): Call<ResponseData>

    // 3-3-5. 마이페이지 - 작성한 글 - 지원자 목록
    @GET("apply/project/{projectId}")
    fun getProjectApplyUserList(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId: Int
    ): Call<ProjectApplyUser>


    // 3-4. 나의 프로젝트(팀)

    // 3-4-1. 팀 목록 - 나의팀 리스트 조회
    @GET("myProject")
    fun getMyTeamList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("sort") sort : String,
        @Query("status") status : String
    ): Call<MyTeam>

    // 3-4-2. 팀 목록 - 나의팀 상세 조회
    @GET("myProject/{projectId}")
    fun getMyTeamDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("projectId") projectId : Int
    ): Call<MyTeamDetail>



    // 4. 인재풀

    // 4-1. 팀원 찾기 - 인재풀
    @GET("pool")
    fun getUserPoolList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("page") page : Int,
        @Query("positionCategory") positionCategory : String,
        @Query("sortby") sortby : String
    ): Call<UserPool>

    // 4-2. 팀원 찾기 - 인재풀 - ON/OFF
    @POST("pool")
    fun registerUserPool(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<UserRegistration>

    // 4-3. 팀원 찾기 - 인재풀 - 게시글 좋아요 ON/OFF
    @POST("pool/like/{userPoolId}")
    fun onoffLikeUser(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userPoolId") userPoolId : Int
    ): Call<UserLike>

    // 4-4. 팀원 찾기 - 인재풀 - 게시글 상세(사용자정보)
    @GET("pool/profile/{userPoolId}")
    fun getUserPoolDetailInfo(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userPoolId") userPoolId : Int
    ): Call<UserPoolDetailInfo>

    // 4-5. 팀원 찾기 - 인재풀 - 게시글 상세(사용자평가)
    @GET("pool/review/{userPoolId}")
    fun getUserPoolDetailReview(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userPoolId") userPoolId : Int
    ): Call<UserPoolDetailReview>

    // 4-6. 팀원 찾기 - 인재풀 - 게시글 상세(사용자평가) - 모든 리뷰 조회
    @GET("pool/review/detail/{userPoolId}")
    fun getUserPoolDetailReviewAll(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("sort") sort : Int,
        @Query("userPoolId") userPoolId : Int
    ): Call<UserPoolDetailReviewAll>

    // 4-7. 팀원 찾기 - 인재풀 - 검색
    @GET("pool/search")
    fun searchUserPool(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("keyword") keyword : String
    ): Call<UserPoolSearch>






    // 5. 유저 리뷰

    // 5-1. 칭찬 뱃지 정보 불러오기
    @GET("compliment")
    fun getComplimentList(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<Compliment>

    // 5-2. 팀원 평가하기 - 특정 유저 평가하기
    @POST("review")
    fun reviewTeamMember(
        @Header("X-AUTH-TOKEN") token: String,
        @Body userReviewAddRequestDto: UserReviewAddRequestDto
    ): Call<TeamMemberReview>

    // 5-3. 유저의 "사용자 평가" 조회
    @GET("review/{targetId}")
    fun getApplyUserReview(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("targetId") targetId: Int
    ): Call<ApplyUserReview>

    // 5-4. 유저의 "모든 리뷰 내용" 조회
    @GET("review/detail/{userId}")
    fun getApplyUserReviewAll(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("userId") userId: Int,
        @Query("sort") sort: String
    ): Call<ApplyUserReviewAll>



    // 6-1. 마이페이지

    // 6-1-1. 마이페이지
    @GET("mypage")
    fun getMyPage(
        @Header("X-AUTH-TOKEN") token: String): Call<MyPage>

    // 6-1-2. 마이페이지 - 내가 지원한 글
    @GET("mypage/applied")
    fun getApplyPostList(
        @Header("X-AUTH-TOKEN") token: String): Call<ApplyPost>

    // 6-1-3. 마이페이지 - 내가 스크랩한 글 - 인재풀
    @GET("mypage/like/pool")
    fun getScrapUserPoolList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("positionCategory") positionCategory : String,
        @Query("sortby") sortby : String
    ): Call<ScrapUserPool>

    // 6-1-4. 마이페이지 - 내가 스크랩한 글 - 모집글
    @GET("mypage/like/project")
    fun getScrapRecruitPostList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("positionCategory") positionCategory : String,
        @Query("sortby") sortby : String
    ): Call<ScrapRecruitPost>

    // 6-1-5. 마이페이지 - 내가 작성한 글
    @GET("mypage/written")
    fun getWrittenPostList(
        @Header("X-AUTH-TOKEN") token: String): Call<WrittenPost>


    // 6-2. 마이페이지 - 프로필 수정

    // 6-2-1. 마이페이지 - 내 정보 수정하기 - 첫 화면
    @GET("mypage/edit")
    fun getMyProfileData(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<MyProfile>

    // 6-2-2.마이페이지 - 내 정보 수정하기 - 프로필 - 관심 태그 수정
    @PUT("mypage/edit/hashtag")
    fun editTag(
        @Header("X-AUTH-TOKEN") token: String,
        @Body myPageEditHashtagRequestDtoo: HashtagEdit
    ): Call<HashtagEditResponse>

    // 6-2-3. 마이페이지 - 내 정보 수정하기 - 프로필 - 이미지 수정
    @Multipart
    @PUT("mypage/edit/image")
    fun editProfileImage(
        @Header("X-AUTH-TOKEN") token: String,
        @Part image : MultipartBody.Part
    ): Call<ProfileImageEditResponse>

    // 6-2-4. 마이페이지 - 내 정보 수정하기 - 프로필 - 한 줄 소개 수정
    @PUT("mypage/edit/introduction")
    fun editIntroduction(
        @Header("X-AUTH-TOKEN") token: String,
        @Body myPageEditIntroductionRequestDto: IntroEdit
    ): Call<IntroEditResponse>

    // 6-2-5. 마이페이지 - 내 정보 수정하기 - 프로필 - 닉네임 수정
    @PUT("mypage/edit/nickname")
    fun editNickname(
        @Header("X-AUTH-TOKEN") token: String,
        @Body mypageEditNicknameRequestDto: NicknameEdit
    ): Call<NicknameEditResponse>

    // 6-2-6. 마이페이지 - 내 정보 수정하기 - 프로필 - 포지션 목록
    @GET("mypage/edit/position")
    fun getPosition(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<PositionResponse>

    // 6-2-7. 마이페이지 - 내 정보 수정하기 - 프로필 - 포지션 수정
    @PUT("mypage/edit/position")
    fun editPosition(
        @Header("X-AUTH-TOKEN") token: String,
        @Body myPageEditPositionRequestDto: PositionEdit
    ): Call<PositionEditResponse>





    // 6-3. 마이페이지 - 프로필 수정 - 선택정보

    // 6-3-1. 마이페이지 - 내 정보 수정하기 - 수상 추가 하기 팝업 - 등록하기
    @POST("mypage/edit/award")
    fun addMyProfileAward(
        @Header("X-AUTH-TOKEN") token: String,
        @Body userAwardAddRequestDto: MyProfileAwardAdd
    ): Call<MyProfileAwardAddResponse>


    // 6-3-2. 마이페이지 - 내 정보 수정하기 - 경력 추가 하기 팝업 - 등록하기
    @POST("mypage/edit/career")
    fun addMyProfileCareer(
        @Header("X-AUTH-TOKEN") token: String,
        @Body userCareerAddRequestDto: MyProfileCareerAdd
    ): Call<MyProfileCareerAddResponse>


    // 6-3-3. 마이페이지 - 내 정보 수정하기 - 자격증 추가 하기 팝업 - 등록하기
    @POST("mypage/edit/license")
    fun addMyProfileLicense(
        @Header("X-AUTH-TOKEN") token: String,
        @Body userLicenseAddRequestDto: MyProfileLicenseAdd
    ): Call<MyProfileLicenseAddResponse>


    // 6-3-4. 마이페이지 - 내 정보 수정하기 - 링크 추가 하기 팝업 - 등록하기
    @POST("mypage/edit/link")
    fun addMyProfileLink(
        @Header("X-AUTH-TOKEN") token: String,
        @Body userLinkAddRequestDto: MyProfileLinkAdd
    ): Call<MyProfileLinkAddResponse>


    // 6-3-5. 학과 검색
    @GET("mypage/edit/major-info")
    fun searchMajor(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("keyword") keyword: String
    ): Call<MyProfileMajorSearchResponse>


    // 6-3-6. 마이페이지 - 내 정보 수정하기 - 학력 추가 하기 팝업 - 등록하기
    @POST("mypage/edit/school")
    fun addMyProfileSchool(
        @Header("X-AUTH-TOKEN") token: String,
        @Body userSchoolAddRequestDto: MyProfileSchoolAdd
    ): Call<MyProfileSchoolAddResponse>


    // 6-3-7. 학교 검색
    @GET("mypage/edit/school-info")
    fun searchSchool(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("keyword") keyword: String
    ): Call<MyProfileSchoolSearchResponse>



    // 7. 유저
    // 7-1. 팀 목록 - 나의 태그 리스트
    @GET("user/tag")
    fun getUserTagList(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<UserTag>


    // 8. 채팅
    // 8-1. 채팅 방 목록 불러오기
    @GET("chatroom")
    fun getChatRoom(
        @Header("X-AUTH-TOKEN") token: String
    ): Call<Chat>

    // 8-2. 유저와 채팅 내용 불러오기
    @GET("chatroom/{chatRoomId}")
    fun getChatDetail(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("chatRoomId") chatRoomId: Int,
        @Query("page") page: Int
    ): Call<ChatDetail>

    // 8-3. 유저에게 메시지 보내기
    @POST("message")
    fun sendMessage(
        @Header("X-AUTH-TOKEN") token: String,
        @Body chatMessageSendRequestDto: ChatMessage
    ): Call<ChatMessageResponse>




    // 9. 신고 (댓글, 게시글, 채팅 신고하기)
    @POST("report")
    fun report(
        @Header("X-AUTH-TOKEN") token: String,
        @Body reportRequestDTO: Report
    ): Call<ResponseData>


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