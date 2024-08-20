<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function getEmpListUpdateTest(){
		alert("getEmpListUpdateTest Run....")
		var arr = new Array();
		var item;
		
		<c:forEach items="${listEmp}" var="item">
			arr.push({
						empno:"${item.empno}",			
						ename:"${item.ename}",			
						deptno:"${item.deptno}"			
					 });
		</c:forEach>
		
		for(var i=0; i<arr.length;){
			alert("arr.empno->" + i + " : " + arr[i].empno);
			alert("arr.ename->" + i + " : " + arr[i].ename);
			i++;
			if(i>2) return;
		}
	}
	//javeScript에서 empList를 수정한 data를 json 데이텉로 전환 empController로 보내기 예제
		function getEmpListUpdate(){
			alert("getEmpListUpdate Run...")
			let empList  = [];
			const inputs = document.querySelectorAll('input[name="empno"], input[name="ename"], input[name="deptno"]');
			for (let i = 0; i < inputs.length; i+=3){
				const empno  = inputs[i+1].value;
				const ename  = inputs[i+2].value;
				const deptno = inputs[i+3].value;
				// 불러온 값들을 JSON 객체 형태로 만들어준다.
				const empItem = {
									"empno":empno
								,	"ename":ename
								,   "deptno":deptno
								};
				//JSON 객체를 배열 안에 넣어둔다.
				empList.push(empItem);
				if(i>5)break;
			}
			alert("JSON.stringify(empList) -> " +JSON.stringify(empList));
			
			if(empList.length > 0 ){
				
				$.ajax({
					url			: 'empListUpdate',
					contentType : 'application/json',
					data	    : JSON.stringify(empList),
					method  	: 'POST',
					dataType	: 'json',
					success		: function(response){
									console.log(response.updateResult);
									}
					});
				
				alert("Ajax empListUpdate 수행...");
			}
	}
		
</script>
</head>
<body>
	<h2>회원 정보3</h2>
		<table  id="empList">
			<tr><th>번호</th><th>사번</th><th>이름</th><th>업무</th><th>부서</th></tr>
		 	<c:forEach var="emp" items="${listEmp}" varStatus="status">
				<tr id="empListRow"><td>emp${status.index}</td>
				    <td>
				        <input type="hidden" class="deptno"  id="deptno" name="deptno" value="${emp.deptno }">
				        <input type="text"   class="empno"   id="empno"  name="empno"  value="${emp.empno }">${emp.empno }</td>
				    <td><input type="text"   class="ename"   id="ename"  name="ename"  value="${emp.ename }">${emp.ename }</td>
					<td>${emp.job }</td><td>${emp.deptno } 
					</td>
				</tr>    
		     
		     </c:forEach>
		</table>
	RestController LISTVO3: <input  type="button" id="btn_Dept3"
                                    value="empLISTTest 전송 "  
                                    onclick="getEmpListUpdateTest()"><p>
    RestController LISTVO3: <input  type="button" id="btn_Dept3"
                                    value="empLIST 전송 "  
                                    onclick="getEmpListUpdate()"><p>
                                   

	
	<h1>The End </h1>
</body>
</html>