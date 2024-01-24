# 🍊혼저옵서예 - 제주도 관광지 숙박예매 시스템🍊

![혼저옵서예 메인 페이지](https://github.com/wowssun/jejuOseyo/assets/119738419/db08db67-0a40-4745-b445-ce31869899d2)


## 💡프로젝트 소개

- 본 시스템은 제주도 지역 숙박 예매사이트입니다.
- 회원은 제주도 숙소 검색,예약 및 결제, 리뷰를 작성할 수 있습니다.
- 관심있는 숙소를 위시리스트에 담아둘 수 있으며, 장바구니에 추가할 수 있습니다.
- 회원은 자유게시판에서 글 조회, 작성, 수정, 삭제가 가능합니다.
- 호스트는 숙소 등록이 가능하고 등록된 숙소 정보를 수정하거나 삭제할 수 있습니다.

## 📑개발 기간 및 개발 환경

- **개발 기간** : 2023.07.21 ~ 2023.08.15
- **개발 환경**

<div>
	<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
	<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
	<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
	<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
	<img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"><br>
	<img src="https://img.shields.io/badge/oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white">
	<img src="https://img.shields.io/badge/eclipse-2C2255?style=for-the-badge&logo=eclipseide&logoColor=white">
	<img src="https://img.shields.io/badge/Apache-Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"><br>
	<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
	<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
</div>

## 📌 역할 분담 ( UI + 기능 )

### 🍀김나라
- **메인, 숙소, 리뷰, 위시리스트**
	- 숙소 전체 목록,상세 조회, 검색, 등록, 수정, 삭제
	- 리뷰 목록, 상세 조회, 등록, 수정, 삭제
	- 위시리스트 전체 목록, 등록, 삭제

### 🍀노지연
- **자유게시판**
	- 전체 목록, 상세 조회, 작성, 수정, 삭제

### 🍀손지희
- **일반, 회원, 호스트**
	- 로그인 및 로그아웃, 회원가입, 아이디 및 비밀번호 찾기
	- 마이페이지, 내 정보 수정, 탈퇴
	- 호스트 신청, 목록, 승인 및 거절, 거절내역

### 🍀우선제
- **장바구니, 예약 및 결제**
	- 장바구니 전체 목록, 등록, 삭제
	- 예약 전체 목록, 상세 조회, 예약 정보 조회, 예약 및 결제, 예약취소 및 결제취소
	- 아임포트 API

## ⚒️ 프로젝트 구조

```
📦 jejuOseyo
├─ .DS_Store
├─ .classpath
├─ .project
├─ .settings
├─ README.md
└─ src
   └─ main
      ├─ java
      │  └─ jejuOseyo
      │     ├─ controller
      │     │  ├─ CaYeController.java
      │     │  ├─ FreeRecordController.java
      │     │  ├─ MateController.java
      │     │  ├─ MemHoController.java
      │     │  └─ RoomController.java
      │     ├─ dao
      │     │  ├─ CartDAO.java
      │     │  ├─ FreeDAO.java
      │     │  ├─ HostDAO.java
      │     │  ├─ MateDAO.java
      │     │  ├─ MateapplyDAO.java
      │     │  ├─ MatecmDAO.java
      │     │  ├─ MatewishDAO.java
      │     │  ├─ MemberDAO.java
      │     │  ├─ ReviewDAO.java
      │     │  ├─ RggDAO.java
      │     │  ├─ RoomDAO.java
      │     │  └─ YeyakDAO.java
      │     ├─ filter
      │     │  ├─ EncodingFilter.java
      │     │  ├─ LogFilter.java
      │     │  └─ MemberFilter.java
      │     ├─ listener
      │     │  └─ DBCPInitListener.java
      │     ├─ util
      │     │  └─ DBConn.java
      │     └─ vo
      │        ├─ .DS_Store
      │        ├─ CartVO.java
      │        ├─ FreeVO.java
      │        ├─ HostVO.java
      │        ├─ MateVO.java
      │        ├─ MateapplyVO.java
      │        ├─ MatecmVO.java
      │        ├─ MatewishVO.java
      │        ├─ MemberVO.java
      │        ├─ PaymentVO.java
      │        ├─ ReviewVO.java
      │        ├─ RggVO.java
      │        ├─ RoomVO.java
      │        └─ YeyakVO.java
      └─ webapp
         ├─ META-INF
         │  ├─ MANIFEST.MF
         │  └─ context.xml
         ├─ WEB-INF
         │  ├─ lib
         │  └─ web.xml
         ├─ admin
         │  ├─ adMyInfo.jsp
         │  ├─ adPWChange.jsp
         │  ├─ hostInfo.jsp
         │  ├─ hostList.jsp
         │  ├─ hostMng.jsp
         │  ├─ hostRJList.jsp
         │  ├─ hostRQInfo.jsp
         │  ├─ hostRQList.jsp
         │  ├─ memInfo.jsp
         │  └─ memList.jsp
         ├─ cart
         │  ├─ cartList.jsp
         │  └─ yeyakCheck.jsp
         ├─ common
         │  ├─ hidSearch.jsp
         │  ├─ hostLogin.jsp
         │  ├─ hpwModify.jsp
         │  ├─ hpwSearch.jsp
         │  ├─ logout.jsp
         │  ├─ memLogin.jsp
         │  ├─ midSearch.jsp
         │  ├─ mpwModify.jsp
         │  └─ mpwSearch.jsp
         ├─ css
         │  └─ star.css
         ├─ free
         │  ├─ YeyakDAO.java
         │  ├─ freeList.jsp
         │  ├─ freeModify.jsp
         │  ├─ freeView.jsp
         │  ├─ freeWrite.jsp
         │  └─ myFreeCmList.jsp
         ├─ host
         │  ├─ crnumPopup.jsp
         │  ├─ hostJoin.jsp
         │  ├─ hostMyInfo.jsp
         │  ├─ hostMypage.jsp
         │  └─ hostmyPWModify.jsp
         ├─ include
         │  ├─ footer.jsp
         │  └─ header.jsp
         ├─ index.jsp
         ├─ mate
         │  ├─ mateList.jsp
         │  ├─ mateView.jsp
         │  ├─ mateWrite.jsp
         │  ├─ matecmList.jsp
         │  ├─ matecmWrite.jsp
         │  └─ myMatecmList.jsp
         ├─ mateapply
         │  ├─ mateApplyList.jsp
         │  └─ myMateApply.jsp
         ├─ matewish
         │  └─ myMatewishList.jsp
         ├─ member
         │  ├─ memJoin.jsp
         │  ├─ memMyInfo.jsp
         │  ├─ memMypage.jsp
         │  └─ mypagePWModify.jsp
         ├─ resources
         │  ├─ images
         │  └─ js
         │     └─ script.js
         ├─ review
         │  ├─ reviewAdd.jsp
         │  ├─ reviewInfo.jsp
         │  └─ reviewList.jsp
         ├─ rgg
         │  └─ rggList.jsp
         ├─ room
         │  ├─ allRoomList.jsp
         │  ├─ jusoPopup.jsp
         │  ├─ myRoomList.jsp
         │  ├─ roomAdd.jsp
         │  ├─ roomInfo.jsp
         │  ├─ roomModify.jsp
         │  ├─ searchRoomList.jsp
         │  └─ sortByStar.jsp
         └─ yeyak
            ├─ hoyeyakDetail.jsp
            ├─ hoyeyakList.jsp
            ├─ yeyakDetail.jsp
            └─ yeyakList.jsp
```

## 🔍 팀 PPT
### 팀 PPT
- 자세한 작업 과정과 완성 화면을 볼 수 있습니다.

[![구글 드라이브](https://github.com/wowssun/jejuOseyo/assets/119738419/1ebc8fd6-f200-4a30-a9d6-770931d508fe)](https://docs.google.com/presentation/d/1hm9K5nDFC9FWBCdyUJcuysFf_gDea3Rbd2zguFKkt4A/edit?usp=sharing)

### 블로그
- 프로젝트를 경험하며 공부한 기록입니다.

  [![hashnode](https://github.com/wowssun/jejuOseyo/assets/119738419/c95c2300-5109-4b94-b6c8-8964d396f915)](https://wowssun.hashnode.dev/)

