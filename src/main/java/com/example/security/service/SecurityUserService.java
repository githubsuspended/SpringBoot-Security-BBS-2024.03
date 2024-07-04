package com.example.security.service;

import java.util.List;

import com.example.security.entity.SecurityUser;

public interface SecurityUserService {
	public static final int COUNT_PER_PAGE = 10;

	SecurityUser findBySuid(int suid);
	
	SecurityUser findByUid(String uid);
	
	SecurityUser findByEmail(String email);
	
	List<SecurityUser> getSecurityUserList(int page);
	
	int getSecurityUserCount();
	
	void insertSecurityUser(SecurityUser securityUser);
	
	void updateSecurityUser(SecurityUser securityUser);
	
	void deleteSecurityUser(String uid);
	
}
