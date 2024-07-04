package com.example.security.service;

import java.util.List;

import com.example.security.entity.Anniversary;

public interface AnniversaryService {

	List<Anniversary> getAnnivListByDay(int suid, String sdate);

	List<Anniversary> getAnnivList(int suid, String startDay, String endDay);
	
	void insertAnniv(Anniversary anniv);
	
}
