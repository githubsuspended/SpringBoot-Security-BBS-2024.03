package com.example.security.entity;

import java.time.LocalDate;

public class SecurityUser {
	private int suid;
	private String uid;
	private String pwd;
	private String uname;
	private String email;
	private String provider;
	private LocalDate regDate;
	private String role;
	private int isDeleted;
	private String profile;
	private String github;
	private String insta;
	private String location;
	
	public SecurityUser() { }
	public SecurityUser(String uid, String pwd, String uname, String email, String provider, String profile) {
		this.uid = uid;
		this.pwd = pwd;
		this.uname = uname;
		this.email = email;
		this.provider = provider;
		this.profile = profile;
	}
	public SecurityUser(String uid, String pwd, String uname, String email, String provider, String profile,
			String github, String insta, String location) {
		this.uid = uid;
		this.pwd = pwd;
		this.uname = uname;
		this.email = email;
		this.provider = provider;
		this.profile = profile;
		this.github = github;
		this.insta = insta;
		this.location = location;
	}
	public SecurityUser(int suid, String uid, String pwd, String uname, String email, String profile, String github,
			String insta, String location) {
		this.suid = suid;
		this.uid = uid;
		this.pwd = pwd;
		this.uname = uname;
		this.email = email;
		this.profile = profile;
		this.github = github;
		this.insta = insta;
		this.location = location;
	}
	public SecurityUser(int suid, String uid, String pwd, String uname, String email, String provider, LocalDate regDate,
			String role, int isDeleted, String profile, String github, String insta, String location) {
		this.suid = suid;
		this.uid = uid;
		this.pwd = pwd;
		this.uname = uname;
		this.email = email;
		this.provider = provider;
		this.regDate = regDate;
		this.role = role;
		this.isDeleted = isDeleted;
		this.profile = profile;
		this.github = github;
		this.insta = insta;
		this.location = location;
	}
	
	public int getSuid() {
		return suid;
	}
	public void setSuid(int suid) {
		this.suid = suid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public LocalDate getRegDate() {
		return regDate;
	}
	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getGithub() {
		return github;
	}
	public void setGithub(String github) {
		this.github = github;
	}
	public String getInsta() {
		return insta;
	}
	public void setInsta(String insta) {
		this.insta = insta;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
