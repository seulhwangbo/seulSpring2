<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Mybatis JPA 회원 생성</h1>
	<div class="container">
		<form action="${pageContext.request.contextPath}/memberJpa/save" method="post">
			ID : <input type="text" id="id"   name="id"    	  required="required"><p>
			이름 : <input type="text" id="name" name="name" 	  placeholder="이름을 입력하세요"><p>
			비번 : <input type="text" id="pw"   name="password" placeholder="PW를 입력하세요"><p>
				<button type="submit">등록</button>
		</form>
		
		<a href="/members">JPA Member List 조회</a>
	</div><!-- /container -->
</body>
</html>