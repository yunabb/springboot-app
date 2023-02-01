package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dto.UserDetailDto;
import com.example.security.AuthenticatedUser;
import com.example.security.LoginUser;
import com.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/info")
	public String info(@AuthenticatedUser LoginUser loginUser, Model model) {
		
		UserDetailDto userDetailDto = userService.getUserDetail(loginUser.getId());
		model.addAttribute("user", userDetailDto);
		
		return "user/detail";
	}
	
	@GetMapping("/delete")
	@AuthenticatedUser
	public String deleteform() {
		return "user/delete-form";
	}
	
	@PostMapping("/delete")
	public String delete(@AuthenticatedUser LoginUser loginUser, String password) {
		userService.deleteUser(loginUser.getId(), password);
		
		return "redirect:delete-success";	
	}
	
	@GetMapping("/delete-success")	
	public String deleteSuccess() {
		return "user/delete-success";	
	}
	
	@GetMapping("/password")
	@AuthenticatedUser
	public String passwordChangeForm() {
		return "user/password-form";
	}
	
	@PostMapping("/password")
	public String changePassword(@AuthenticatedUser LoginUser loginUser, 
			@RequestParam(name = "oldPassword") String oldPassword,
			@RequestParam(name = "password") String password) {
		
		userService.changePassword(loginUser.getId(), oldPassword, password);
		
		return "redirect:password-success";
	}
	
	@GetMapping("/password-success")
	public String passwordChangeSuccess() {
		return "user/password-success";
	}
	
	@GetMapping("/detail.json")
	@ResponseBody
	public UserDetailDto userDetail(@RequestParam("userId") String userId) {
		UserDetailDto dto = userService.getUserDetail(userId);
		
		return dto;
	}
	
}
