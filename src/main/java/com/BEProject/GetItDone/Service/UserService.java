package com.BEProject.GetItDone.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BEProject.GetItDone.Model.User;
import com.BEProject.GetItDone.Repository.UserDao;

@Service
public class UserService {
	@Autowired
	UserDao userDao;
	public User getByEmail(String email) {
		return userDao.findByEmail(email);
	}
	public void saveUser(User user) {
		userDao.save(user);
	}
	public List<User> listOfUsers() {
		return userDao.findAll();
	}
	public User getUserById(long userId) {
		return userDao.getOne(userId);
	}
	public void updateUserProfile(long userId,User user) {
		
		userDao.updateProfile(userId,user.getName(),user.getEmail(),user.getAddress(),user.getContactNumber(),user.getPassword());
	}

}
