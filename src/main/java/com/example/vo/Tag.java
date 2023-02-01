package com.example.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("Tag")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tag {

	private int postNo;
	private String content;	
}
