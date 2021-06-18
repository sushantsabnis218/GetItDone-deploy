package com.BEProject.GetItDone.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.BEProject.GetItDone.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter{

	@Autowired
	AuthenticationSuccessHandler successHandler;
	@Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	auth
		.inMemoryAuthentication()
		.withUser("admin@admin.com")
		.password(passwordEncoder().encode("admin"))
		.roles("ADMIN");
    	
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.sessionManagement()
         .invalidSessionUrl("/login")
         .maximumSessions(1)
         .expiredUrl("/login?invalid-session=true")
         .maxSessionsPreventsLogin(false);
        http.authorizeRequests()
            .antMatchers("/users").hasRole("ADMIN")
            .antMatchers("/serviceProviderDashboard").hasRole("SERVICE_PROVIDER")
            .antMatchers("/serviceSeekerDashboard").hasRole("SERVICE_SEEKER")
            .antMatchers("/adminDashboard").hasRole("ADMIN")
            .antMatchers("/").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            	.loginPage("/login")
                .usernameParameter("email")
                .successHandler(successHandler)
                .permitAll()
            .and().logout().logoutSuccessUrl("/").invalidateHttpSession(true).permitAll().and()
            .csrf().disable().exceptionHandling()
            .accessDeniedPage("/404");
        	
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    

}
