package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.oracle.oBootBoard.command.BExecuteCommand;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BController {
	private static final Logger logger = LoggerFactory.getLogger(BController.class); 
	private final BExecuteCommand bExecuteService;
	
	@Autowired
	public BController(BExecuteCommand bExecuteService) {
		this.bExecuteService = bExecuteService;
	}
	
	@RequestMapping("list")
	public String list(Model model){
		logger.info("list start...");
		bExecuteService.blistCmd(model);
		model.addAttribute("count","50");
		return "list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model){
		System.out.println("content_view Start....");
		
		model.addAttribute("request",request);
		bExecuteService.bContentCmd(model);
		return "content_view";
	}
	
	
}
