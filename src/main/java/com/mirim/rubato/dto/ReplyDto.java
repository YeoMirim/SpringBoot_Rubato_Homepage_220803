package com.mirim.rubato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
	
	private int rbnum;	// 댓글 고유 번호
	private int boardnum;	// 댓글이 달린 게시글의 고유번호
	private String rbid;	// 덧글을 쓴 게시자의 아이디
	private String rbcontent;	// 덧글 내용
	private String rbdate;	// 덧글 쓴 날짜

}
