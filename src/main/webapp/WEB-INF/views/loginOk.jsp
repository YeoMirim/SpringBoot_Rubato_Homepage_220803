<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- model에 실려온 값을 확인하고 경고창만 띄우는 기능을 가진 페이지 -->

	<%	
		int checkId = Integer.parseInt(request.getAttribute("checkIdValue").toString());
		int checkPw = Integer.parseInt(request.getAttribute("checkPwValue").toString());
	
		if (checkId == 0) {
	%>
		<script type="text/javascript">
			alert("입력하신 아이디는 존재하지 않습니다.\n다시 확인해주세요.")
			history.go(-1);
		</script>
	<%
		} else if(checkPw == 0) {		
	%>	
		<script type="text/javascript">
			alert("입력하신 비밀번호가 맞지 않습니다.\n다시 확인해주세요.")
			history.go(-1);
		</script>
	<%
		} else {
			response.sendRedirect("index");  // index 화면으로 보내고 session값이 생기면서 로그인됨
		}
	%>	
		
</body>
</html>