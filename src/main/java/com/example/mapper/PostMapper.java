package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.PostDetailDto;
import com.example.dto.PostListDto;
import com.example.vo.AttachedFile;
import com.example.vo.Post;
import com.example.vo.Tag;

@Mapper
public interface PostMapper {

	void insertPost(Post post);
	
	int getTotalRows();
	List<PostListDto> getPosts(Map<String, Object> param);
	
	Post getPostByNo(int postNo);
	void updatePost(Post post);
	
	PostDetailDto getPostDetailByNo(int postNo);
	
	void insertAttachedFile(AttachedFile attachedFile);
	void insertTag(Tag tag);
	
	List<AttachedFile> getAttachedFilesByPostNo(int postNo);
	List<Tag> getTagsByPostNo(int postNo);
	

}
