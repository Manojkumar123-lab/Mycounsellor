package jyothi.it.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import jyothi.it.entity.Counsellor;
import jyothi.it.entity.Enquiry;
import jyothi.it.entity.dto.Dashboard;
import jyothi.it.entity.repo.CounsellorReporsitory;
import jyothi.it.entity.repo.EnquiryRepository;

@Service
public class EnquiryServiceImp implements EnquiryService {

	@Autowired
	private EnquiryRepository enquiryRepo;

	@Autowired
	private CounsellorReporsitory counsellorRepo;

	@Override
	public Dashboard getDashboardInfo(Integer counsellorId) {

		Long totalEnquiries = enquiryRepo.getTotalEnquiries(counsellorId);
		Long opencnt = enquiryRepo.getOpenEnquiries(counsellorId, "new");
		Long Lostcnt = enquiryRepo.getLostEnquiries(counsellorId, "lost");
		Long Enrolledcnt = enquiryRepo.getEnrolledEnquiries(counsellorId, "enrolled");

		Dashboard dsh = new Dashboard();
		dsh.setTotalEnqs(totalEnquiries);
		dsh.setOpenEnqs(opencnt);
		dsh.setLostEnqs(Lostcnt);
		dsh.setEnrolledEnqs(Enrolledcnt);

		return dsh;
	}

	@Override
	public boolean addEquiry(Enquiry enuiry, Integer counsellorId) {

		Counsellor counsellorObject = counsellorRepo.findById(counsellorId).orElseThrow();
		//enuiry.setCounsellor(counsellorObject); // fk
		
		boolean existsByEnqId = enquiryRepo.existsByEnqId(enuiry.getEnqId());
		if(existsByEnqId) {
			Enquiry enquiryupdate = new Enquiry();
			enquiryupdate.setCounsellor(counsellorObject);
			enquiryupdate.setCourse(enuiry.getCourse());
			enquiryupdate.setMode(enuiry.getMode());
			enquiryupdate.setStatus(enuiry.getStatus());
			enquiryupdate.setStuname(enuiry.getStuname());
			enquiryupdate.setStuphno(enuiry.getStuphno());
			enquiryupdate.setEnqId(enuiry.getEnqId());
			Enquiry updateEnquiry = enquiryRepo.save(enquiryupdate);
		
			return updateEnquiry.getEnqId() != null;
		}else {
			enuiry.setCounsellor(counsellorObject); // fk
			Enquiry savedEnq = enquiryRepo.save(enuiry);
			return savedEnq.getEnqId() != null;
		}
		
	}

	@Override
	public List<Enquiry> getEnquiries(Enquiry enuiry, Integer counsellorId) {

	//	Counsellor counsellorObject = counsellorRepo.findById(counsellorId).orElseThrow();
		System.out.print("enuiry details"+enuiry);
		Counsellor counsellor = new Counsellor();
		counsellor.setCounsellorId(counsellorId);
		
		Enquiry filterenquiry = new Enquiry();
		enuiry.setCounsellor(counsellor);

		if(enuiry.getCourse() != null && !enuiry.getCourse().equals("")) {
			filterenquiry.setCourse(enuiry.getCourse());
			System.out.print("enuiry details"+enuiry.getCourse());
		}else if(enuiry.getStatus() != null && !enuiry.getStatus().equals("")) {
			filterenquiry.setStatus(enuiry.getStatus());
			System.out.print("enuiry details"+enuiry.getStatus());
		}else if(enuiry.getMode() != null && !enuiry.getMode().equals("")){
			filterenquiry.setMode(enuiry.getMode());
			System.out.print("enuiry details"+enuiry.getMode());
		}
		
		
		
		// dynamic query creation
		Example<Enquiry> of = Example.of(filterenquiry); // filter
		System.out.print("enuiry details"+of);
		return enquiryRepo.findAll(of);

	}

	@Override
	public Enquiry getEnquiry(Integer enqId) {
		return enquiryRepo.findById(enqId).orElseThrow();
	}

}
