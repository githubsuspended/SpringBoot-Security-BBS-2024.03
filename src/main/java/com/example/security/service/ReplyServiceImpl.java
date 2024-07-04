package com.example.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dao.ReplyDao;
import com.example.security.entity.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired private ReplyDao replyDao;
	
	@Override
	public List<Reply> getReplyList(int bid) {
		return replyDao.getReplyList(bid);
	}

	@Override
	public void insertReply(Reply reply) {
		replyDao.insertReply(reply);
	}

}
