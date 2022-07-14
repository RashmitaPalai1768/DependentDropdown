package com.txt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.txt.entity.districtEntity;

public interface districtRepo extends JpaRepository<districtEntity, Integer>{

	@Query("From districtEntity dd where dd.s_id.state_id=:state_id")
	List<districtEntity> findAllBlockByDistrictId(@Param("state_id")int state_id);
}
