package com.example.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("Post")
@Getter
@Setter
public class Post {

	private int no;
	private String title;
	private String userId;
	private String content;
	private String deleted;
	private int readCount;
	private int commentCount;
	private Date updatedDate;
	private Date createdDate;
}
