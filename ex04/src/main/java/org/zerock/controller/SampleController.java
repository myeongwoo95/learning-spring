package org.zerock.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/sample/")
@Controller
public class SampleController {
	
	@GetMapping("/all")
	public void doAll() {
		
		log.info("do all can access serverybody");
	}
	
	@GetMapping("/member")
	public void doMember() {
		
		log.info("logined member");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		
		log.info("admin only");
	}
}
