package com.mirim.rubato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FBoardDto {
	
	private int fbnum;			// 게시판 번호
	private String fbid;		// 게시글 작성자 아이디
	private String mname;		// 게시글 작성자 이름 => 회원 테이블 필드
	private String fbcontent;	// 게시글 내용
	private String fbtitle;		// 게시글 제목
	private String fbdate;		// 게시글 작성일
	private int fbhit;			// 조회수
	private int fbreplycount;	// 댓글수
	
}
