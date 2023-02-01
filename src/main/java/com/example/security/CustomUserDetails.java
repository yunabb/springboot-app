package com.example.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetails 인터페이스를 구현한 클래스다. <br/>
 * 사용자정보(아이디, 비밀번호, 권한정보)를 제공하는 클래스다. <br />
 * AuthenticationProvider가 사용자 인증을 할 때 이 객체의 정보를 이용한다. <br/>
 * AuthenticationProvider은 사용자 인증이 완료되면 Authentication객체를 새로 생성해서 사용자정보, 권한정보, 인증완료여부를 저장한다.
 */
public class CustomUserDetails extends LoginUser implements UserDetails {

	private static final long serialVersionUID = -1022370295119655350L;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public CustomUserDetails(String userId, 
			String password, 
			String userName, 
			Collection<? extends GrantedAuthority> authorities) {
		// 부모 생성자를 호출해서 LoginUser의 id, encryptPassword, name에 사용자아이디, 비밀번호, 이름이 저장되게 한다.
		super(userId, password, userName);
		// 사용자의 보유 권한을 authorities에 저장한다.
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return getEncryptPassword();
	}

	@Override
	public String getUsername() {
		return getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
