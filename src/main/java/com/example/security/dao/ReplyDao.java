package com.example.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.security.entity.Reply;

@Mapper
public interface ReplyDao {

	@Select("select r.*, u.uname from reply r"
			+ " join securityUser u on r.suid=u.suid where r.bid=#{bid}")
	List<Reply> getReplyList(int bid);
	
	@Insert("insert into reply values(default, #{comment}, default, #{bid}, #{suid}, #{isMine})")
	void insertReply(Reply reply);
	
}
