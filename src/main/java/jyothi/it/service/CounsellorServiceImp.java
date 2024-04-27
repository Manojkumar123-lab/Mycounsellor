package jyothi.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jyothi.it.entity.Counsellor;
import jyothi.it.entity.repo.CounsellorReporsitory;

@Service
public class CounsellorServiceImp implements CounsellorService {

	@Autowired
	private CounsellorReporsitory counsellorReporsitory;

	@Override
	public boolean savecounsellordetails(Counsellor counsellor) {

		Counsellor findByEmail = counsellorReporsitory.findByEmail(counsellor.getEmail());

		if (findByEmail != null) {
			return false;
		} else {
			Counsellor savecounsellor = counsellorReporsitory.save(counsellor);
			return savecounsellor.getCounsellorId() != null;
		}

	}

	
	@Override
	public Counsellor getCounsellor(String email, String pwd) {

		return counsellorReporsitory.findByEmailAndPwd(email, pwd);
	}

}
