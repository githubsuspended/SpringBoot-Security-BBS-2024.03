package com.example.security.service;

import java.util.List;

import com.example.security.entity.Reply;

public interface ReplyService {

	List<Reply> getReplyList(int bid);
	
	void insertReply(Reply reply);
	
}
