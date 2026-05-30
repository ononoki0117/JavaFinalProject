# 멍냥스토어

Spring Boot 기반의 반려동물 상품 쇼핑몰 웹 애플리케이션입니다.  
상품 조회, 장바구니, 결제 흐름, 로그인/회원가입, 반려동물 프로필 및 리뷰 확인 기능을 제공합니다.

## 프로젝트 소개

이 프로젝트는 반려동물과 관련된 상품을 둘러보고 구매할 수 있는 웹 서비스입니다.  
Thymeleaf 기반의 서버 사이드 렌더링 구조로 작성되었으며, JWT 인증과 세션을 함께 활용합니다.

## 주요 기능

- 상품 목록 조회
- 상품 상세 페이지
- 장바구니 담기 및 장바구니 합계 확인
- 결제 페이지 이동 및 결제 완료 처리
- 회원가입 / 로그인
- 반려동물 프로필 정보 표시
- 상품 리뷰 조회
- Spring Security 및 JWT 기반 인증 처리

## 기술 스택

- Java 17
- Spring Boot 3.3.x
- Spring Web
- Spring Security
- Spring Data MongoDB
- Spring Data JPA
- Thymeleaf
- JWT
- Gradle

## 프로젝트 구조

- `src/main/java`: 애플리케이션 핵심 로직
- `src/main/resources/templates`: Thymeleaf 템플릿
- `src/main/resources/static`: 정적 파일
- `src/main/resources/application.yml`: 서버 및 MongoDB 설정

## 실행 방법

### 1. 준비 사항

- JDK 17 이상
- MongoDB 접근 가능 환경
- Gradle 실행 환경

### 2. 설정

`src/main/resources/application.yml` 파일에서 MongoDB 연결 정보와 서버 포트를 확인하거나 환경에 맞게 수정하세요.

현재 기본 설정은 다음과 같습니다.

- 서버 포트: `10700`
- MongoDB URI: `spring.data.mongodb.uri`

### 3. 실행

```bash
./gradlew bootRun
```

Windows에서는 다음 명령을 사용할 수 있습니다.

```bash
gradlew.bat bootRun
```

브라우저에서 아래 주소로 접속합니다.

```text
http://localhost:10700
```

## 메모

- 이 저장소는 현재 MongoDB 설정이 `application.yml`에 포함되어 있습니다. 공개 저장소로 사용한다면 민감한 값은 환경 변수로 분리하는 것을 권장합니다.
- 결제 기능은 실제 PG 연동보다는 학습 및 포트폴리오용 흐름에 가깝습니다.

## 라이선스

MIT License를 사용합니다. 자세한 내용은 [LICENSE](./LICENSE) 파일을 참고하세요.

