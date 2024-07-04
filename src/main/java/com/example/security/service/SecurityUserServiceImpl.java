package com.example.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dao.SecurityUserDao;
import com.example.security.entity.SecurityUser;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {
	@Autowired private SecurityUserDao securityDao;
	
	@Override
	public SecurityUser findBySuid(int suid) {
		return securityDao.findBySuid(suid);
	}

	@Override
	public SecurityUser findByUid(String uid) {
		return securityDao.findByUid(uid);
	}

	@Override
	public SecurityUser findByEmail(String email) {
		return securityDao.findByEmail(email);
	}

	@Override
	public List<SecurityUser> getSecurityUserList(int page) {
		int count = COUNT_PER_PAGE;
		int offset = (page - 1) * COUNT_PER_PAGE;
		return securityDao.getSecurityUserList(count, offset);
	}

	@Override
	public void insertSecurityUser(SecurityUser securityUser) {
		securityDao.insertSecurityUser(securityUser);
	}

	@Override
	public void updateSecurityUser(SecurityUser securityUser) {
		securityDao.updateUser(securityUser);
	}

	@Override
	public void deleteSecurityUser(String uid) {
		securityDao.deleteSecurityUser(uid);
	}

	@Override
	public int getSecurityUserCount() {
		return securityDao.getSecurityUserCount();
	}

}
