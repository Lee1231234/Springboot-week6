
# Readme 작성중

* 목적
   미니프로젝트 O-mat!의 백앤드를 개발하기위한 git입니다.
   

* 폴더  

  * main
    * Auth
        * Authority (멤버 권한)
    * Configuration
      * JwtSecurityConfiguration (JWT 토큰을 위한 필터)
      * S3Config (S3 사용을 위한 설정)
      * SecuriryConfiguration (스프링 부트 웹 시큐리티를 위한 설정)
    * domain
      * Comment
      * Likes
      * Member
      * Post 
      * RefreshToken (리프레쉬 토큰 저장)
      * Timestamped (생성 수정시간 제어)
      * UserDetailsIMPl ( 시큐리티 권한설정)
    * Dto(응답,요청을위한 Dto)
      * Request
        * CommentRequestDto
        * LoginRequestDto
        * MemberRequestDto
        * PostRequestDto
      * Response
        * CommentResponseDto
        * MemberResponseDto
        * PostResponseDto
        * ResponseDto
        * ResponseErrorDto
      * TokenDto
    * JWT
      * AccessDeniedHandlerException (에러처리)
      * AuthenticationEntryPointException (에러처리)
      * JwtFilter   (권한 검증)
      * TokenProvider (토큰생성 권한 검증 생성)
      
    * Repository (DB)
      * CommentRepository
      * LikesRepository
      * MemberRepository
      * PostRepository
      * RefreshTokenRepository
     
    * Service 
      * CommentService
      * MemberService
      * PostService
      * UserDetailsSeviceImpl 
    * util
      S3Uploader
    
    

* [API](https://www.notion.so/a12cec20b7954524a1c72ff9d2ac5238?v=67980b3e5fb145d88ae4c79aced92003)

노션으로 작성됨.
