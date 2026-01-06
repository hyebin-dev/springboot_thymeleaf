# springboot_thymeleaf

코리아IT아카데미 (디지털컨버전스) 「공공데이터 융합 풀스택 개발자 양성과정Ⅰ」 강의에서 진행한  
**Spring Boot + Thymeleaf 실습 프로젝트 3종**을 정리한 레포지토리입니다.  
각 프로젝트는 **서로 독립적으로 실행**됩니다.

---

## 구성

- `www` : **Thymeleaf 템플릿 문법/모델 바인딩** 예제 (컨트롤러 → 뷰)
- `demo` : **MyBatis + MySQL + Spring Security** 기반 게시판/회원 실습
- `demo2` : **Spring Data JPA + Querydsl + Spring Security + 파일 업로드 + 댓글** 게시판 확장 실습

---

## 개발 환경

- Java: **17**
- Build Tool: **Gradle (Wrapper 포함)**
- Spring Boot
  - `www` : **3.4.12**
  - `demo` : **3.4.12**
  - `demo2` : **3.5.9**

---

## 실행 방법(공통)

각 프로젝트 폴더로 이동해서 실행합니다.

```bash
cd (폴더명)
./gradlew bootRun
````

---

## 실행 전 체크 (중요)

### 1) 포트 충돌

* `www`  : `server.port = 8088` (`www/src/main/resources/application.properties`)
* `demo` : `server.port = 8088` (`demo/src/main/resources/application.properties`)
* `demo2`: 민감 정보 포함으로 인해 `application.properties`(또는 `.yml`)를 레포지토리에 포함하지 않았습니다.
  → 실행 시 포트는 **직접 작성한 설정 파일 또는 실행 환경에 따라 결정**됩니다.

`demo`와 `www`는 동시에 실행하면 **8088 포트 충돌**이 발생합니다.

---

### 2) URL 매핑 패턴(`/board/*` 등) 주의

`demo`, `demo2` 컨트롤러는 클래스 레벨에 다음과 같은 매핑을 사용합니다.

* `demo`

  * `@RequestMapping("/board/*")`
  * `@RequestMapping("/user/*")`
* `demo2`

  * `@RequestMapping("/board/*")`
  * `@RequestMapping("/user/*")`
  * `@RequestMapping("/comment/*")`

---

# 1) www

(Thymeleaf 문법 / 모델 바인딩 예제)

## 포트

* `server.port = 8088`
* 파일: `www/src/main/resources/application.properties`

## 엔드포인트

| Method | Path    | Note                 |
| ------ | ------- | -------------------- |
| GET    | `/`     | index                |
| GET    | `/ex01` | list binding         |
| GET    | `/ex02` | query: `name`, `age` |
| GET    | `/ex03` | list / map / VO      |

---

# 2) demo

(MyBatis + MySQL + Spring Security)

## 기술 스택

* Spring Boot (Web, Thymeleaf)
* MyBatis (`mybatis-spring-boot-starter:3.0.5`)
* MySQL Connector/J
* Spring Security + thymeleaf extras
* thymeleaf-layout-dialect
* Lombok

## 포트

* `server.port = 8088`
* 파일: `demo/src/main/resources/application.properties`

## DB 설정 (환경변수)

```properties
spring.datasource.jdbc-url=jdbc:mysql://localhost:3306/springdb
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

실행 전 `DB_USERNAME`, `DB_PASSWORD` 환경변수가 필요합니다.

## MyBatis 매퍼

* `boardMapper.xml`

  * 조회 조건: `is_del = 'N'`
  * 삭제 처리: `update board set is_del = "Y" where bno = #{bno}`
  * 검색 조건 분리: `<sql id="search">`
* `userMapper.xml`

  * 회원가입 시 `ROLE_USER` 권한 insert

---

## 엔드포인트

**(컨트롤러 기준: 실제 매핑 패턴)**

### BoardController (`/board/*`)

| Method | Path                | Note         |
| ------ | ------------------- | ------------ |
| GET    | `/board/*/register` |              |
| POST   | `/board/*/register` |              |
| GET    | `/board/*/list`     |              |
| GET    | `/board/*/detail`   | query: `bno` |
| POST   | `/board/*/modify`   |              |
| GET    | `/board/*/remove`   | query: `bno` |

### UserController (`/user/*`)

| Method | Path             | Note     |
| ------ | ---------------- | -------- |
| GET    | `/user/*/signup` |          |
| POST   | `/user/*/signup` |          |
| GET    | `/user/*/login`  |          |
| GET    | `/user/*/list`   | ADMIN 권한 |
| GET    | `/user/*/modify` |          |
| POST   | `/user/*/modify` |          |
| GET    | `/user/*/remove` |          |

---

# 3) demo2

(JPA + Querydsl + 파일 업로드 + 댓글)

## 기술 스택

* Spring Boot (Web, Thymeleaf)
* Spring Data JPA
* Querydsl (JPA / APT, `src/main/generated`)
* MySQL Connector/J
* Spring Security
* Thumbnailator
* Lombok

## 설정 파일 없음

* `application.properties` / `.yml`은 **민감 정보 보호 목적**으로 레포지토리에 포함하지 않았습니다.
* 실행 시 datasource/JPA 설정을 직접 작성해야 합니다.

## 파일 업로드 경로 (하드코딩)

```java
D:\web_0826_khb\_myProject\_java\_fileUpload
```

다른 환경에서는 경로 수정이 필요합니다.

---

## 엔드포인트

**(컨트롤러 기준: 실제 매핑 패턴)**

### BoardController (`/board/*`)

| Method | Path                   | Note            |
| ------ | ---------------------- | --------------- |
| GET    | `/board/*/register`    |                 |
| POST   | `/board/*/register`    | multipart       |
| GET    | `/board/*/list`        | paging / search |
| GET    | `/board/*/detail`      | query: `bno`    |
| POST   | `/board/*/modify`      | multipart       |
| GET    | `/board/*/remove`      | query: `bno`    |
| DELETE | `/board/*/file/{uuid}` | file delete     |

### CommentController (`/comment/*`)

| Method | Path                           | Note |
| ------ | ------------------------------ | ---- |
| GET    | `/comment/*/list/{bno}/{page}` | JSON |
| POST   | `/comment/*/post`              |      |
| PUT    | `/comment/*/modify`            |      |
| DELETE | `/comment/*/remove/{cno}`      |      |

### UserController (`/user/*`)

| Method | Path             | Note  |
| ------ | ---------------- | ----- |
| GET    | `/user/*/join`   |       |
| POST   | `/user/*/join`   |       |
| GET    | `/user/*/login`  |       |
| GET    | `/user/*/modify` |       |
| POST   | `/user/*/modify` |       |
| GET    | `/user/*/list`   | ADMIN |
| GET    | `/user/*/remove` |       |


---

## 참고

* 본 레포는 **학습·실습 목적**의 코드 모음입니다.
* `demo2`는 설정 파일 미포함 및 업로드 경로 하드코딩으로 인해 실행 환경에 맞는 수정이 필요합니다.
* `demo`와 `www`는 동일 포트(8088)를 사용하므로 동시에 실행할 수 없습니다.

