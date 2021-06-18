package com.BEProject.GetItDone.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.BEProject.GetItDone.Model.UserInfo;
import com.BEProject.GetItDone.Model.UserInfo.Role;

public class CustomUserDetails  implements UserDetails {
	private UserInfo user;
	
	public CustomUserDetails(UserInfo user) {
		this.user = user;
	}
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		Role role = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
         
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getEmail();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
     

	 
}
