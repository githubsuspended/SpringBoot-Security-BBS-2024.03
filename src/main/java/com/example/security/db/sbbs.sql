SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS anniversary;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS securityUser;




/* Create Tables */

CREATE TABLE anniversary
(
	aid int NOT NULL AUTO_INCREMENT,
	suid int NOT NULL,
	aname varchar(20) NOT NULL,
	adate char(8) NOT NULL,
	isHoliday int DEFAULT 0,
	PRIMARY KEY (aid)
);


CREATE TABLE board
(
	bid int NOT NULL AUTO_INCREMENT,
	suid int NOT NULL,
	title varchar(256) NOT NULL,
	content varchar(4000),
	modTime datetime DEFAULT CURRENT_TIMESTAMP,
	isDeleted int DEFAULT 0,
	viewCount int DEFAULT 0,
	replyCount int DEFAULT 0,
	likeCount int DEFAULT 0,
	files varchar(512),
	PRIMARY KEY (bid)
);


CREATE TABLE likes
(
	lid int NOT NULL AUTO_INCREMENT,
	bid int NOT NULL,
	suid int NOT NULL,
	value int DEFAULT 0,
	PRIMARY KEY (lid)
);


CREATE TABLE reply
(
	rid int NOT NULL AUTO_INCREMENT,
	comment varchar(256) NOT NULL,
	regTime datetime DEFAULT CURRENT_TIMESTAMP,
	bid int NOT NULL,
	suid int NOT NULL,
	isMine int DEFAULT 0,
	PRIMARY KEY (rid)
);


CREATE TABLE schedule
(
	sid int NOT NULL AUTO_INCREMENT,
	suid int NOT NULL,
	sdate char(8) NOT NULL,
	title varchar(40) NOT NULL,
	place varchar(40),
	startTime char(5),
	endTime char(5),
	isImportant int DEFAULT 0,
	memo varchar(100),
	PRIMARY KEY (sid)
);


CREATE TABLE securityUser
(
	suid int NOT NULL AUTO_INCREMENT,
	uid varchar(64) NOT NULL,
	pwd char(60) NOT NULL,
	uname varchar(40) NOT NULL,
	email varchar(64),
	provider varchar(16) NOT NULL,
	regDate date DEFAULT (CURRENT_DATE),
	role varchar(16) DEFAULT '''ROLE_USER''' NOT NULL,
	isDeleted int DEFAULT 0,
	profile varchar(100),
	github varchar(40),
	insta varchar(40),
	location varchar(20),
	PRIMARY KEY (suid)
);



/* Create Foreign Keys */

ALTER TABLE likes
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reply
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE anniversary
	ADD FOREIGN KEY (suid)
	REFERENCES securityUser (suid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE board
	ADD FOREIGN KEY (suid)
	REFERENCES securityUser (suid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE likes
	ADD FOREIGN KEY (suid)
	REFERENCES securityUser (suid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reply
	ADD FOREIGN KEY (suid)
	REFERENCES securityUser (suid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE schedule
	ADD FOREIGN KEY (suid)
	REFERENCES securityUser (suid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



