package com.BEProject.GetItDone.Service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BEProject.GetItDone.Model.BookingDetails;
import com.BEProject.GetItDone.Repository.BookingDetailsDao;

@Service
public class BookingService {
	@Autowired
	BookingDetailsDao bookingDetailsDao;
	private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
	
	public void insertBooking(BookingDetails bookingDetails) {
		bookingDetailsDao.save(bookingDetails);
	}
	public List<BookingDetails> getMyOrders(long userId){
		
		return bookingDetailsDao.getMyOrders(userId);
	}
	public List<BookingDetails> getMyBookings(long userId){
		
		return bookingDetailsDao.getMyBookings(userId);
	}
	
	
	public void changeStatus(long id, long num, long otp) {
		//System.out.println(num);
 		if(num == 1) {
 			bookingDetailsDao.updateStatusToAccepted(id,getRandomNumberInRange(1000, 10000));
 		}
 		else if(num == 2) {
 			bookingDetailsDao.updateStatusToRejected(id);
 		}
 		else if (num == 3){
 			if(bookingDetailsDao.getBookingOtp(id) == otp) {
 				bookingDetailsDao.updateStatusToCompleted(id);
 			}
 				
 		}
	}
	public void changeRating(long rating , long bookingId) {
		bookingDetailsDao.updateRating(rating,bookingId);
	}
	public double getAverageRating(long providedServiceId) {
		return bookingDetailsDao.getAverageRating(providedServiceId);
	}
	public BookingDetails getBookingById(long bookingId) {
		return bookingDetailsDao.getBookingById(bookingId);
	}
}
