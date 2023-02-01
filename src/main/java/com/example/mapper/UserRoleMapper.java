package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.vo.UserRole;

@Mapper
public interface UserRoleMapper {

	void insertUserRole(UserRole userRole);
	void deleteUserRole(UserRole userRole);
	List<UserRole> getUserRolesByUserId(String userId);
}
