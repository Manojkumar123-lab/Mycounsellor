package jyothi.it.service;

import org.springframework.stereotype.Service;

import jyothi.it.entity.Counsellor;

@Service
public interface CounsellorService {
	
	public boolean savecounsellordetails(Counsellor counsellor);
	public Counsellor getCounsellor(String email, String pwd);

}
