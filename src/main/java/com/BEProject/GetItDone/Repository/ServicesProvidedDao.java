package com.BEProject.GetItDone.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.BEProject.GetItDone.Model.ServicesProvided;

public interface ServicesProvidedDao extends JpaRepository<ServicesProvided, Long> {
	@Modifying
	@Transactional
	@Query(value="UPDATE services_provided set active_status='ACTIVE' where provided_service_id = ?1", nativeQuery=true)
	public void updateStatusToActive(long provided_service_id);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE services_provided set active_status='INACTIVE' where provided_service_id = ?1", nativeQuery=true)
	public void updateStatusToInactive(long provided_service_id);
	
	@Query(value="select * from services_provided where user_id = ?1 and active_status='ACTIVE'", nativeQuery=true)
	public List<ServicesProvided> getMyServices(long user_id);
	
	@Query(value="select * from services_provided where active_status='ACTIVE'", nativeQuery=true)
	public List<ServicesProvided> getActiveServices();
	
	@Modifying
	@Transactional
	@Query(value="UPDATE services_provided set avg_rating=?2 where provided_service_id  = ?1", nativeQuery=true)
	public void updateRating(long provided_service_id, double avg_rating);
	
	@Query(value="select avg(cost_per_hour) from services_provided where service_id = ?1 group by service_id ", nativeQuery=true)
	public double getAverageCost(long serviceId);
	
	@Query(value="select * from services_provided where provided_service_id = ?1 ", nativeQuery=true)
	public ServicesProvided getProviderByServiceId(long provided_service_id);
	
	@Query(value="select * from services_provided where user_id = ?2 and service_id = ?1", nativeQuery=true)
	public ServicesProvided getServiceByServiceAndUserId(long service_id, long user_id);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE services_provided set area_of_service=?2, cost_per_hour=?3 where provided_service_id  = ?1", nativeQuery=true)
	public void updateProvidedService(long provided_service_id, String area_of_service, double cost_per_hour);
}
