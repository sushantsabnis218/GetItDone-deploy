package com.BEProject.GetItDone.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="BookingDetails")
public class BookingDetails {
	public enum BookingStatus{ BOOKED, ACCEPTED, REJECTED, COMPLETED};
	
	public BookingDetails(){
		
	}
	public BookingDetails(ServicesProvided service,String address,LocalDateTime bookingTime,User user){
		this.userId = user;
		this.providedServiceId  = service;
		this.address = address;
		this.bookingTime = bookingTime;
		this.status = BookingStatus.BOOKED;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookingId")
	private long bookingId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "userId", nullable = false)
	private User userId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "providedServiceId", nullable = false)
	private ServicesProvided providedServiceId;
	
	@Column(name = "timeStamp", nullable=false)
	@CreationTimestamp
    private LocalDateTime timeStamp;
	
	@Column(columnDefinition = "varchar(10)", name = "status")
	@Enumerated(value = EnumType.STRING)
	private BookingStatus status;
	
	@Column(columnDefinition = "Decimal(2,1)", name = "rating")
	private double rating;
	
	@Column(name="address", length=200, nullable=false)
	private String address;
	
	@Column(name = "timeSlot", nullable=false)
    private LocalDateTime bookingTime;
	
	@Column(name="otp", length=4)
	private long otp;
	
	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public ServicesProvided getProvidedServiceId() {
		return providedServiceId;
	}

	public void setProvidedServiceId(ServicesProvided providedServiceId) {
		this.providedServiceId = providedServiceId;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	public long getOtp() {
		return otp;
	}
	public void setOtp(long otp) {
		this.otp = otp;
	}
	public String Noti() {
		return  "User : " + userId.getName() + ", Service : " + providedServiceId.getServiceId().getTitle() + ", Time : "
				+ timeStamp + ", Address : " + address ;
	}
	
	
		
}
