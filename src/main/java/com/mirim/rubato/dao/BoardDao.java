package com.mirim.rubato.dao;

import java.util.ArrayList;
import com.mirim.rubato.dto.FBoardDto;

public interface BoardDao {

	public void fbWriteDao(String fbid, String fbtitle, String fbcontent) ;			// 자유게시판 글쓰기
	
	public ArrayList<FBoardDto> fbListDao();		// 자유게시판 글 목록 가져오기
	
	public FBoardDto fbViewDao(String fbnum);		// 자유게시판 글 내용 보기
	
	public void fbHitDao(String fbnum);		// 자유게시판 조회수 증가 함수
}
