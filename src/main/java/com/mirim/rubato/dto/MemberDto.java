package com.mirim.rubato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   // lombok 기능
@NoArgsConstructor	// super 생성자
@AllArgsConstructor  // using 생성자(모든 생성자)
public class MemberDto {

	private String mid;
	private String mpw;
	private String mname;
	private String memail;
}
