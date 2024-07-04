package com.example.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.dao.AnniversaryDao;
import com.example.security.dao.ScheduleDao;
import com.example.security.entity.Anniversary;
import com.example.security.entity.SchDay;
import com.example.security.entity.Schedule;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired private ScheduleDao schedDao;
	@Autowired private AnniversaryDao annivDao;
	
	@Override
	public Schedule getSchedule(int sid) {
		return schedDao.getSchedule(sid);
	}

	@Override
	public List<Schedule> getSchedList(int suid, String startDay, String endDay) {
		return schedDao.getSchedList(suid, startDay, endDay);
	}

	@Override
	public List<Schedule> getSchedListByDay(int suid, String sdate) {
		return schedDao.getSchedList(suid, sdate, sdate);
	}

	@Override
	public void insertSchedule(Schedule schedule) {
		schedDao.insertSchedule(schedule);
	}

	@Override
	public void updateSchedule(Schedule schedule) {
		schedDao.updateSchedule(schedule);
	}

	@Override
	public void deleteSchedule(int sid) {
		schedDao.deleteSchedule(sid);
	}

	@Override
	public SchDay generateSchDay(int suid, int day, String sdate, int date, int isOtherMonth) {
		List<Anniversary> annivList = annivDao.getAnnivList(suid, sdate, sdate);
		List<Schedule> schedList = schedDao.getSchedList(suid, sdate, sdate);
		int isHoliday = 0;
		List<String> aList = new ArrayList<>();
		for (Anniversary anniv: annivList) {
			aList.add(anniv.getAname());
			if (isHoliday == 0)
				isHoliday = anniv.getIsHoliday();
		}
		SchDay schDay = new SchDay(day, date, isHoliday, isOtherMonth, sdate, aList, schedList);
		return schDay;
	}

}
