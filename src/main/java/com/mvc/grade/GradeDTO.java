package com.mvc.grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDTO {
	private Long gradeCode;
	
	private int userCode;
	
	private Long listCode;
	
	private Double grade;
}
