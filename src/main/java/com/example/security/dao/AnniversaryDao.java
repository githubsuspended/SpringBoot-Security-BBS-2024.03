package com.example.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.security.entity.Anniversary;

@Mapper
public interface AnniversaryDao {

	@Select("SELECT * FROM anniversary WHERE (suid=#{suid} OR suid=1)"
			+ "  and adate BETWEEN #{startDay} AND #{endDay}"
			+ "  ORDER BY adate")
	List<Anniversary> getAnnivList(int suid, String startDay, String endDay);
	
	@Insert("insert into anniversary values(default, #{suid}, #{aname}, #{adate}, #{isHoliday})")
	void insertAnniv(Anniversary anniv);
	
}
