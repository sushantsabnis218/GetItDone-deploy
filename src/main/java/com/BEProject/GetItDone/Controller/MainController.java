package com.BEProject.GetItDone.Controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BEProject.GetItDone.Model.AvailableServices;
import com.BEProject.GetItDone.Model.BookingDetails;
import com.BEProject.GetItDone.Model.ServicesProvided;
import com.BEProject.GetItDone.Model.User;
import com.BEProject.GetItDone.Service.AvailableTaskServices;
import com.BEProject.GetItDone.Service.BookingService;
import com.BEProject.GetItDone.Service.ProviServices;
import com.BEProject.GetItDone.Service.UserService;

@Controller

public class MainController {

	@Autowired 
	private ProviServices proviServices;
	
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private AvailableTaskServices availableTaskServices;
	 
	 @Autowired
	 private BookingService bookingService;
	 
	 @GetMapping("/")
	 public String viewHomePage() {
		 return "index";
	 }
	 @GetMapping("/register")
	 public String showRegistrationForm(Model model) {
	     model.addAttribute("user", new User());
	     return "signupForm";
	 }
	 
	 @PostMapping("/process_registeration")
	 public String processRegister(@Valid User user,  Errors error, RedirectAttributes redirAttrs) {
		 if (null != error && error.getErrorCount() > 0) {
		     return "signupForm";
	        }
		 else{
	         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	   	     String encodedPassword = passwordEncoder.encode(user.getPassword());
	   	     user.setPassword(encodedPassword);
	   	     User tempUser = new User(user);
	   	     try{
	   	     	userService.saveUser(tempUser);
	   	     redirAttrs.addFlashAttribute("success", "Welcome to the GetItDone Community !");
	   	     }catch(Exception E) {
	   	    	redirAttrs.addFlashAttribute("registerError", "Error! User Already Exists");
	   	     }
	   	     return "redirect:/";
	        }
	     
	 }

	 

	 @GetMapping("/login")
	 public String viewLoginPage() {
	        return "login";
	 }
	 
	 @GetMapping("/serviceSeekerDashboard")
	 public String viewServiceSeekerDashboard(Model model, HttpSession session) {
		 
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User user=userService.getByEmail(username);
		session.setAttribute("user", user);
		List<ServicesProvided> allServices = proviServices.getAllServices();
		model.addAttribute("listAllServices",allServices);
		List<BookingDetails> myBookings = bookingService.getMyBookings(user.getUserId());
		model.addAttribute("myBookings",myBookings);
	  	return "serviceSeekerDashboard";
	 }

	 @GetMapping("/serviceProviderDashboard")
	 public String ViewserviceProviderrDashboard(Model model, HttpSession session) {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		User user=userService.getByEmail(username);
		session.setAttribute("user", user);
		List<AvailableServices> listOfAvailableServices = availableTaskServices.getAvailableServices();
		model.addAttribute("listOfAvailableServices",listOfAvailableServices);
		List<ServicesProvided> myServices = proviServices.getMyServices(user.getUserId());
		model.addAttribute("listMyServices",myServices);
		List<BookingDetails> myOrders = bookingService.getMyOrders(user.getUserId());
		model.addAttribute("myOrders",myOrders);
		return "serviceProviderDashboard";
	 }
	 
	 @GetMapping("/adminDashboard")
	 public String viewAdminDashBoard(Model model, HttpSession session) {
	     List<User> listUsers = userService.listOfUsers();
	     model.addAttribute("listUsers", listUsers);
	     String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		 session.setAttribute("userName", userName);
	     List<AvailableServices> listServices = availableTaskServices.getAvailableServices();
	     model.addAttribute("listAvailableServices",listServices);
	     model.addAttribute("availableServices",new AvailableServices());
	     List<ServicesProvided> listProvided = proviServices.getProvidedServices();
	     model.addAttribute("listProvided",listProvided);
		 return "adminDashboard";
	 }
	 @PostMapping("/addService")
	 public String addService(@Valid AvailableServices availableServices, Errors error,RedirectAttributes redirAttrs) {
		 if (null != error && error.getErrorCount() > 0) {
	            return "adminDashboard";
	        }
		 else{
			 try {
			 	availableTaskServices.saveService(availableServices);
			 	redirAttrs.addFlashAttribute("newServiceNoti", "Added New Service : "+availableServices.getTitle());
			 }catch(Exception e) {
				 redirAttrs.addFlashAttribute("AlreadyExists", "AlreadyExists : "+availableServices.getTitle());
			 }
			 return "redirect:adminDashboard";
		 }
	 }
	 @PostMapping("/deleteService")
	 public String deleteService(@RequestParam("serviceId") long serviceIdToBeDeleted, RedirectAttributes redirAttrs){
		 String tempServiceTitle = availableTaskServices.getServiceByServiceId(String.valueOf(serviceIdToBeDeleted)).getTitle();
		 try {
			 availableTaskServices.removeService(serviceIdToBeDeleted);
			 redirAttrs.addFlashAttribute("deleteServiceNoti", "Deleted Service : "+ tempServiceTitle);
		 }
		 catch(Exception E){
			 redirAttrs.addFlashAttribute("ErrorDeleteServiceNoti", "Error occured while deleting service : "+ tempServiceTitle);
		 }
		 
		 return "redirect:adminDashboard";
	 }
	 
	 @PostMapping("/applyForService")
	 public String applyForService(
			 					   @RequestParam("userId") long userId,
			 					   @RequestParam("appliedFor") String service,
			 					   @RequestParam("costPerHour") double serviceCostPerHour,
			 					   @RequestParam("areaOfService") String serviceAreaOfService,
			 					   HttpSession session,
			 					  RedirectAttributes redirAttrs
			 					   ) {
		
		 User user = userService.getUserById(userId);
		 AvailableServices serviceTemp = availableTaskServices.getServiceByServiceId(service);
		 ServicesProvided servicesProvided = new ServicesProvided(user,serviceTemp,serviceCostPerHour,serviceAreaOfService);
		 proviServices.saveService(servicesProvided);
		 redirAttrs.addFlashAttribute("serviceApplicationNoti", "Applied for service : "+ serviceTemp.getTitle());
		 return "redirect:serviceProviderDashboard";
		 
	 }
	 @GetMapping("/404")
	 public String error(){
		 return "accessDenied";
	 }
	 @PostMapping("/toggleStatus")
	 public String toggleStatus(
			 	@RequestParam("providedServiceId") long providedServiceId,
			 	@RequestParam("currentStatus") String currentStatus,
			 	@RequestParam("serviceId") long serviceId,
			 	RedirectAttributes redirAttrs
			 ) {
		 		proviServices.changeStatus(providedServiceId,currentStatus);
		 		double tempAvgCost = proviServices.getAvgCost(serviceId);
		 		availableTaskServices.updateAvgCost(serviceId, tempAvgCost);
		 		redirAttrs.addFlashAttribute("toggleStatusNoti", "Toggled Status for service - "+ availableTaskServices.getServiceByServiceId(String.valueOf(serviceId)).getTitle()+", by User - "+ proviServices.getProviderByServiceId(providedServiceId).getUserId().getName());
		 		return "redirect:adminDashboard";
		 
	 }
	 @PostMapping("/bookService")
	 public String setBooking(
			 	@RequestParam("serviceProviderId") long idOfserviceForBooking,
			 	Model model
			 	){
		 
		 ServicesProvided serviceForBooking = proviServices.getProvidedService(idOfserviceForBooking);
		 model.addAttribute("serviceForBooking",serviceForBooking);
		
		 return "bookService";

	 }
	 @PostMapping("/bookServiceDetails")
	 public String bookService(
			 @RequestParam("serviceId") String serviceForBooking,
			 @RequestParam("bookingAddress") String bookingAddress,
			 @RequestParam("bookingTime") String bookingTime,
			 HttpSession session,
			 RedirectAttributes redirAttrs
			 ) {
		 
		 String username=SecurityContextHolder.getContext().getAuthentication().getName();
		 User user=userService.getByEmail(username);
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		 LocalDateTime dateTime = LocalDateTime.parse(bookingTime, formatter);
		 ServicesProvided tempServiceForBooking = proviServices.getProvidedService(Long.parseLong(serviceForBooking));
		 BookingDetails tempBooking = new BookingDetails(tempServiceForBooking,bookingAddress, dateTime,user);
		 bookingService.insertBooking(tempBooking);
		 redirAttrs.addFlashAttribute("bookServiceNoti", "Service : "+proviServices.getProvidedService(Long.parseLong(serviceForBooking)).getServiceId().getTitle()+" booked successfully");
		 return "redirect:serviceSeekerDashboard";
		 
	 }
	 @PostMapping("/changeOrderStatus")
	 public String changeOrderStatus(
			 	@RequestParam("id") long orderId,
			 	@RequestParam("otp") String otp,
			 	@RequestParam("num") String num,
			 	RedirectAttributes redirAttrs
			 ) {
		 		bookingService.changeStatus(orderId,Long.parseLong(num),Long.parseLong(otp));
		 		redirAttrs.addFlashAttribute("changeOrderStatusNoti", "Changed Status for order : "+bookingService.getBookingById(orderId).Noti());
		 		return "redirect:serviceProviderDashboard";
		 
	 }
	 @PostMapping("/updateRating")
	 public String updateRating(
			 	@RequestParam("rating") String rating,
			 	@RequestParam("providedServiceId") long providedServiceId,
			 	@RequestParam("id") long bookingId,
			 	RedirectAttributes redirAttrs
			 ) {
		 	
		 	bookingService.changeRating(Long.parseLong(rating),bookingId);
		 	double tempAvgRating = bookingService.getAverageRating(providedServiceId);
		 	proviServices.updateRating(providedServiceId,tempAvgRating);
		 	redirAttrs.addFlashAttribute("ratingUpdateNoti", "Updated rating for booking no. : "+ bookingId);
		 	return "redirect:serviceSeekerDashboard";
	 }
	 @PostMapping("/updateUserProfile")
	 public String updateUserProfile(Model model) {
		 String username=SecurityContextHolder.getContext().getAuthentication().getName();
		 User user=userService.getByEmail(username);
	     model.addAttribute("user", user);
	     
	     return "updateProfile";
	 }
	 @PostMapping("/saveNewProfile")
	 public String saveNewProfile(@Valid User user,  Errors error, RedirectAttributes redirAttrs) {
		 if (null != error && error.getErrorCount() > 0) {
		     return "updateProfile";
	        }
		 else{
			 
	         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	   	     String encodedPassword = passwordEncoder.encode(user.getPassword());
	   	     user.setPassword(encodedPassword);
	   	     User tempUser = new User(user);
	   	     
	   	     try{
	   	     	userService.updateUserProfile(user.getUserId(), tempUser);
	   	     	redirAttrs.addFlashAttribute("success", "Profile updated successfully");
	   	     }catch(Exception E) {
	   	    	redirAttrs.addFlashAttribute("updateError", "Error! Profile cannot be updated");
	   	     }
	   	     System.out.println(tempUser.getRole());
	   	     if(tempUser.getRole().toString().equals("ROLE_SERVICE_SEEKER")) {
	   	    	 
	   	    	 return "redirect:/serviceSeekerDashboard";
	   	     }
	   	     else {
	   	    	 return "redirect:/serviceProviderDashboard";
	   	     }
	        }
	     
	 }
	 
}
