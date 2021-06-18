package com.BEProject.GetItDone.Model;

import java.util.HashSet;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.BEProject.GetItDone.Model.User.Status;

@Entity
@Table(name="ServicesProvided")
public class ServicesProvided {
	public ServicesProvided(){
		
	}
	public ServicesProvided(User user,AvailableServices service,double costPerHour,String areaOfService){
		this.userId = user;
		this.serviceId = service;
		this.costPerHour = costPerHour;
		this.areaOfService = new HashSet<String>(); 		
		this.areaOfService.add(areaOfService);
		this.avgRating = 0.0;
		this.activeStatus=Status.INACTIVE;
	}
	enum Status{ ACTIVE, INACTIVE };
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "providedServiceId")
	private long providedServiceId;
	
	@ManyToOne( fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "userId", nullable = false)
	private User userId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "serviceId", nullable = false)
	private AvailableServices serviceId;
	
	@Column(columnDefinition = "Decimal(2,1)", name="avgRating")
	private double avgRating;
	
	@Column(columnDefinition = "Decimal(12,2) default '0.0'", name="costPerHour", nullable=false)
	private double costPerHour;
	
	@Column(name="areaOfService", nullable=false)
	private HashSet<String> areaOfService;

	@Column(columnDefinition = "varchar(10)", name="activeStatus", nullable=false)
	@Enumerated(value = EnumType.STRING)
	private Status activeStatus;
	
	public Status getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(Status activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	public long getProvidedServiceId() {
		return providedServiceId;
	}
	public void setProvidedServiceId(long providedServiceId) {
		this.providedServiceId = providedServiceId;
	}
	
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	public AvailableServices getServiceId() {
		return serviceId;
	}
	public void setServiceId(AvailableServices serviceId) {
		this.serviceId = serviceId;
	}
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	public double getCostPerHour() {
		return costPerHour;
	}
	public void setCostPerHour(double costPerHour) {
		this.costPerHour = costPerHour;
	}
	public HashSet<String> getAreaOfService() {
		return areaOfService;
	}
	public void setAreaOfService(HashSet<String> areaOfService) {
		this.areaOfService = areaOfService;
	}
	
	
	
	
}
