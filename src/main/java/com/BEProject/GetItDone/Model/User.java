package com.BEProject.GetItDone.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Entity
@Table(name="User")
public class User implements Serializable{
	enum Status{ ACTIVE, INACTIVE };
	public enum Role{ROLE_SERVICE_SEEKER, ROLE_SERVICE_PROVIDER};
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private long userId;
	@Column(name="name", length=100, nullable=false)
	@NotEmpty(message="{NotEmpty.User.name}")
	private String name;
	@Column(name="email", length=100, nullable=false,unique=true)
	@NotEmpty(message="{NotEmpty.User.email}")
	@Email
	private String email;
	@Column(name="address", length=200, nullable=false)
	@NotEmpty(message="{NotEmpty.User.address}")
	private String address;
	@Column(name="password", nullable=false)
	@NotNull
	@Size(min = 4, message = "{Size.User.password}")
	private String password;
	@Column(name="contactNumber", length=15, nullable=false,unique=true)
	@NotNull
    @Size(max = 10, min = 10, message = "{Size.User.contactNumber}")
    @Pattern(regexp = "[7-9][0-9]{9}", message = "{Pattern.User.contactNumber}")
	private String contactNumber;
	@Column(columnDefinition = "varchar(10)", name="activeStatus", nullable=false)
	@Enumerated(value = EnumType.STRING)
	private Status activeStatus;
	
	@Column(columnDefinition = "varchar(50)", name="role", nullable=false)
	@Enumerated(value = EnumType.STRING)
	private Role role;
	
	
	
	public User() {
		
	}
	public User(User user) {
		this.name = user.name;
		this.email = user.email;
		this.address = user.address;
		this.contactNumber = user.contactNumber;
		this.password = user.password;
		this.role = user.role;
		if(user.role.equals(Role.ROLE_SERVICE_PROVIDER)) {
			this.activeStatus = Status.ACTIVE;
		}
		else if(user.role.equals(Role.ROLE_SERVICE_SEEKER)) {
			this.activeStatus = Status.ACTIVE;
		}
	}
	

	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Status getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(Status activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
}
