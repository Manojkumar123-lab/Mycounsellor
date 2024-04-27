package jyothi.it.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jyothi.it.entity.Counsellor;
import java.util.List;

@Repository
public interface CounsellorReporsitory extends JpaRepository<Counsellor, Integer>{
	
public Counsellor findByEmail(String email);
public Counsellor findByEmailAndPwd(String email, String pwd);
public Counsellor  findByCounsellorId(Integer counsellorId);

}
