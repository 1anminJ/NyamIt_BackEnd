# NyamIt_BE - 건강 관리 웹 애플리케이션 백엔드

## 📌 **기본 정보**

- **프로젝트 이름:** NyamIt_BE
- **사용 기술 스택:**
  - **백엔드 프레임워크:** Spring Boot 3.5.0
  - **언어:** Java 17
  - **ORM:** Spring Data JPA
  - **DB:** MySQL
  - **보안/인증:** Spring Security, JWT (io.jsonwebtoken)
  - **빌드 도구:** Gradle
  - **기타:** Lombok, Jakarta Validation

## 📂 **전체 구조 요약**

```
src/main/java/com/minjeong/nyamit_be/
├── controller/          # REST API 엔드포인트 제공
│   ├── UserController.java
│   ├── CommunityController.java
│   ├── FastingController.java
│   ├── RecipeController.java
│   ├── MealController.java
│   └── SnackController.java
├── service/            # 비즈니스 로직 처리
│   ├── UserService.java
│   ├── CommunityService.java
│   ├── FastingService.java
│   ├── RecipeService.java
│   ├── MealService.java
│   └── SnackService.java
├── dto/               # 데이터 전송 객체
│   ├── SignUpDTO.java
│   ├── LoginDTO.java
│   ├── CommunityDTO.java
│   ├── ProfileUpdateDTO.java
│   ├── FastingRequestDTO.java
│   └── ...
├── entity/            # DB 테이블과 매핑되는 엔티티 클래스
│   ├── User.java
│   ├── Community.java
│   ├── FastingRecord.java
│   ├── Recipe.java
│   ├── Meal.java
│   └── Snack.java
├── repository/        # JPA 기반 데이터 접근 계층
│   ├── UserRepository.java
│   ├── CommunityRepository.java
│   ├── FastingRecordRepository.java
│   ├── RecipeRepository.java
│   ├── MealRepository.java
│   └── SnackRepository.java
├── security/          # 인증 관련 클래스
│   └── JwtUtil.java
├── config/           # 전역 설정
│   ├── SecurityConfig.java
│   └── WebConfig.java
└── exception/        # 예외 처리
    └── GlobalExceptionHandler.java
```

## 🚀 **기능 흐름 요약**

### 1) **회원가입 및 로그인 (JWT 발급 포함)**
- **회원가입:**  
  - `UserController.signup()` → `UserService.registerUser()`  
  - 회원 정보 저장 후 "회원가입 성공" 반환
- **로그인:**  
  - `UserController.login()` → `UserService.loginUser()`  
  - 로그인 성공 시 JWT 토큰 발급 및 반환
- **JWT 인증:**  
  - 이후 API 호출 시 `Authorization: Bearer <JWT>` 헤더 사용

### 2) **프로필 관리**
- **프로필 조회/수정:**  
  - `UserController.getProfile()`/`updateProfile()`  
  - JWT에서 userId 추출 → 해당 유저의 프로필 반환/수정

### 3) **커뮤니티 게시판**
- **글 목록 조회:**  
  - `CommunityController.getAll()` → `CommunityService.getAllPosts()`
- **글 작성:**  
  - `CommunityController.create()` → `CommunityService.createPost()`  
  - JWT에서 userId 추출, 본인 정보로 글 작성
- **글 수정/삭제:**  
  - `CommunityController.update()/delete()` → `CommunityService.updatePost()/deletePost()`  
  - JWT에서 userId 추출, 작성자 본인만 수정/삭제 가능

### 4) **금식 기록(Dashboard/Fasting)**
- **금식 기록 저장:**  
  - `FastingController.recordFasting()` → `FastingService.saveFastingRecord()`
- **주간 금식 기록 조회:**  
  - `FastingController.getWeeklyFasting()` → `FastingService.getWeeklyRecords()/countFastingDaysThisWeek()`

### 5) **기타 부가 기능**
- **식단/간식/레시피 관리:**  
  - 각각 `MealController`, `SnackController`, `RecipeController`에서 CRUD 제공

## 🔄 **API 흐름 요약**

### 주요 API 엔드포인트

| 기능 | HTTP 메서드 | 엔드포인트 | 컨트롤러 | 서비스 |
|------|-------------|------------|----------|--------|
| 회원가입 | POST | `/api/users/signup` | `UserController.signup()` | `UserService.registerUser()` |
| 로그인 | POST | `/api/users/login` | `UserController.login()` | `UserService.loginUser()` |
| 프로필 조회 | GET | `/api/users/profile` | `UserController.getProfile()` | `UserService.getProfile()` |
| 프로필 수정 | PUT | `/api/users/profile` | `UserController.updateProfile()` | `UserService.updateProfile()` |
| 커뮤니티 글 목록 | GET | `/api/community` | `CommunityController.getAll()` | `CommunityService.getAllPosts()` |
| 커뮤니티 글 작성 | POST | `/api/community` | `CommunityController.create()` | `CommunityService.createPost()` |
| 커뮤니티 글 수정 | PUT | `/api/community/{id}` | `CommunityController.update()` | `CommunityService.updatePost()` |
| 커뮤니티 글 삭제 | DELETE | `/api/community/{id}` | `CommunityController.delete()` | `CommunityService.deletePost()` |
| 금식 기록 저장 | POST | `/api/fasting/record` | `FastingController.recordFasting()` | `FastingService.saveFastingRecord()` |
| 주간 금식 조회 | GET | `/api/fasting/weekly/{userId}` | `FastingController.getWeeklyFasting()` | `FastingService.getWeeklyRecords()` |

### API 흐름 예시

```
1. 회원가입
   POST /api/users/signup
   → UserController.signup()
   → UserService.registerUser()

2. 로그인
   POST /api/users/login
   → UserController.login()
   → UserService.loginUser()
   → JwtUtil.generateToken()

3. 커뮤니티 글 작성
   POST /api/community
   → CommunityController.create()
   → CommunityService.createPost()
   → JwtUtil.extractUserIdFromRequest()
   → CommunityRepository.save()
```

## 🔐 **인증 처리 방식**

### JWT 토큰 구조
- **생성:** `JwtUtil.generateToken(userId, name)`
  - userId는 JWT의 subject(sub)에 저장
  - name은 claim에 저장
  - 만료시간: 24시간
  - 서명 알고리즘: HS256

### 인증 흐름
1. **로그인 성공 시:** JWT 토큰 발급 및 반환
2. **API 호출 시:** `Authorization: Bearer <JWT>` 헤더 포함
3. **토큰 검증:** `JwtUtil.getUserIdFromToken(token)` 또는 `extractUserIdFromRequest(request)`
4. **사용자 인증:** 추출된 userId 기반으로 사용자 정보 조회 및 권한 확인

### 주요 인증 관련 메서드
- `JwtUtil.generateToken()`: JWT 토큰 생성
- `JwtUtil.getUserIdFromToken()`: 토큰에서 userId 추출
- `JwtUtil.validateToken()`: 토큰 유효성 검증
- `JwtUtil.extractUserIdFromRequest()`: HTTP 요청에서 토큰 추출 및 userId 반환

## 📌 **특이사항 또는 개선포인트**

### **장점**
- ✅ Spring Boot 기반의 표준적인 구조로, 각 계층(controller, service, repository, dto, entity)이 명확히 분리되어 유지보수 용이
- ✅ JWT 기반 인증으로 RESTful API에 적합
- ✅ Lombok, JPA 등 최신 Java 생태계 활용
- ✅ 커뮤니티 게시판에서 작성자 본인만 수정/삭제 가능한 권한 관리 구현

### **보완점**
- ⚠️ 프론트엔드 정적 파일/템플릿이 없어, 실제 프론트엔드와의 연동 예시가 부족함
- ⚠️ 예외 처리(권한, 유효성 등) 세분화 필요
- ⚠️ JWT 시크릿 키가 코드에 하드코딩되어 있음 → 환경변수 또는 설정 파일로 분리 권장
- ⚠️ API 문서화(Swagger 등) 추가 시 외부 협업 및 유지보수에 유리

## 🛠 **실행 방법**

### 필수 요구사항
- Java 17 이상
- MySQL
- Gradle

### 실행 단계
1. **데이터베이스 설정**
   ```properties
   # application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nyamit_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

2. **애플리케이션 실행**
   ```bash
   ./gradlew bootRun
   ```

3. **API 테스트**
   - 기본 포트: 8080
   - API 엔드포인트: `http://localhost:8080/api/*`

## 📚 **주요 파일 참조**

### 컨트롤러 계층
- `UserController.java`: 사용자 인증 및 프로필 관리
- `CommunityController.java`: 커뮤니티 게시판 CRUD
- `FastingController.java`: 금식 기록 관리
- `RecipeController.java`: 레시피 관리
- `MealController.java`: 식사 기록 관리
- `SnackController.java`: 간식 기록 관리

### 서비스 계층
- `UserService.java`: 사용자 관련 비즈니스 로직
- `CommunityService.java`: 커뮤니티 관련 비즈니스 로직
- `FastingService.java`: 금식 기록 관련 비즈니스 로직

### 보안
- `JwtUtil.java`: JWT 토큰 생성, 검증, 사용자 정보 추출

### 엔티티
- `User.java`: 사용자 정보
- `Community.java`: 커뮤니티 게시글
- `FastingRecord.java`: 금식 기록
- `Recipe.java`: 레시피 정보
- `Meal.java`: 식사 정보
- `Snack.java`: 간식 정보

---

**개발자:** 민정  
**프로젝트 목적:** 건강 관리 및 커뮤니티 기능을 제공하는 웹 애플리케이션 백엔드 # NyamIt_BackEnd
