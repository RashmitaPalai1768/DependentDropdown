package com.txt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.txt.entity.stateEntity;

public interface stateRepo extends JpaRepository<stateEntity, Integer>{

	@Query("From stateEntity ss where ss.c_id.country_id=:country_id")
	List<stateEntity> findAllstateByDistrictId(@Param("country_id")int country_id);
}
