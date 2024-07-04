package com.example.security.service;

import java.util.List;

import com.example.security.entity.SchDay;
import com.example.security.entity.Schedule;

public interface ScheduleService {

	Schedule getSchedule(int sid);
	
	List<Schedule> getSchedList(int suid, String startDay, String endDay);
	
	List<Schedule> getSchedListByDay(int suid, String sdate);
	
	void insertSchedule(Schedule schedule);
	
	void updateSchedule(Schedule schedule);
	
	void deleteSchedule(int sid);
	
	SchDay generateSchDay(int suid, int day, String sdate, int date, int isOtherMonth);

}
