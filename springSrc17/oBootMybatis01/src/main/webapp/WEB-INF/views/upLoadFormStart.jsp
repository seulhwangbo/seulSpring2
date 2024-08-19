<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	UpLoad Image : <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${saveName}">
<!-- 	이미지나 파일은 post로 해야 한다 multipart/form-data도 필수이다 -->
	<form id="form1" action="uploadForm" method="post" enctype="multipart/form-data">
		<input type="file" name="file1"><p>
		<input type="text" name="title"><p>
		<input type="submit">
	</form>
</body>
</html>