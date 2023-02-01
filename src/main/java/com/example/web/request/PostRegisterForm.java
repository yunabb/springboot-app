package com.example.web.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRegisterForm {

	private String title;
	private String content;
	private MultipartFile upfile;
	private List<String> tags;
	private String filename;
}
