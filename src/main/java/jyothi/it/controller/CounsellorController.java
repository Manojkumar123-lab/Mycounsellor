package jyothi.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jyothi.it.entity.Counsellor;
import jyothi.it.entity.dto.Dashboard;
import jyothi.it.service.CounsellorService;
import jyothi.it.service.CounsellorServiceImp;
import jyothi.it.service.EnquiryService;
import jyothi.it.service.EnquiryServiceImp;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorServiceImp counsellorServiceImp;

	@Autowired
	private EnquiryServiceImp enquiryServiceImp;

	@GetMapping("/logout")
    	public String logout(HttpServletRequest req, Model mdl) {
		HttpSession session = req.getSession(false); // get session
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/register")
	public String register(Model mdl) {
		mdl.addAttribute("counsellor", new Counsellor());
		return "registerView";
	}

	@PostMapping("/saveregister")
	public String HandleRegister(Counsellor c, Model mdl) {

		boolean status = counsellorServiceImp.savecounsellordetails(c);
		if (status) {
			mdl.addAttribute("smsg", "Counsellor Saved..");
		} else {
			mdl.addAttribute("emsg", "Failed To Save");
		}
		return "registerView";
	}

	@GetMapping("/")
	public String login(Model mdl) {
		mdl.addAttribute("counseller", new Counsellor()); // form binding obj
		return "index";
	}

	@PostMapping("/login")
	public String HandleLogin(HttpServletRequest req, Counsellor counsellor, Model mdl) {

		Counsellor c = counsellorServiceImp.getCounsellor(counsellor.getEmail(), counsellor.getPwd());

		if (c == null) {
			mdl.addAttribute("emsg", "Invalid Credentails");
			mdl.addAttribute("counseller", new Counsellor()); // form binding obj
			return "index";
		} else {
			HttpSession session = req.getSession(true); // always new sesssion // set counsellor Id to session
			session.setAttribute("cid", c.getCounsellorId());

			Dashboard dashboardInfo = enquiryServiceImp.getDashboardInfo(c.getCounsellorId());
			mdl.addAttribute("dahsboard", dashboardInfo);
			return "dashboard";
		}
	}
	
	@GetMapping("/getdashboard")
	public String buildDashboard(HttpServletRequest req, Counsellor counsellor, Model mdl) {
		
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");

		Dashboard dashboardInfo = enquiryServiceImp.getDashboardInfo(cid);
		mdl.addAttribute("dahsboard", dashboardInfo);
		return "dashboard";
	}
	
	
	
}
