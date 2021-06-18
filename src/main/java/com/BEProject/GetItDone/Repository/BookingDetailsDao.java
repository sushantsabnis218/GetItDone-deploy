package com.BEProject.GetItDone.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.BEProject.GetItDone.Model.BookingDetails;

public interface BookingDetailsDao extends JpaRepository<BookingDetails, Long> {
	@Query(value="select * from booking_details where provided_service_id IN (select t1.provided_service_id from services_provided as t1 where user_id = ?1)", nativeQuery=true)
	public List<BookingDetails> getMyOrders(long user_id);

	@Query(value="select * from booking_details where user_id = ?1", nativeQuery=true)
	public List<BookingDetails> getMyBookings(long user_id);
	

	@Modifying
	@Transactional
	@Query(value="UPDATE booking_details set status='ACCEPTED', otp = ?2 where booking_id = ?1 and otp = '0'", nativeQuery=true)
	public void updateStatusToAccepted(long booking_id, long otp);

	@Modifying
	@Transactional
	@Query(value="UPDATE booking_details set status='REJECTED' where booking_id = ?1", nativeQuery=true)
	public void updateStatusToRejected(long booking_id);

	@Modifying
	@Transactional
	@Query(value="UPDATE booking_details set status='COMPLETED' where booking_id = ?1", nativeQuery=true)
	public void updateStatusToCompleted(long booking_id);
	
	@Query(value="select otp from booking_details where booking_id = ?1", nativeQuery=true)
	public long getBookingOtp(long booking_id);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE booking_details set rating=?1 where booking_id = ?2", nativeQuery=true)
	public void updateRating(long rating,long booking_id);
	
	
	@Query(value="select avg(rating) from booking_details where rating != 0.0 and provided_service_id = ?1 group by provided_service_id ", nativeQuery=true)
	public double getAverageRating(long providedServiceId);
	
	@Query(value="select * from booking_details where booking_id = ?1", nativeQuery=true)
	public BookingDetails getBookingById(long booking_id);
	
}

