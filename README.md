# 📘 BookMe - 도서 추천 웹 프로젝트

**BookMe**는 사용자의 선호 장르를 기반으로 책을 추천하고, Google Books API를 통해 도서를 검색할 수 있는 웹 프로젝트 입니다.  


---

## 🛠 사용 기술
- **IntelliJ IDEA**
- **Spring Boot 3.4.1**
- **JDK 17**
- **Thymeleaf**
- **Spring Security**
- **JPA (Hibernate)**
- **MySQL**
- **Google Books API**
- **HTML/CSS**

---

## ✨ 주요 기능

- ✅ 사용자 회원가입 / 로그인
- ✅ 선호 장르 설정 및 수정
- ✅ Google Books API 기반 도서 검색 (제목/저자)
- ✅ 추천 도서 자동 제공 (선호 장르 기반)
- ✅ 페이지네이션 및 도서 수 보기 설정
- ✅ 넷플릭스 스타일 UI 및 팝업 미리보기
- ✅ 검색 결과 정렬 옵션 (추후 확장 가능)

---

## 📁 프로젝트 디렉토리 구조

<pre>
src
└── main
    ├── java
    │   └── com.bookme
    │       ├── config
    │       │   └── SecurityConfig.java            # Spring Security 설정
    │       ├── controller
    │       │   ├── PreferenceController.java      # 장르 설정 컨트롤러
    │       │   ├── RecommendController.java       # 추천/검색 페이지 컨트롤러
    │       │   └── UserController.java            # 로그인/회원가입 처리
    │       ├── dto
    │       │   ├── Book.java                      # 책 정보 DTO
    │       │   └── GenreOption.java               # 장르 선택 항목
    │       ├── entity
    │       │   ├── GenrePreference.java           # 유저의 장르 설정 Entity
    │       │   └── User.java                      # 사용자 Entity
    │       ├── repository
    │       │   ├── GenrePreferenceRepository.java # 장르 설정 JPA 리포지토리
    │       │   └── UserRepository.java            # 사용자 JPA 리포지토리
    │       ├── security
    │       │   ├── CustomUserDetails.java         # UserDetails 구현체
    │       │   └── CustomUserDetailsService.java  # 로그인 인증 서비스
    │       ├── service
    │       │   ├── GoogleBooksService.java        # Google Books API 호출 로직
    │       │   ├── PreferenceService.java         # 장르 관련 서비스
    │       │   ├── RecommendService.java          # 도서 추천 서비스
    │       │   └── UserService.java               # 회원가입 서비스
    │       └── BookmeApplication.java             # Spring Boot 시작점
    │
    ├── resources
    │   ├── static
    │   │   ├── netflix-style.css                  # 기본 스타일
    │   │   ├── netflix-auth.css                   # 로그인/회원가입 스타일
    │   │   └── netflix-preference.css             # 장르 설정 스타일
    │   ├── templates
    │   │   ├── fragments
    │   │   │   └── pagination.html                # 페이지네이션 템플릿
    │   │   ├── login.html                         # 로그인 페이지
    │   │   ├── signup.html                        # 회원가입 페이지
    │   │   ├── preference.html                    # 선호 장르 설정 페이지
    │   │   └── recommend.html                     # 추천/검색 페이지
    │   └── application.yml                        # 환경설정 파일
</pre>
