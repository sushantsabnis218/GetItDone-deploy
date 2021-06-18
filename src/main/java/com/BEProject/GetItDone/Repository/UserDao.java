package com.BEProject.GetItDone.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.BEProject.GetItDone.Model.User;

public interface UserDao extends JpaRepository<User, Long> {
	@Query("SELECT user FROM user_table user WHERE email = ?1")
    public User findByEmail(String email);
	@Modifying
	@Transactional
	@Query(value="UPDATE user_table set name = ?2 ,email = ?3, address = ?4, contact_number= ?5 , password = ?6 where user_id = ?1", nativeQuery=true)
	public void updateProfile(long user_id, String name,String email,String address,String contact_number,String password);
}

