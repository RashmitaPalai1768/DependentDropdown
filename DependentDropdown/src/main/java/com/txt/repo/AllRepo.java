package com.txt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.txt.entity.AllEntity;

public interface AllRepo extends JpaRepository<AllEntity, Integer>{

	@Query("select fy from AllEntity fy order by all_id")
	public List<AllEntity> getAll();
	
	@Query("From AllEntity where country_id1.country_id=:country_id AND state_id1.state_id=:state_id AND district_id1.district_id=:district_id")
	AllEntity findByAllId(@Param("country_id")Long country_id,@Param("state_id")Long state_id,@Param("district_id")Long district_id); 

	
}
