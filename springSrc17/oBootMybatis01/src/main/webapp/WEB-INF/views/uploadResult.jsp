<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Image : ${savedName}><p>
	UpLoad Image : <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${savedName}">
	
	<a href="uploadFileDelete?delFile=${savedName}"> upload 삭제 TEST </a>
</body>
</html>