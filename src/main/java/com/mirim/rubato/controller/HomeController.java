package com.mirim.rubato.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mirim.rubato.dao.MemberDao;

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
	public String board_list() {
		
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
		
		HttpSession session = request.getSession();
		session.setAttribute("sessionId", memberid);
		session.setAttribute("sessionName", membername);
		
		return "redirect:index";
	}

}
