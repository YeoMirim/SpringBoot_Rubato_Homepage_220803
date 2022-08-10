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
<link rel="stylesheet" type="text/css" href="${pagaContext.request.contextPath }/resources/css/board_view_main.css">
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
  <img src="${pagaContext.request.contextPath }/resources/img/comm.gif">
  <h2 id="board_title">자유 게시판 </h2>
  <div id="view_title_box"> 
    <span>${fbView.fbtitle }</span>
    <span id="info">작성자 : ${fbView.mname } | 조회수 : ${fbView.fbhit } | 작성일 : ${fbView.fbdate }</span>
  </div>	
  <p id="view_content">
    ${fbView.fbcontent }
  </p>
  
  <!-- 그림인 경우 화면에 보이게 만들기 -->
  <c:if test="${fileDto.fileextension == 'jpg' or fileDto.fileextension == 'gif' or fileDto.fileextension == 'png' or fileDto.fileextension == 'bmp'}">
  	<p id="image_view">
  		<img src="${pagaContext.request.contextPath }/resources/uploadfiles/${fileDto.filename }" >
  	</p>
  </c:if>
  <!-- 사진이 한번에 안뜨는 경우 uploadfile을 refresh해주거나 Window -> preference -> workspace 상단 3개 체크 -->
  <!-- 게시글 첨부파일 다운로드 기능 추가 -->
  <hr>
  <br>
  <p id="file_info">
  	 <span class="file_info"> 첨부파일 : 
  	 <a href="${pagaContext.request.contextPath }/resources/uploadfiles/${fileDto.filename }" style="text-decoration: none">
  	 ${fileDto.orifilename }</a></span> 	   	   
  </p>
  <br>
  <!-- 게시글 첨부기능 끝 -->
  
  <!-- 해당 글의 작성된 댓글 출력 list -->
  <table border="1"  cellpadding="0" cellspacing="0" width="750" style="border-style:hidden;" >
  	<c:forEach items="${rblist }" var="rbdto">
  		<tr>
  			<td colspan="2">
  				내용 : ${rbdto.rbcontent }
  			</td>
  		</tr>
  		<tr>	
  			<td>
  				댓글 작성자 : ${rbdto.rbid }
  			</td>
  			<td>
  				게시일 : ${rbdto.rbdate }
  			</td>
  		</tr>
  	</c:forEach>
  </table>
  <!-- 댓글 리스트 종료 -->
  
  <!-- 댓글 작성 기능 추가 -->	
  <div id="comment_box">
	  <form action="replyOk">
	  	<input type="hidden" name="boardnum" value="${fbView.fbnum }">	
	    <img id="title_comment" src="${pagaContext.request.contextPath }/resources/img/title_comment.gif">
	    <textarea name="rbcontent"></textarea>
	    <input type="image" id="ok_ripple" src="${pagaContext.request.contextPath }/resources/img/ok_ripple.gif">
	  </form>
  </div>
  <!-- 댓글 기능 끝 -->
  
  <div id="buttons">
  
	<%
		String sid = (String) session.getAttribute("sessionId");  // 형변환 필요
		
		if (sid == null) {
			sid = "GUEST";
		}
		
		String qid = request.getAttribute("boardId").toString();   // Model에서 넘어온 값 빼는 방법
		
								
		if ((sid != null) && (sid.equals(qid)) || (sid.equals("admin")) ) {   // 앞이 참일 경우에만 뒤의 조건을 봄, 아이디가 admin일 경우 모두 다 수정삭제 가능
	%>
  
    <a href="fbdelete?fbnum=${fbView.fbnum }"><img src="${pagaContext.request.contextPath }/resources/img/delete.png"></a>		
    <% 
		}
    %>
    
    <a href="board_list"><img src="${pagaContext.request.contextPath }/resources/img/list.png"></a>
    <a href="board_write"><img src="${pagaContext.request.contextPath }/resources/img/write.png"></a>			
  </div>
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