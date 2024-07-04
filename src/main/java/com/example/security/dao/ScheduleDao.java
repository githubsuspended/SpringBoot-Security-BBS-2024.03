package com.example.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.security.entity.Schedule;

@Mapper
public interface ScheduleDao {

	@Select("select * from schedule where sid=#{sid}")
	Schedule getSchedule(int sid);
	
	@Select("SELECT * FROM schedule"
			+ " WHERE suid=#{suid} AND sdate BETWEEN #{startDay} AND #{endDay}"
			+ " ORDER BY sdate, startTime")
	List<Schedule> getSchedList(int suid, String startDay, String endDay);

	@Insert("insert into schedule values"
			+ " (default, #{suid}, #{sdate}, #{title}, #{place}, #{startTime}, #{endTime},"
			+ " #{isImportant}, #{memo})")
	void insertSchedule(Schedule sched);
	
	@Update("update schedule set sdate=#{sdate}, title=#{title}, place=#{place},"
			+ " startTime=#{startTime}, endTime=#{endTime}, isImportant=#{isImportant},"
			+ " memo=#{memo} where sid=#{sid}")
	void updateSchedule(Schedule sched);
	
	@Delete("delete from schedule where sid=#{sid}")
	void deleteSchedule(int sid);
	
}
