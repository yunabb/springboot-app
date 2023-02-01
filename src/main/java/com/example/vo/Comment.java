package com.example.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("Comment")
@Getter
@Setter
public class Comment {

	private int no;
	private String userId;
	private String content;
	private Date createdDate;
	private Date updatedDate;
	private int postNo;
}
