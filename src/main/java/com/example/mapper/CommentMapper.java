package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.CommentListDto;
import com.example.vo.Comment;

@Mapper
public interface CommentMapper {

	List<CommentListDto> getCommentsByPostNo(int postNo);
	void insertComment(Comment comment);
	Comment getCommentByNo(int commentNo);
	void deleteComment(int commentNo);
}
