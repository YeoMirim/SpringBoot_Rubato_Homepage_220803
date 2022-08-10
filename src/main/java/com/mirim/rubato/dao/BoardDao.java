package com.mirim.rubato.dao;

import java.util.ArrayList;
import com.mirim.rubato.dto.FBoardDto;
import com.mirim.rubato.dto.FileDto;
import com.mirim.rubato.dto.ReplyDto;

public interface BoardDao {

	public void fbWriteDao(String fbid, String fbtitle, String fbcontent) ;			// 자유게시판 글쓰기
	
	public ArrayList<FBoardDto> fbListDao();		// 자유게시판 글 목록 가져오기
	
	public FBoardDto fbViewDao(String fbnum);		// 자유게시판 글 내용 보기
	
	public void fbHitDao(String fbnum);		// 자유게시판 조회수 증가 함수
	
	public void fbdeleteDao(String fbnum);	// 자유게시판 글 삭제
	
//	[게시글 검색용 Dao]	
	public ArrayList<FBoardDto> fbTitleSearchList(String keyword);		// 자유게시판 제목으로 검색한 결과 리스트 가져오기
	public ArrayList<FBoardDto> fbContentSearchList(String keyword);	// 자유게시판 내용으로 검색한 결과 리스트 가져오기
	public ArrayList<FBoardDto> fbNameSearchList(String keyword);		// 자유게시판 작성자로 검색한 결과 리스트 가져오기

//	[파일 업로드용 Dao]
	public void fbfileInsertDao(int boardnum, String filename, String orifilename, String fileurl, String fileextension);
	//파일 업로드(파일이 첨부된 게시글번호, 변경된 파일이름, 원본 파일이름, 파일저장경로, 파일의 확장자)
	public FileDto fbGetFileInfoDao(String boardnum);	// 파일이 첨부된 게시글의 번호로 검색하여 첨부된 파일의 정보 불러오기

//	[댓글 작성용 Dao]
	public void rbwriteDao(int boardnum, String rbid, String rbcontent);	// 댓글 작성
	
//	[댓글 List Dao]
	public ArrayList<ReplyDto> rblistDao(int fbnum);	// 댓글이 달린 원 글의 게시판 번호로 검색하여 모든 댓글 리스트 반환


}
