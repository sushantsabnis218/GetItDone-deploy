package com.BEProject.GetItDone.Model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="AvailableServices")
public class AvailableServices {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceId")
	private long serviceId;
	@Column(name="title", length=50, nullable=false,unique=true)
	@NotEmpty(message="{NotEmpty.AvailableService.title}")
	private String title;
	@Column(columnDefinition = "Decimal(12,2)", name="avgCost")
	private double avgCost;

	
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getAvgCost() {
		return avgCost;
	}
	public void setAvgCost(double avgCost) {
		this.avgCost = avgCost;
	}
	
}
