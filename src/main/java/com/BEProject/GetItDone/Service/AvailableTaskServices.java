package com.BEProject.GetItDone.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BEProject.GetItDone.Model.AvailableServices;
import com.BEProject.GetItDone.Repository.AvailableServicesDao;

@Service
public class AvailableTaskServices {
	@Autowired
	AvailableServicesDao availableServicesDao ;
	
	public List<AvailableServices> getAvailableServices (){
		return availableServicesDao.findAll();
	}
	
	public void saveService(AvailableServices availableService) {
		availableServicesDao.save(availableService);
	}
	public void removeService(long serviceIdToBeDeleted) {
		availableServicesDao.deleteById(serviceIdToBeDeleted);
	}
	 public void updateAvgCost(long serviceId, double avg_cost) {
		 availableServicesDao.updateAvgCost(serviceId, avg_cost);
	 }
	 public AvailableServices getServiceByServiceId(String serviceId) {
			long serviceIdtemp = Long.parseUnsignedLong(String.valueOf(serviceId));
			return availableServicesDao.getOne(serviceIdtemp);
		}
	 
}
