package com.BEProject.GetItDone.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BEProject.GetItDone.Model.ServicesProvided;
import com.BEProject.GetItDone.Repository.ServicesProvidedDao;

@Service
public class ProviServices {

	@Autowired
	ServicesProvidedDao servicesProvidedDao;

	public void saveService(ServicesProvided service) {
		servicesProvidedDao.save(service);
	}
	public List<ServicesProvided> getProvidedServices(){
		return servicesProvidedDao.findAll();
	}
	public void changeStatus(long id,String currentStatus) {
		if(currentStatus.equals("INACTIVE")) {
			servicesProvidedDao.updateStatusToActive(id);
		}else if(currentStatus.equals("ACTIVE")) {
			servicesProvidedDao.updateStatusToInactive(id);
		}
	}
	public List<ServicesProvided> getMyServices(long userId){
		return servicesProvidedDao.getMyServices(userId);
	}
	public List<ServicesProvided> getAllServices(){
		return servicesProvidedDao.getActiveServices();
	}
	public ServicesProvided getProvidedService(long id) {
		return servicesProvidedDao.getOne(id);
	}
	public void updateRating(long providedServiceId, double avgRating) {
		servicesProvidedDao.updateRating(providedServiceId, avgRating);
	}
	public double getAvgCost(long serviceId) {
		return servicesProvidedDao.getAverageCost(serviceId);
	}
	public ServicesProvided getProviderByServiceId(long providedServiceId) {
		return servicesProvidedDao.getProviderByServiceId(providedServiceId);
	}
	public ServicesProvided getServiceByServiceId(String serviceId) {
		long serviceIdtemp = Long.parseUnsignedLong(String.valueOf(serviceId));
		return servicesProvidedDao.getOne(serviceIdtemp);
	}
	public void removeService(long serviceIdToBeDeleted) {
		servicesProvidedDao.deleteById(serviceIdToBeDeleted);
	}
	 public ServicesProvided getServiceByServiceAndUserId(String serviceId, long userId) {
			long serviceIdtemp = Long.parseUnsignedLong(String.valueOf(serviceId));
			return servicesProvidedDao.getServiceByServiceAndUserId(serviceIdtemp, userId);
		}
	 public void updateProvidedService(long provided_service_id, String area_of_service, double cost_per_hour) {
		 servicesProvidedDao.updateProvidedService(provided_service_id, area_of_service, cost_per_hour);
	 }
}
