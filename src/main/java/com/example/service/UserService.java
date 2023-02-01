package com.example.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.UserDetailDto;
import com.example.exception.AlreadyRegisteredEmailException;
import com.example.exception.AlreadyRegisteredUserIdException;
import com.example.exception.ApplicationException;
import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import com.example.vo.User;
import com.example.vo.UserRole;
import com.example.web.request.UserRegisterForm;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void registerUser(UserRegisterForm userRegisterForm) {
		User savedUser = userMapper.getUserById(userRegisterForm.getId());
		if (savedUser != null) {
			throw new AlreadyRegisteredUserIdException("["+userRegisterForm.getId()+"] 사용할 수 없는 아이디입니다.");
		}
		savedUser = userMapper.getUserByEmail(userRegisterForm.getEmail());
		if (savedUser != null) {
			throw new AlreadyRegisteredEmailException("["+userRegisterForm.getEmail()+"] 사용할 수 없는 이메일입니다.");
		}
		
		User user = new User();
		BeanUtils.copyProperties(userRegisterForm, user);
		
		// 비밀번호를 암호화한다.
		user.setEncryptPassword(passwordEncoder.encode(userRegisterForm.getPassword()));
		userMapper.insertUser(user);
		
		List<String> roleNames = userRegisterForm.getRoleName();
		for (String roleName : roleNames) {
			UserRole userRole = new UserRole(userRegisterForm.getId(), roleName);
			userRoleMapper.insertUserRole(userRole);			
		}
	}
	
	public UserDetailDto getUserDetail(String userId) {
		User user = userMapper.getUserById(userId);
		List<UserRole> userRoles = userRoleMapper.getUserRolesByUserId(userId);
		
		UserDetailDto userDetailDto = new UserDetailDto();
		BeanUtils.copyProperties(user, userDetailDto);
		userDetailDto.setUserRoles(userRoles);
		
		return userDetailDto;
	}
	
	public void changePassword(String userId, String oldPassword, String password) {
		User user = userMapper.getUserById(userId);
		if (user == null) {
			throw new ApplicationException("사용자 정보가 존재하지 않아서 비밀번호를 변경할 수 없습니다.");
		}
		if ("Y".equals(user.getDeleted())) {
			throw new ApplicationException("이미 탈퇴처리된 사용자는 비밀번호를 변경할 수 없습니다.");
		}

		// 암호화된 비밀번호를 비교할 때는 Password의 matches(rawPassword, encryptPassword);
		if (!passwordEncoder.matches(oldPassword, user.getEncryptPassword())) {
			throw new ApplicationException("비밀번호가 일치하지 않아서 비밀번호를 변경할 수 없습니다.");
		}
		
		user.setEncryptPassword(passwordEncoder.encode(password));
		userMapper.updateUser(user);		
	}
	
	public void deleteUser(String userId, String password) {
		User user = userMapper.getUserById(userId);
		if (user == null) {
			throw new ApplicationException("사용자 정보가 존재하지 않아서 탈퇴처리할 수 없습니다.");
		}
		if ("Y".equals(user.getDeleted())) {
			throw new ApplicationException("이미 탈퇴처리된 사용자입니다.");
		}
		if (!passwordEncoder.matches(password, user.getEncryptPassword())) {
			throw new ApplicationException("비밀번호가 일치하지 않아서 탈퇴처리할 수 없습니다.");
		}
		user.setDeleted("Y");
		userMapper.updateUser(user);
	}
	

	public List<User> getAllUsers() {
		return userMapper.getAllUsers();
	}
	
	public void addUserRole(UserRole userRole) {
		
	}
}
