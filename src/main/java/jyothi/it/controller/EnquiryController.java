package jyothi.it.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jyothi.it.entity.Enquiry;
import jyothi.it.service.EnquiryServiceImp;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryServiceImp enqservice;
	
	
	// add enq - page display
	@GetMapping("/enquiry")
	public String addEnquiry(Model model) {
		model.addAttribute("enquiry", new Enquiry()); // sending empty object
		return "addEnq";
	}

	// save enq
	@PostMapping("/saveEnquiry")
	public String saveEnquiry(Enquiry enq, HttpServletRequest req, Model mdl) {

		HttpSession session = req.getSession(false); // false means existing session not created new session
		Integer cid = (Integer) session.getAttribute("cid");

		boolean status = enqservice.addEquiry(enq, cid);
		if (status) {
			mdl.addAttribute("smsg", "Enquiry Saved");
		} else {
			mdl.addAttribute("emsg", "Enquiry Not saved");
		}

		return "addEnq";
	}

	// get all enquiries
	@GetMapping("/getEnquirys")
	public String getEnquiry(HttpServletRequest req, Model mdl) {

		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");

		List<Enquiry> alllist = enqservice.getEnquiries(new Enquiry(), cid); // sample new Enquiry()
		mdl.addAttribute("allenquirys", alllist);

		mdl.addAttribute("enquiry", new Enquiry()); // binding the object

		return "viewEnquiries";
	}

	// filter enqs
	@PostMapping("/filter-enqs")
	public String fiterEnqs(Enquiry enq, HttpServletRequest req, Model mdl) {

		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("cid");

		List<Enquiry> filterlist = enqservice.getEnquiries(enq, cid);
		mdl.addAttribute("allenquirys", filterlist);
		mdl.addAttribute("enquiry", new Enquiry());
		return "viewEnquiries";
	}

	// edit & update enq  // no need session we already have
	@GetMapping("/updateEnquiry")
	public String ediEnquiry(@RequestParam("enqId") Integer enqId, Model mdl) {

		Enquiry enquiry = enqservice.getEnquiry(enqId);
		mdl.addAttribute("enquiry", enquiry);
		return "addEnq";

	}

}
