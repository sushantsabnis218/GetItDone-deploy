package com.BEProject.GetItDone.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BEProject.GetItDone.Model.UserInfo;
import com.BEProject.GetItDone.Repository.UserDao;

@Service
public class UserService {
	@Autowired
	UserDao userDao;
	public UserInfo getByEmail(String email) {
		return userDao.findByEmail(email);
	}
	public void saveUser(UserInfo user) {
		userDao.save(user);
	}
	public List<UserInfo> listOfUsers() {
		return userDao.findAll();
	}
	public UserInfo getUserById(long userId) {
		return userDao.getOne(userId);
	}
	public void updateUserProfile(long userId,UserInfo user) {
		
		userDao.updateProfile(userId,user.getName(),user.getEmail(),user.getAddress(),user.getContactNumber(),user.getPassword());
	}

}
