package com.example.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("User")
@Getter
@Setter
public class User {

	private String id;
	private String encryptPassword;
	private String name;
	private String email;
	private String tel;
	private String photo;
	private String deleted;
	private Date updatedDate;
	private Date createdDate;
}
