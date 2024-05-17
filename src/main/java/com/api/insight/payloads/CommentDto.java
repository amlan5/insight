package com.api.insight.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
	
	private int commentId;
	
	private String commentContent;
	
	private UserDto user;

}
