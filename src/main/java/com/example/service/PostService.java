package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.CommentListDto;
import com.example.dto.PostDetailDto;
import com.example.dto.PostListDto;
import com.example.exception.ApplicationException;
import com.example.mapper.CommentMapper;
import com.example.mapper.PostMapper;
import com.example.utils.Pagination;
import com.example.vo.AttachedFile;
import com.example.vo.Comment;
import com.example.vo.Post;
import com.example.vo.Tag;
import com.example.web.request.PostModifyForm;
import com.example.web.request.PostRegisterForm;

@Service
@Transactional
public class PostService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private PostMapper postMapper;

	// 게시글 등록 서비스
	public void insertPost(String userId, PostRegisterForm form) {
		// SPRING_POSTS 테이블에 게시글 정보 저장
		Post post = new Post();
		post.setUserId(userId);
		BeanUtils.copyProperties(form, post);		
		postMapper.insertPost(post);
		
		// SPRING_POST_ATTACHED_FILES 테이블에 게시글 첨부파일 정보 저장
		if (form.getFilename() != null) {
			AttachedFile attachedFile = new AttachedFile();
			attachedFile.setPostNo(post.getNo());
			attachedFile.setFilename(form.getFilename());
			
			postMapper.insertAttachedFile(attachedFile);
		}
		
		// SPRING_POST_TAGS 테이블에 게시글 태그정보 저장
		if (form.getTags() != null) {
			List<String> tags = form.getTags();
			for (String tagContent : tags) {
				Tag tag = new Tag(post.getNo(), tagContent);
				postMapper.insertTag(tag);
			}
		}
	}

	// 게시글 목록 조회 서비스
	public Map<String, Object> getPosts(int page) {
		int totalRows = postMapper.getTotalRows();
		Pagination pagination = new Pagination(page, totalRows);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("begin", pagination.getBegin());
		param.put("end", pagination.getEnd());
		
		List<PostListDto> posts = postMapper.getPosts(param);
		
		Map<String, Object> result = new HashMap<>();
		result.put("posts", posts);
		result.put("pagination", pagination);
		
		return result;
	}

	// 게시글의 조회수를 증가시키는 서비스
	public void increaseReadCount(int postNo) {
		Post post = postMapper.getPostByNo(postNo);
		if (post == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		if ("Y".equals(post.getDeleted())) {
			throw new ApplicationException("["+postNo+"] 번 게시글은 삭제된 게시글입니다.");
		}
		
		post.setReadCount(post.getReadCount() + 1);
		postMapper.updatePost(post);
	}
	
	// 게시글의 상세정보(게시글 정보와 댓글목록 정보)를 제공하는 서비스
	public PostDetailDto getPostDetail(int postNo) {
		// 게시글 정보 조회
		PostDetailDto postDetailDto = postMapper.getPostDetailByNo(postNo);
		if (postDetailDto == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		
		// 댓글 정보 조회
		List<CommentListDto> commentListDtos = commentMapper.getCommentsByPostNo(postNo);
		postDetailDto.setComments(commentListDtos);
		
		// 첨부파일 정보 조회
		List<AttachedFile> attachedFiles = postMapper.getAttachedFilesByPostNo(postNo);
		postDetailDto.setAttachedFiles(attachedFiles);
		
		// 태그 정보 조회
		List<Tag> tags = postMapper.getTagsByPostNo(postNo);
		postDetailDto.setTags(tags);
		
		return postDetailDto;
	}

	// 댓글을 등록하는 기능
	public void insertComment(String userId, String content, int postNo) {
		Post post = postMapper.getPostByNo(postNo);
		if (post == null) {
			throw new ApplicationException("["+postNo+"] 번 게시글이 존재하지 않습니다.");
		}
		
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setContent(content);
		comment.setPostNo(postNo);		
		commentMapper.insertComment(comment);
		
		post.setCommentCount(post.getCommentCount() + 1);
		postMapper.updatePost(post);		
	}

	public void deleteComment(String userId, int commentNo) {

		Comment comment = commentMapper.getCommentByNo(commentNo);
		if (comment == null) {
			throw new ApplicationException("["+commentNo+"] 번 댓글이 존재하지 않습니다.");			
		}
		commentMapper.deleteComment(commentNo);
		
		Post post = postMapper.getPostByNo(comment.getPostNo());
		post.setCommentCount(post.getCommentCount() - 1);
		postMapper.updatePost(post);		
	}

	public void updatePost(PostModifyForm postModifyForm) {
		Post post = new Post();
		BeanUtils.copyProperties(postModifyForm, post);
		
		postMapper.updatePost(post);
	}

}













