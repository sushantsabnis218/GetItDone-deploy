package com.BEProject.GetItDone.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.BEProject.GetItDone.Model.UserInfo;
import com.BEProject.GetItDone.Repository.UserDao;
import com.BEProject.GetItDone.Security.CustomUserDetails;

public class CustomUserDetailsService  implements UserDetailsService{
	@Autowired
	private UserDao userDao;
	@Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userDao.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}


