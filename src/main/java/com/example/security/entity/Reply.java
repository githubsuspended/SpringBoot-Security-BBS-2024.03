package com.example.security.entity;

import java.time.LocalDateTime;

public class Reply {
	private int rid;
	private String comment;
	private LocalDateTime regTime;
	private int bid;
	private int suid;
	private int isMine;
	private String uname;
	
	public Reply() { }
	public Reply(String comment, int bid, int suid, int isMine) {
		this.comment = comment;
		this.bid = bid;
		this.suid = suid;
		this.isMine = isMine;
	}
	public Reply(int rid, String comment, LocalDateTime regTime, int bid, int suid, int isMine, String uname) {
		this.rid = rid;
		this.comment = comment;
		this.regTime = regTime;
		this.bid = bid;
		this.suid = suid;
		this.isMine = isMine;
		this.uname = uname;
	}
	
	@Override
	public String toString() {
		return "Reply [rid=" + rid + ", comment=" + comment + ", regTime=" + regTime + ", bid=" + bid + ", suid=" + suid
				+ ", isMine=" + isMine + ", uname=" + uname + "]";
	}
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public LocalDateTime getRegTime() {
		return regTime;
	}
	public void setRegTime(LocalDateTime regTime) {
		this.regTime = regTime;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getSuid() {
		return suid;
	}
	public void setSuid(int suid) {
		this.suid = suid;
	}
	public int getIsMine() {
		return isMine;
	}
	public void setIsMine(int isMine) {
		this.isMine = isMine;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
}
