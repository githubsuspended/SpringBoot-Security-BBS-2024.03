package com.example.security.entity;

public class Like {
	private int lid;
	private int bid;
	private int suid;
	private int value;
	
	public Like() { }
	public Like(int bid, int suid, int value) {
		this.bid = bid;
		this.suid = suid;
		this.value = value;
	}
	public Like(int lid, int bid, int suid, int value) {
		this.lid = lid;
		this.bid = bid;
		this.suid = suid;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Like [lid=" + lid + ", bid=" + bid + ", suid=" + suid + ", value=" + value + "]";
	}
	
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
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
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
