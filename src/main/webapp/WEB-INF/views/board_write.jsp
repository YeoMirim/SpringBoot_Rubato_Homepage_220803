<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<!DOCTYPE html>
<html>
<head> 
<meta charset="utf-8">
<title>클래식기타 커뮤니티</title>
<link rel="stylesheet" type="text/css" href="${pagaContext.request.contextPath }/resources/css/common.css">
<link rel="stylesheet" type="text/css" href="${pagaContext.request.contextPath }/resources/css/header.css">
<link rel="stylesheet" type="text/css" href="${pagaContext.request.contextPath }/resources/css/footer.css">
<link rel="stylesheet" type="text/css" href="${pagaContext.request.contextPath }/resources/css/board_left.css">
<link rel="stylesheet" type="text/css" href="${pagaContext.request.contextPath }/resources/css/board_write_main.css">
</head>
<body>
<div id="wrap">
<header>
  <a href="index"><img id="logo" src="${pagaContext.request.contextPath }/resources/img/logo.png"></a>
<nav id="top_menu">
  <a href="index">HOME</a> |
  <%
  	String sessionId = (String) session.getAttribute("sessionId"); // 형변환 필요
  	if (sessionId == null) {
  %>
  <a href="index">LOGIN</a> | <a href="member_join">JOIN</a> |
  <%
  	} else {
  %>
  <a href="logout">LOGOUT</a> | MODIFY | 
  <%
  	}
  %>  
  NOTICE
</nav>
<nav id="main_menu">
  <ul>
    <li><a href="board_list">자유 게시판</a></li>
    <li><a href="#">기타 연주</a></li>
    <li><a href="#">공동 구매</a></li>
    <li><a href="#">연주회 안내</a></li>
    <li><a href="#">회원 게시판</a></li>
  </ul>
</nav>
</header> <!-- header -->
<aside>
  <%
		if (sessionId == null) {
	%>
	  <article id="login_box">
	    <img id="login_title" src="${pagaContext.request.contextPath }/resources/img/ttl_login.png">
	    <div id="input_button">
	    <form action="member_loginOk" method="post">  
	    <ul id="login_input">
	      <li><input type="text" name="mid"></li>
	      <li><input type="password" name="mpw"></li>
	    </ul>
	    <!-- 
	    <img id="login_btn" src="${pagaContext.request.contextPath }/resources/img/btn_login.gif">
	     -->
	     <input type="image" src="${pagaContext.request.contextPath }/resources/img/btn_login.gif">
	    </form>
	    </div> 
	    <div class="clear"></div>
	    <div id="join_search">
	      <img src="${pagaContext.request.contextPath }/resources/img/btn_join.gif" href="">
	      <img src="${pagaContext.request.contextPath }/resources/img/btn_search.gif">
	    </div>
	  </article>
  	<%
  		} else {	
  	%>
  		<article id="login_box">
	    <img id="login_title" src="${pagaContext.request.contextPath }/resources/img/ttl_login.png">
	    <div id="input_button">
	    	"<%=sessionId %>"님 로그인 중입니다<br><br>
	    	<input class="button" type="button" value="Logout" onclick="location.href='/logout'">
	    </div>
	  </article>
  	
  	<%
  		}
  	%>
  
  <nav id="sub_menu">
    <ul>
      <li><a href="board_list">+ 자유 게시판</a></li>
      <li><a href="#">+ 방명록</a></li>
      <li><a href="#">+ 공지사항</a></li>
      <li><a href="#">+ 등업 요청</a></li>
      <li><a href="#">+ 포토갤러리</a></li>
    </ul>
  </nav>
  <article id="sub_banner">
    <ul>
      <li><img src="${pagaContext.request.contextPath }/resources/img/banner1.png"></li>
      <li><img src="${pagaContext.request.contextPath }/resources/img/banner2.png"></li>		
      <li><img src="${pagaContext.request.contextPath }/resources/img/banner3.png"></li>
    </ul>	
  </article>
</aside> 

<section id="main">
  <img src="${pageContext.request.contextPath }/resources/img/comm.gif">
  <h2 id="board_title">자유 게시판 </h2>
  <div id="write_title"><h2>글쓰기</h2></div>
  
  <form action="board_writeOk" method="post" enctype="multipart/form-data">  <!-- 파일 첨부는 method가 반드시 post여야 함, 파일 전송은 인코딩되야함 -->
  <table>
 <!-- Join으로 처리할 예정이므로 이름과 이메일은 필요X
    <tr id="name">
      <td class="col1">이름</td>
      <td class="col2"><input type="text" name="fbname"></td>
    </tr>
    <tr id="name">
      <td class="col1">이메일</td>
      <td class="col2"><input type="text" name="fbemail"></td>
    </tr>
-->    
    <tr id="subject">
      <td class="col1">제목</td>
      <td class="col2"><input type="text" name="fbtitle"></td>
    </tr>		
    <tr id="content">
      <td class="col1">내용</td>
      <td class="col2"><textarea name="fbcontent"></textarea></td>
    </tr>
     	
    <tr id="upload">
      <td class="col1">업로드 파일</td>
      <td class="col2"><input type="file" name="uploadFiles"></td>
    </tr>
    	
  </table>
  <div id="buttons">
    <input type="image" src="${pageContext.request.contextPath }/resources/img/ok.png">
    <a href="board_list"><img src="${pageContext.request.contextPath }/resources/img/list.png"></a>
  </div>
  </form>
  
</section> <!-- section main -->
<div class="clear"></div>
<footer>
  <img id="footer_logo" src="${pagaContext.request.contextPath }/resources/img/footer_logo.gif">
  <ul id="address">
    <li>서울시 강남구 삼성동 1234 우 : 123-1234</li>  
    <li>TEL : 031-123-1234  Email : email@domain.com</li>
    <li>COPYRIGHT (C) 루바토 ALL RIGHTS RESERVED</li>
  </ul>
  <ul id="footer_sns">
    <li><img src="${pagaContext.request.contextPath }/resources/img/facebook.gif"></li>  
    <li><img src="${pagaContext.request.contextPath }/resources/img/blog.gif"></li>
    <li><img src="${pagaContext.request.contextPath }/resources/img/twitter.gif"></li>
  </ul>
</footer> <!-- footer -->

</div> <!-- wrap -->
</body>
</html>
