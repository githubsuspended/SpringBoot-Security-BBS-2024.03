package com.example.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dao.AnniversaryDao;
import com.example.security.entity.Anniversary;

@Service
public class AnniversaryServiceImpl implements AnniversaryService {
	@Autowired private AnniversaryDao annivDao;
	
	@Override
	public List<Anniversary> getAnnivListByDay(int suid, String sdate) {
		return annivDao.getAnnivList(suid, sdate, sdate);
	}

	@Override
	public List<Anniversary> getAnnivList(int suid, String startDay, String endDay) {
		return annivDao.getAnnivList(suid, startDay, endDay);
	}

	@Override
	public void insertAnniv(Anniversary anniv) {
		annivDao.insertAnniv(anniv);
	}

}
