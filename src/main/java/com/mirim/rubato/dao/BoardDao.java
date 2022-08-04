package com.mirim.rubato.dao;

import java.util.ArrayList;
import com.mirim.rubato.dto.FBoardDto;

public interface BoardDao {

	public void fbWriteDao(String fbid, String fbtitle, String fbcontent) ;			// 자유게시판 글쓰기
	
	public ArrayList<FBoardDto> fbListDao();		// 자유게시판 글 목록 가져오기
	
	public FBoardDto fbViewDao(String fbnum);		// 자유게시판 글 내용 보기
	
	public void fbHitDao(String fbnum);		// 자유게시판 조회수 증가 함수
	
	public ArrayList<FBoardDto> fbTitleSearchList(String keyword);		// 자유게시판 제목으로 검색한 결과 리스트 가져오기
	public ArrayList<FBoardDto> fbContentSearchList(String keyword);	// 자유게시판 내용으로 검색한 결과 리스트 가져오기
	public ArrayList<FBoardDto> fbNameSearchList(String keyword);		// 자유게시판 작성자로 검색한 결과 리스트 가져오기
	
	public void fbfileInsertDao(int boardnum, String filename, String orifilename, String fileurl, String fileextension);
	//파일 업로드(파일이 첨부된 게시글번호, 변경된 파일이름, 원본 파일이름, 파일저장경로, 파일의 확장자)
}
