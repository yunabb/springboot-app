package com.example.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import com.example.vo.User;
import com.example.vo.UserRole;

/**
 * UserDetailsService 인터페이스를 구현한 클래스다.<br />
 * UserDetailsService 인터페이스의 UserDetails loadUserByUsername(String username) 메소드를 구현한 클래스다. <br />
 * loadUserByUsername(String username) 메소드는 사용자명(아이디, 이메일, 사원번호, 학생번호, 직원아이디 등)으로 데이터베이스에서
 * 사용자정보와 권한정보를 조회해서 UserDetails객체에 담아서 반환하는 메소드다.<br/>
 * UserDetailsService 인터페이스의 구현클래스에 재정의 loadUserByUsername(String username) 메소드가 반환하는 UserDetails 구현객체가
 * 인증과정에서 사용된다.
 * 
 * CustomUserDetailsService 클래스에서는 사용자정보와 권한정보를 조회해서 CustomUserDetails객체를 생성하고 담아서 반환했다.<br/>
 * 
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	// 사용자정보와 사용자권한 정보를 조회하기 위해서 UserMapper와 UserRoleMapper를 주입받는다.
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		// 사용자아이디는 인증작업을 수행하는 AuthenticationProvider로부터 전달받은 것이다.
		
		// 사용자아이디로 사용자 정보를 조회한다.
		User user = userMapper.getUserById(userId);
		// 사용자정보가 존재하지 않으면 예외를 던진다.
		if (user == null) {
			throw new UsernameNotFoundException("사용자 정보가 존재하지 않습니다.");
		}
		if ("Y".equals(user.getDeleted())) {
			throw new UsernameNotFoundException("탈퇴한 사용자입니다.");
		}
		// 사용자의 권한정보를 조회한다.
		List<UserRole> userRoles = userRoleMapper.getUserRolesByUserId(userId);
		// 조회된 권한정보로 GrantedAuthority객체를 생성한다.
		Collection<? extends GrantedAuthority> authorities = this.getAuthorities(userRoles);
		
		return new CustomUserDetails(
				user.getId(),				// 사용자 아이디 
				user.getEncryptPassword(), 	// 암호화된 사용자 비밀번호
				user.getName(), 			// 사용자이름
				authorities);				// 사용자가 보유한 권한정보
	}
	
	// 사용자 권한정보 목록을 전달받아서 GrantedAuthority객체의 집합으로 반환한다.
	private Collection<? extends GrantedAuthority> getAuthorities(List<UserRole> userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (UserRole userRole : userRoles) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRoleName());
			authorities.add(authority);
		}
		
		return authorities;
	}
}
