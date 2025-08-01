# 🧾 HashSnap Backend Session-Auth-Task

## 📌 프로젝트 개요

본 프로젝트는 회원 가입 및 인증 시스템을 구현한 REST API입니다.
사용자 등록, 로그인, 비밀번호 재설정 등의 핵심 기능을 제공하며, JSON 기반의 데이터 통신을 통해 안전하고 효율적인 사용자 관리 시스템을 구축했습니다.

---

## 🚀 실행 방법 (설치 및 구동 가이드)

### 1. 환경 설정

- Java 17
- Node.js 22
- MySQL 8.0
- Gradle
- Git

### 2. 프로젝트 클론

```bash
git clone https://github.com/JUHYE0925/session-auth-task.git
```

### 3. 백엔드 실행

```bash
cd backend
./gradlew build
java -jar build/libs/HashSnap_Coding_Test.jar
```

### 4. 데이터베이스 설정

- `application.yml` 또는 `.env` 파일에 DB 접속 정보 입력
- 초기 스키마 및 데이터는 `init.sql` 혹은 DB 폴더의 `hashsnap` 파일 참고

---

## 🛠 사용 기술 스택

### Backend

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security(Session-based Authentication)
- MySQL
- Gradle
- Thymeleaf

## 📚 API 명세서

| 메서드    | URL                           | 설명                 | 요청 데이터                                                                                                                         | 응답 데이터                                                                                                        |
|--------|-------------------------------|--------------------|--------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| `GET`  | `/user/signup`                | 회원가입 화면 요청         | -                                                                                                                              | `View:user/signup`                                                                                            |
| `POST` | `/user/signup`                | 회원가입               | `{ "userName": "홍길동", "nickname" : "user01", "userPhone" : "01012345678", "userEmail": "test@email.com", "password": "1234" }` | `200 OK`                                                                                                      |
| `POST` | `/user/check-duplicate`       | 닉네임/이메일/전화번호 중복 체크 | `{ "nickname": "user01"} 또는 { "userPhone" : 01012345678 } 또는 { "userEmail" : "test@email.com" }`                               | `"isDuplicate" : true/false`                                                                                  |
| `GET`  | `/auth/login`                 | 로그인 화면 요청          | -                                                                                                                              | `View:auth/login`                                                                                             |
| `POST` | `/auth/login`                 | 로그인                | `{ "userPhone": "01012345678", "password": "1234" }`                                                                           | `200 OK + SESSIONID`                                                                                          |
| `GET`  | `/auth/logout`                | 로그아웃               | -                                                                                                                              | `Redirect:/main`                                                                                              |
| `GET`  | `/user/my-page`               | 나의 정보 조회           | -                                                                                                                              | `[ { "userName": "홍길동", "nickname": "user01", "userPhone": "01012345678", "userEmail" : "test@email.com" } ]` |
| `POST` | `/user/verification-requests` | 이메일 인증 코드 발송       | `{ "userEmail" : "test@email.com" }`                                                                                           | `200 OK`                                                                                                      |
| `POST` | `/user/verification`          | 이메일 인증 번호 확인       | `{ "userEmail" : "test@email.com", "code" : 166895 }`                                                                          | `{ "result" : true/false }`                                                                                   |
| `POST` | `/user/reset-password`        | 비밀번호 재설정           | `{ "newPassword": 5678, "confirmedPassword" : 5678, "userEmail": "test@email.com" }`                                           | `200 OK`                                                                                                      |

    
---

## ✅ 구현 범위 (완료된 기능 목록)

1. 회원 가입 기능
   - 회원가입 절차 구현


2. 로그인 기능
   - 식별 가능한 모든 정보를 통한 로그인 지원
   - 세션 기반 인증 구현
   

3. 내 정보 조회 기능
   - 로그인된 사용자의 프로필 정보 조회
   - 민감한 정보(비밀번호) 제외하고 반환
   

4. 비밀번호 재설정 기능
   - 비로그인 상태에서 비밀번호 재설정 가능
   - 이메일 인증
   - 새 비밀번호 설정 및 업데이트

---

## 🧐 특별히 신경 쓴 부분 (코드 리뷰 요청 포인트)

🔐 Session 기반 인증/인가 흐름
- HttpSession 기반으로 로그인 성공 시 사용자 정보 저장
- 로그인 시 usernameParameter, passwordParameter 커스텀 설정 (예: 전화번호 기반 인증)
- CustumLoginSuccessHandler를 통해 인증 후 처리 제어
- AuthFailHandler로 인증 실패 처리 (에러 응답, 로그 기록 등)

🛑 권한 및 URL 접근 제어
- /user/page → ROLE_USER 필요
- /admin/* → ROLE_ADMIN 필요
- /auth/*, /user/*, /main, / 등은 permitAll() 처리로 비인가 접근 허용
- 정적 리소스는 WebSecurityCustomizer 통해 인증 없이 접근 가능

🧹 로그아웃 및 세션 관리
- /auth/logout 경로로 로그아웃 처리
- 로그아웃 시 JSESSIONID 삭제 및 invalidateHttpSession(true) 적용
- 세션 최대 허용 수 1개 (maximumSessions(1)), 만료 시 /로 리다이렉트
- csrf().disable() 설정 주석으로 이유를 명시하면 좋음

---

## ✨ 추가 구현 사항 (요구사항 외 기능)

✉️ 이메일 인증 기반 비밀번호 재설정
- 인증 요청 → 이메일 전송 → 인증 코드 확인 → 재설정 페이지 접근 허용
- Redis를 활용한 인증 코드 임시 저장 방식 언급


