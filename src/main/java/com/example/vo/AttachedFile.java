package com.example.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("AttachedFile")
@Getter
@Setter
public class AttachedFile {

	private int postNo;
	private String filename;
}
