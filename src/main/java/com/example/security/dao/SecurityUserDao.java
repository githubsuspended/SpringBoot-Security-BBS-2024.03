package com.example.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.security.entity.SecurityUser;

@Mapper
public interface SecurityUserDao {

	@Select("select * from securityUser where suid=#{suid}")
	SecurityUser findBySuid(int sid);
	
	@Select("select * from securityUser where uid=#{uid}")
	SecurityUser findByUid(String uid);
	
	@Select("select * from securityUser where email=#{email}")
	SecurityUser findByEmail(String email);
	
	@Select("select * from securityUser where isDeleted=0 order by suid desc"
			+ " limit #{count} offset #{offset}")
	List<SecurityUser> getSecurityUserList(int count, int offset);
	
	@Select("select count(suid) from securityUser where isDeleted=0")
	int getSecurityUserCount();
	
	@Insert("insert into securityUser values (default, #{uid}, #{pwd}, #{uname}, #{email}, "
			+ "#{provider}, default, default, default, #{profile}, #{github}, #{insta}, #{location})")
	void insertSecurityUser(SecurityUser securityUser);

	@Update("update securityUser set pwd=#{pwd}, uname=#{uname}, email=#{email}, profile=#{profile},"
			+ " github=#{github}, insta=#{insta}, location=#{location} where suid=#{suid}")
	void updateUser(SecurityUser securityUser);
	
	@Update("update securityUser set isDeleted=1 where uid=#{uid}")
	void deleteSecurityUser(String uid);
	
}
