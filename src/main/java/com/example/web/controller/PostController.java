package com.example.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.dto.PostDetailDto;
import com.example.exception.ApplicationException;
import com.example.security.AuthenticatedUser;
import com.example.security.LoginUser;
import com.example.service.PostService;
import com.example.web.request.PostModifyForm;
import com.example.web.request.PostRegisterForm;
import com.example.web.view.FileDownloadView;

@Controller
@RequestMapping("/post")
@SessionAttributes({"modifyPost"})
public class PostController {
	
	private final String directory = "c:/files";
	
	@Autowired
	private PostService postService;
	@Autowired
	private FileDownloadView fileDownloadView;	
	
	@GetMapping("/insert")
	@AuthenticatedUser
	public String form() {
		return "post/form";
	}
	
	@PostMapping("/insert")
	public String insertPost(@AuthenticatedUser LoginUser loginUser, PostRegisterForm form) throws IOException {
		MultipartFile upfile = form.getUpfile();
		if (!upfile.isEmpty()) {
			String filename = upfile.getOriginalFilename();
			form.setFilename(filename);
			
			FileCopyUtils.copy(upfile.getInputStream(), new FileOutputStream(new File(directory, filename)));
		}
		
		postService.insertPost(loginUser.getId(), form);
		
		return "redirect:list";
	}
	
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", required = false, defaultValue = "1") int page, Model model) {
		
		Map<String, Object> result = postService.getPosts(page);
		model.addAttribute("posts", result.get("posts"));
		model.addAttribute("pagination", result.get("pagination"));
		
		return "post/list";
	}
	
	@GetMapping("/read")
	public String read(@RequestParam("postNo") int postNo) {
		postService.increaseReadCount(postNo);
		
		return "redirect:detail?postNo=" + postNo;
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam("postNo") int postNo, Model model) {
		PostDetailDto postDetailDto = postService.getPostDetail(postNo);
		model.addAttribute("post", postDetailDto);
		
		return "post/detail";
	}
	
	@GetMapping("/download")
	public ModelAndView fileDownload(@RequestParam("filename") String filename) {
		File file = new File(directory, filename);
		if (!file.exists()) {
			throw new ApplicationException("["+filename+"] 파일이 존재하지 않습니다.");
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("file", file);
		mav.setView(fileDownloadView);
		
		return mav;
	}
	
	@PostMapping("/insert-comment")
	public String insertComment(@AuthenticatedUser LoginUser loginUser,
			@RequestParam("postNo") int postNo,
			@RequestParam("content") String content) {
		
		postService.insertComment(loginUser.getId(), content, postNo);
		
		return "redirect:detail?postNo=" + postNo;
	}
	
	@GetMapping("/delete-comment")
	public String deleteComment(@AuthenticatedUser LoginUser loginUser,
			@RequestParam("postNo") int postNo,
			@RequestParam("commentNo") int commentNo) {
		
		postService.deleteComment(loginUser.getId(), commentNo);
		
		return "redirect:detail?postNo=" + postNo;
	}
	
	@GetMapping("/modify")
	public String modifyform(@RequestParam("postNo") int postNo, Model model) {
		PostDetailDto dto = postService.getPostDetail(postNo);
		
		PostModifyForm form = new PostModifyForm();
		BeanUtils.copyProperties(dto, form);
		
		model.addAttribute("modifyPost", form);
		
		return "post/modify-form";
	}
	
	@PostMapping("/modify")
	public String modify(@ModelAttribute("modifyPost") PostModifyForm postModifyForm) {
		postService.updatePost(postModifyForm);
		
		return "redirect:detail?postNo=" + postModifyForm.getNo();
	}
	
}
