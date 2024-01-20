<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>logout.jsp</title>
</head>
<body>



<%

/* String msg = (String) session.getAttribute("msg");
if (msg != null) {
	out.println("<script>alert('" + msg + "');</script>");
	session.removeAttribute("msg");
} */
	
	
	session.invalidate(); //세션무효화!! 로그아웃
	response.sendRedirect("../index.jsp");

%> 


</body>
</html>





