package com.example.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.security.entity.Like;

@Mapper
public interface LikeDao {

	@Select("select * from likes where bid=#{bid} and suid=#{suid}")
	Like getLike(int bid, int suid);
	
	@Select("select * from likes where lid=#{lid}")
	Like getLikeByLid(int lid);
	
	@Select("select * from likes where bid=#{bid}")
	List<Like> getLikeList(int bid);
	
	@Insert("insert into likes values(default, #{bid}, #{suid}, #{value})")
	void insertLike(Like like);
	
	// update likes set value=if(value=0,1,0) where lid=#{lid}
	@Update("update likes set value=#{value} where lid=#{lid}")
	void updateLike(Like like);
	
}
