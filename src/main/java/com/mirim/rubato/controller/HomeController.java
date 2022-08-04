package com.mirim.rubato.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mirim.rubato.dao.BoardDao;
import com.mirim.rubato.dao.MemberDao;
import com.mirim.rubato.dto.FBoardDto;

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
		
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
		
		ArrayList<FBoardDto> fbDtos = boardDao.fbListDao();
		
		int listcount = fbDtos.size();	// 게시판 글 목록의 글 개수
		
		model.addAttribute("fblist",fbDtos);
		model.addAttribute("listcount", listcount);
		
		return "board_list";
	}
	
	
	@RequestMapping (value = "/board_view")
	public String board_view() {
		
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
	
	
	@RequestMapping (value = "/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();	// 세션 삭제 => 로그아웃
		
		return "redirect:index";
	}
	
	
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

	
	@RequestMapping (value = "/board_writeOk", method = RequestMethod.POST)  // 첨부파일이 있기때문에 post로 처리
	public String board_writeOk(HttpServletRequest request) {
		
		String fbtitle = request.getParameter("fbtitle");
		String fbcontent = request.getParameter("fbcontent");
		
		BoardDao boardDao = sqlSession.getMapper(BoardDao.class);		// sqlsession으로 boardDao 생성
		HttpSession session = request.getSession();
		
		String fbid = (String) session.getAttribute("sessionId");		// ID를 session에서 뽑아냄
		
		if (fbid == null) {		// 로그인이 안된 경우
			fbid = "GUEST";		// 작성자는 guest로 출력됨	
		}
		
		boardDao.fbWriteDao(fbid, fbtitle, fbcontent);
		
		return "redirect:board_list";   // 게시판으로 돌려보냄
	}
	
	
	
}
