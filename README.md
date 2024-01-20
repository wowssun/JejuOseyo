## 프로젝트 구조

```
📦 jejuOseyo
├─ .DS_Store
├─ .classpath
├─ .project
├─ .settings
│  ├─ .jsdtscope
│  ├─ org.eclipse.core.resources.prefs
│  ├─ org.eclipse.jdt.core.prefs
│  ├─ org.eclipse.wst.common.component
│  ├─ org.eclipse.wst.common.project.facet.core.xml
│  ├─ org.eclipse.wst.jsdt.ui.superType.container
│  └─ org.eclipse.wst.jsdt.ui.superType.name
├─ README.md
├─ build
│  ├─ .DS_Store
│  └─ classes
│     └─ jejuOseyo
│        ├─ controller
│        │  ├─ CaYeController.class
│        │  ├─ FreeRecordController.class
│        │  ├─ MateController.class
│        │  ├─ MemHoController.class
│        │  └─ RoomController.class
│        ├─ dao
│        │  ├─ CartDAO.class
│        │  ├─ FreeDAO.class
│        │  ├─ HostDAO.class
│        │  ├─ MateDAO.class
│        │  ├─ MateapplyDAO.class
│        │  ├─ MatecmDAO.class
│        │  ├─ MatewishDAO.class
│        │  ├─ MemberDAO.class
│        │  ├─ ReviewDAO.class
│        │  ├─ RggDAO.class
│        │  ├─ RoomDAO.class
│        │  └─ YeyakDAO.class
│        ├─ filter
│        │  ├─ EncodingFilter.class
│        │  ├─ LogFilter.class
│        │  └─ MemberFilter.class
│        ├─ listener
│        │  └─ DBCPInitListener.class
│        ├─ util
│        │  └─ DBConn.class
│        └─ vo
│           ├─ .DS_Store
│           ├─ CartVO.class
│           ├─ FreeVO.class
│           ├─ HostVO.class
│           ├─ MateVO.class
│           ├─ MateapplyVO.class
│           ├─ MatecmVO.class
│           ├─ MatewishVO.class
│           ├─ MemberVO.class
│           ├─ PaymentVO.class
│           ├─ ReviewVO.class
│           ├─ RggVO.class
│           ├─ RoomVO.class
│           └─ YeyakVO.class
└─ src
   ├─ .DS_Store
   └─ main
      ├─ .DS_Store
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
         ├─ .DS_Store
         ├─ META-INF
         │  ├─ MANIFEST.MF
         │  └─ context.xml
         ├─ WEB-INF
         │  ├─ lib
         │  │  ├─ cos.jar
         │  │  ├─ json-simple-1.1.1.jar
         │  │  ├─ ojdbc11.jar
         │  │  ├─ taglibs-standard-impl-1.2.5.jar
         │  │  └─ taglibs-standard-spec-1.2.5.jar
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