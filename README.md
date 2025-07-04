# KT-Library

## 모델
[www.msaez.io/#/98487008/storming/3a2cbc79628d3b4fe6cf3c4308a39b66](www.msaez.io/#/98487008/storming/3a2cbc79628d3b4fe6cf3c4308a39b66)

## 시작하기

이 저장소는 KT-Library 마이크로서비스를 위한 모노레포입니다. Docker Compose를 사용하여 백엔드 마이크로서비스, API 게이트웨이, 프론트엔드를 포함한 모든 서비스를 실행할 수 있습니다.

### 사전 준비 사항

서비스를 실행하기 전에 다음 도구들이 설치되어 있는지 확인하십시오:
*   Docker 및 Docker Compose
*   Node.js (Docker를 사용하지 않고 프론트엔드 개발/빌드 시)
*   Maven (Docker를 사용하지 않고 백엔드 개발/빌드 시)

### Docker Compose로 모든 서비스 실행하기

모든 마이크로서비스(백엔드, 게이트웨이, 프론트엔드) 및 Kafka를 시작하려면:

1.  **Docker 이미지 빌드 및 서비스 시작:**
    ```bash
    docker-compose -f build-docker-compose.yml up --build
    ```
    이 명령어는 필요한 모든 Docker 이미지를 빌드한 다음 `build-docker-compose.yml`에 정의된 서비스를 시작합니다.

2.  **애플리케이션 접속:**
    모든 서비스가 실행되면 브라우저에서 프론트엔드 애플리케이션에 접속할 수 있습니다:
    ```
    http://localhost:8088
    ```

### 개별 서비스 실행하기 (개발용)

Docker Compose 외부에서 개발 또는 디버깅을 위해 개별 서비스를 실행해야 하는 경우:

#### 백엔드 마이크로서비스 실행
각 마이크로서비스 디렉토리 내의 `README.md` 파일을 참조하여 특정 지침을 확인하십시오:
*   `point`
*   `author`
*   `customer`
*   `manuscript`
*   `ai`
*   `platform`

#### API 게이트웨이 실행 (Spring Gateway)
```bash
cd gateway
mvn spring-boot:run
```

#### 프론트엔드 실행
```bash
cd frontend
npm install
npm start
```

## API 테스트

`httpie` API 클라이언트를 사용하여 API를 테스트할 수 있습니다. API 게이트웨이는 `8088` 포트에서 실행됩니다.

*   **point**
    ```bash
    http :8088/points id="id" point="point" createAt="createAt" updateAt="updateAt" customerId="customerId"
    ```
*   **author**
    ```bash
    http :8088/authors id="id" email="email" name="name" introduction="introduction" isApproved="isApproved" createdAt="createdAt" updatedAt="updatedAt"
    ```
*   **customer**
    ```bash
    http :8088/customers id="id" name="name" email="email" isKtUser="isKTUser" createdAt="createdAt" updatedAt="updatedAt"
    http :8088/subsciptions id="id" isValid="isValid" startDate="startDate" endDate="endDate" createdAt="createdAt" updatedAt="updatedAt"
    ```
*   **manuscript**
    ```bash
    http :8088/manuscripts id="id" manuscriptTitle="manuscriptTitle" manuscriptContent="manuscriptContent" authorId="authorId" authorName="authorName" authorIntroduction="authorIntroduction" createdAt="createdAt" updatedAt="updatedAt"
    ```
*   **ai**
    ```bash
    http :8088/books id="id" summary="summary" coverUrl="coverUrl" bookUrl="bookUrl" createdAt="createdAt" updatedAt="updatedAt" manuscriptTitle="manuscriptTitle" manuscriptContent="manuscriptContent" authorId="authorID" authorName="authorName" introduction="introduction" category="category" price="price"
    ```
*   **platform**
    ```bash
    http :8088/bookShelves id="id" title="title" category="category" isBestSeller="isBestSeller" viewCount="viewCount" summary="summary" coverUrl="coverUrl" fileUrl="fileUrl" authorId="authorId" price="price" authorName="authorName" introduction="introduction"
    ```
    