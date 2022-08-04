package com.mirim.rubato.controller;

import java.io.File;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.mirim.rubato.dao.BoardDao;
import com.mirim.rubato.dao.MemberDao;
import com.mirim.rubato.dto.FBoardDto;
import com.mirim.rubato.dto.FileDto;

@Controller
public class HomeController {
	
	@Autowired
	private SqlSession sqlSession;

	
	@RequestMapping (value = "/")
	public String root() {
		
		return "index";
	}
	
	@RequestMapping (value = "/index")
	public String index() {
		
		return "index";
	}
	
	
	@RequestMapping (value = "/board_list")
	public String board_list(HttpServletRequest request, Model model) {
		
		String searchKeyword = request.getParameter("searchKeyword");
		String searchOption = request.getParameter("searchOption");
		
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		
		
//		[게시판 검색 파트]		
		ArrayList<FBoardDto> fbDtos = null;
			
		if (searchKeyword == null) {	// null인 경우 모든 리스트값을 출력
			fbDtos = boardDao.fbListDao();
		}
		else if (searchOption.equals("title")) {
			fbDtos = boardDao.fbTitleSearchList(searchKeyword);
		}
		else if (searchOption.equals("content")) {
			fbDtos = boardDao.fbContentSearchList(searchKeyword);
		}
		else if (searchOption.equals("writer")) {
			fbDtos = boardDao.fbNameSearchList(searchKeyword);
		}
//		검색파트 종료		
		
		int listcount = fbDtos.size();	// 게시판 글 목록의 글 개수
		
		model.addAttribute("fblist",fbDtos);
		model.addAttribute("listcount", listcount);
		
		return "board_list";
	}
	
	
	@RequestMapping (value = "/board_view")
	public String board_view(HttpServletRequest request, Model model) {
		
		String fbnum = request.getParameter("fbnum");
		int fbnumint = Integer.parseInt(fbnum);
		
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		
		boardDao.fbHitDao(fbnum);		// 조회수 증가 함수 호출
		
		FBoardDto fboardDto = boardDao.fbViewDao(fbnum);
		FileDto fileDto = boardDao.fbGetFileInfoDao(fbnum);
		
		model.addAttribute("fbView", fboardDto);
		model.addAttribute("fileDto",fileDto);
		model.addAttribute("rblist",boardDao.rblistDao(fbnumint));	// 댓글 리스트 가져와서 변환하기
		
		return "board_view";
	}
	
	
	@RequestMapping (value = "/board_write")
	public String board_write() {
		
		return "board_write";
	}
	

	@RequestMapping (value = "/member_join")
	public String member_join() {
		
		return "member_join";
	}
	
	
	@RequestMapping (value = "/member_joinOk", method = RequestMethod.POST)
	public String member_joinOk(HttpServletRequest request, Model model) {
		
		String memberid = request.getParameter("mid");
		String memberpw = request.getParameter("mpw");
		String membername = request.getParameter("mname");
		String memberemail = request.getParameter("memail");
		
		MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
		memberDao.memberJoinDao(memberid, memberpw, membername, memberemail);	// DB에 넣음

		
		HttpSession session = request.getSession();			// 세션에 올림 (로그인됨)
//		[방법 1]		
		session.setAttribute("sessionId", memberid);
		session.setAttribute("sessionName", membername);
		
		model.addAttribute("memberid", memberid);
		model.addAttribute("membername", membername);

		
//		[방법 2] index.jsp choose 방법 		
//		String sid = (String) session.getAttribute("sessionId");
//		String sname = (String) session.getAttribute("sessionName");
		
//		model.addAttribute("memberid", sid);
//		model.addAttribute("membername", sname);
		
	
		return "redirect:index";
	}
	
	// 로그아웃
	@RequestMapping (value = "/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();	// 세션 삭제 => 로그아웃
		
		return "redirect:index";
	}
	
	// 로그인 완료
	@RequestMapping (value = "/member_loginOk")
	public String member_loginOk(HttpServletRequest request, Model model) {
		
		String memberid = request.getParameter("mid");
		String memberpw = request.getParameter("mpw");
		
		MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
		
		int checkIdValue = memberDao.checkIdDao(memberid);
		// DB에 아이디가 존재하면 1이 반환, 없으면 0이 반환
		int checkPwValue = memberDao.checkPwDao(memberid, memberpw);
		// DB에 아이디와 비밀번호가 일치하는 계정이 존재하면 1이 반환, 없으면 0이 반환
		
		model.addAttribute("checkIdValue", checkIdValue);		// model에 실어서 보냄
		model.addAttribute("checkPwValue", checkPwValue);
		
		if (checkPwValue == 1) {
			
			HttpSession session = request.getSession();
			
			session.setAttribute("sessionId", memberid);	// 아이디 올림
		}
		
		
		return "loginOk";
	}

	//글 작성 완료
	@RequestMapping (value = "/board_writeOk", method = RequestMethod.POST)  // 첨부파일이 있기때문에 post로 처리
	public String board_writeOk(HttpServletRequest request, @RequestPart MultipartFile uploadFiles) throws Exception {  // MultipartFile이 업로드된 파일을 받음(Annotation으로 @RequestPart가 필요함) 
		
		String fbtitle = request.getParameter("fbtitle");
		String fbcontent = request.getParameter("fbcontent");
		
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);		// sqlsession으로 boardDao 생성
		HttpSession session = request.getSession();
		
		String fbid = (String) session.getAttribute("sessionId");		// ID를 session에서 뽑아냄
		
		if (fbid == null) {		// 로그인이 안된 경우
			fbid = "GUEST";		// 작성자는 guest로 출력됨	
		}
		
//		[파일 업로드 관련]
		if (uploadFiles.isEmpty()) {		// 파일 첨부 여부 판단 (true or false)
			boardDao.fbWriteDao(fbid, fbtitle, fbcontent);
			// 첨부된 파일이 없는 경우 제목과 내용만 DB에 업로드
		} else {
			boardDao.fbWriteDao(fbid, fbtitle, fbcontent);  // 게시글의 번호 (실행하자마자 fbnum값이  생성됨)
			
			ArrayList<FBoardDto> fbDtos = boardDao.fbListDao(); // 글 쓰자마자 가장 최근글 바로 꺼냄
			
			
			String orifilename = uploadFiles.getOriginalFilename();		// 기존의 파일의 이름 가져오기
			String fileextension = FilenameUtils.getExtension(orifilename).toLowerCase();	// 파일의 확장자만 가져옴(소문자로 변환시켜 추출)
			String fileurl = "D:\\Springboot_workspace\\SpringBoot_Rubato_Homepage\\src\\main\\resources\\static\\uploadfiles\\";  // 물리적 경로(url) 작성(파일의 Properties)
			String filename; // 랜덤으로 변경된 파일 이름(서버에 저장되는 파일의 이름)
			File destinationFile;	// java.io의 파일관련 클래스

			do {	// 동일한 파일명이 나오지 않을때까지 계속 돌림
				filename = RandomStringUtils.randomAlphanumeric(32) + "." + fileextension;
				// 영문대소문자와 숫자가 혼합된 랜덤 32자의 파일이름을 생성한 후 확장자 연결하여 서버에 저장될 파일의 이름 생성	
				destinationFile = new File(fileurl+filename);	// 파일 경로와 이름으로 초기화
			} while(destinationFile.exists());	// 같은 이름의 파일이 저장소에 존재하면 true 출력
			
			destinationFile.getParentFile().mkdir();
			uploadFiles.transferTo(destinationFile);	// 파일에 관한건 예외처리가 필요 (class 전체에 예외처리함)
			
			int boardnum = fbDtos.get(0).getFbnum();	
			// 가져온 게시글 목록 중에서 가장 최근에 만들어진 글(FBoardDto)을 불러와 게시글번호(fbnum)만 추출
			
			boardDao.fbfileInsertDao(boardnum, filename, orifilename, fileurl, fileextension);
		}
//		파일 업로드 구문 종료 		
		
		return "redirect:board_list";   // 게시판으로 돌려보냄
	}
	
	
	@RequestMapping (value = "replyOk")
	public String replyOk(HttpServletRequest request, Model model) {
		
		String boardnum = request.getParameter("boardnum");		// 댓글이 달릴 원 게시글의 고유번호
		String rbcontent = request.getParameter("rbcontent");	// 댓글의 내용
		int fbnum = Integer.parseInt(boardnum);		// int로 형변환 
		
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("sessionId");
		
		String rbid = null;
		
		if (sessionId == null) {
			rbid = "GUEST";			
		} else {
			rbid = sessionId;
		}
		
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		
		boardDao.rbwriteDao(fbnum, rbid, rbcontent);	
		
		model.addAttribute("fbView", boardDao.fbViewDao(boardnum));		// 원글 내용
		model.addAttribute("rblist", boardDao.rblistDao(fbnum));		// 댓글 목록
		
		return "board_view";
	}
	
}
