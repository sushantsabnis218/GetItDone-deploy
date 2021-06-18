package com.BEProject.GetItDone.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.BEProject.GetItDone.Model.AvailableServices;

public interface AvailableServicesDao extends JpaRepository<AvailableServices, Long> {
	@Modifying
	@Transactional
	@Query(value="UPDATE available_services set avg_cost=?2 where service_id = ?1", nativeQuery=true)
	public void updateAvgCost(long service_id,double avg_cost);
	
	
	
}
