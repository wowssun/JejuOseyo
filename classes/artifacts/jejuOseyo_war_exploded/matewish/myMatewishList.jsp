<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myMatewishList.jsp</title>
</head>
<body>

<%@ include file="../include/header.jsp" %>

<div class="jumbotron">
    <div class="container">
        <h1 class="display-4">여행메이트 위시리스트(찜) 전체 목록</h1>
    </div>
</div>

<main role="main">
    <div class="container">
        <form action="/mate/mymatecmlist.do" method="post">
                
        <!-- 등록된 찜이 없는 경우 -->
        <c:if test="${empty matewishList }">
            <div class="row">위시리스트(찜)이 비었습니다.</div>
        </c:if>
        
        <!-- 그렇지 않은 경우 -->
        <c:if test="${!empty matewishList }">
            <div>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>게시물 제목</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${matewishList}" var="matewishvo">
                            <c:url var="viewLink" value="./MateView.do">
                                <c:param name="mno" value="${matewishvo.mno}"/>
                                <c:param name="mnick" value="${matewishvo.mnick}"/>
                                <c:param name="pageNum" value="${pageNum}"/>
                                <c:param name="type" value="${type}"/>
                                <c:param name="keyword" value="${keyword}"/>
                            </c:url>
                            <tr>
                                <td>${matewishvo.mwno}</td>
                                <td><a href="${viewLink}">${matewishvo.mtitle}</a></td>
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