package jyothi.it.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jyothi.it.entity.Enquiry;
import jyothi.it.entity.dto.Dashboard;

@Service
public interface EnquiryService {
	
	//getdabsh
	public Dashboard getDashboardInfo(Integer counsellorId);

	//get enquiries
	public List<Enquiry> getEnquiries(Enquiry enuiry, Integer counsellorId);
	
	//save enquiry
	public boolean addEquiry(Enquiry enuiry,Integer counsellorId);
	
	//edit
	public Enquiry getEnquiry(Integer enqId);
	
	

}
