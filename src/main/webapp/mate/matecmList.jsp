<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>matecmList.jsp</title>
</head>
<body>

<main role="main">
   <div class="container">
      <form action="/mate/mcmlist.do" method="post">
         <input type="hidden" name="pageNum" value="${pageNum }">

         <!-- 게시물이 없는 경우 -->
         <c:if test="${empty matecmList }">
            <div class="row">
               등록된 댓글이 없습니다.
            </div>
         </c:if>

         <!-- 그렇지 않은 경우 -->
         <c:if test="${!empty matecmList }">
         
            <div>
               <table class="table table-hover">
               <thead>
               		<tr>
                        <th>닉네임</th>
                        <th>작성일자</th>
                        <th>댓글 내용</th>
                     </tr>
                  </thead>
                  
                  <tbody>
                     <c:forEach items="${matecmList }" var="mateCmvo">
                     
                        <tr>
                           <td>${mateCmvo.mnick }</td>
                           <td>${mateCmvo.mcmdate }</td>
                           <td>${mateCmvo.mtext }</td></tr>
                     </c:forEach>
                  </tbody>
               </table>
         	</div>
         </c:if>

         <div class="text-right">
            <a href="./matecmWriteForm.do" class="btn btn-info btn-sm">
               댓글 등록</a>
         </div>
      	 </form>
   </div></main>

</body>
</html>