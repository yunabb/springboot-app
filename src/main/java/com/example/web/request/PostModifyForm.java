package com.example.web.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostModifyForm {

	private int no;
	private String userId;
	private String title;
	private String content;
	private int readCount;
	private int commentCount;
	private String deleted;
	private Date createdDate;
	private Date updatedDate;
}
