<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myMatecmList.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<div class="jumbotron">
    <div class="container">
        <h1 class="display-4">내가 쓴 여행메이트 댓글 목록</h1>
    </div>
</div>

<main role="main">
    <div class="container">
        <form action="/mate/mymatecmlist.do" method="post">
                
        <!-- 게시물이 없는 경우 -->
        <c:if test="${empty mymatecmList }">
            <div class="row">등록된 댓글이 없습니다.</div>
        </c:if>
        
        <!-- 그렇지 않은 경우 -->
        <c:if test="${!empty mymatecmList }">
            <div>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>게시물제목</th>
                            <th>내용</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${mymatecmList}" var="mymatecmvo">
                            <c:url var="viewLink" value="./MateView.do">
                                <c:param name="mno" value="${mymatecmvo.mno}"/>
                                <c:param name="mnick" value="${mymatecmvo.mnick}"/>
                                <c:param name="pageNum" value="${pageNum}"/>
                                <c:param name="type" value="${type}"/>
                                <c:param name="keyword" value="${keyword}"/>
                            </c:url>
                            <tr>
                                <td>${mymatecmvo.mcmno}</td>
                                <td><a href="${viewLink}">${mymatecmvo.mtitle}</a></td>
                                <td>${mymatecmvo.mcmtext}</td>
                                <td>${mymatecmvo.mcmdate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        </form>
    </div>
</main>

<%@ include file="../include/footer.jsp" %>
    
</body>
</html>