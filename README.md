# TodayStock
[Go to English Description](#project-introduction)

## 프로젝트 소개
1인 프로젝트로 주식 정보를 담을 수 있는 collect 백엔드 서비스입니다. 

![프로젝트 구조도](https://private-user-images.githubusercontent.com/67682215/378679675-7f69049e-6ee6-45ab-9cd4-1e33c1ff5eb4.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3Mjk5OTQwODYsIm5iZiI6MTcyOTk5Mzc4NiwicGF0aCI6Ii82NzY4MjIxNS8zNzg2Nzk2NzUtN2Y2OTA0OWUtNmVlNi00NWFiLTljZDQtMWUzM2MxZmY1ZWI0LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDEwMjclMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQxMDI3VDAxNDk0NlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWVkMWI0ZGExMTJjMmYwNDcxNWY3ZGMwMjg0Njk0MmZmYjMwYTQ4NDI3MWYxYWViYmFmZTY0MTBlMTdhNjc5NWEmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.wH4VrZI6121cY7J8hBYrLtZQGZyHRRXJ7BP94gtT2l0)

### 프로젝트 개발 목적
이번에 미주, 국내 주식을 하면서 다양한 정보를 얻고 관리하지 않아 적정 매수, 매도 타이밍을 놓쳤던 경험으로 만들게 되었습니다.  
그리고 최근 학습 내용을 응용할 수 있는 프로젝트를 만들었습니다.
- GCP와 Google Cloud Vertex AI 모델로 Gemini를 사용했습니다.
- Kotlin, Spring, Kotlin DSL을 적용한 프로젝트입니다.

### 개발기간
- 2024.08.15 ~ 진행중
- [초기 개발 진행현황 관리 내역](https://github.com/Ilpyo-Yang/TodayStock/issues/5)
- [향후 개발 필요사항](https://github.com/Ilpyo-Yang/TodayStock/issues/6)

### 기술스택
- Kotlin 1.9.24, Spring Boot 3.3.4, JPA, Kotlin JDSL, Junit5, PostgreSQL
- Github Action, GCP(Compute Engine, Cloud SQL, Vertex AI, Artifact Registry, VPC), Flyway, Docker, Spring Rest API

#### [API 명세](https://todaystock-doc.tiiny.site/)

---

## Project Introduction
This is a solo project for a backend service that can collect stock information.

### Project Development Purpose
This project was created based on my experiences of missing optimal buying and selling opportunities due to the lack of management and access to various information while trading U.S. and domestic stocks.  
Additionally, it serves as an application of my recent learning.
- Utilizes GCP and Google Cloud Vertex AI models, specifically Gemini.
- Developed using Kotlin, Spring, and Kotlin DSL.

### Development Period
- From August 15, 2024 – Ongoing
- [Initial Development Progress Tracking](https://github.com/Ilpyo-Yang/TodayStock/issues/5)
- [Future Development Needs](https://github.com/Ilpyo-Yang/TodayStock/issues/6)

### Tech Stack
- Kotlin 1.9.24, Spring Boot 3.3.4, JPA, Kotlin JDSL, JUnit 5, PostgreSQL
- GitHub Actions, GCP (Compute Engine, Cloud SQL, Vertex AI, Artifact Registry, VPC), Flyway, Docker, Spring Rest API

### [API Documentation](https://todaystock-doc.tiiny.site/)
