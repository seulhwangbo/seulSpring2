package com.oracle.oBootBoard.command;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dto.BDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BExecuteCommand {
	
	private final BDao jdbcDao;
	
	@Autowired
	public  BExecuteCommand(BDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	public void bContentCmd(Model model) {
		System.out.println("BExcuteCommand bContentCmd start...");
		// model 이를 map으로 전환이 가능하다
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String bId = request.getParameter("bId");
		BDto board = jdbcDao.contentView(bId);
		
		model.addAttribute("mvc_board",board);
	}
	public void blistCmd(Model model) {
		ArrayList<BDto> boardDtoList = jdbcDao.boardList();
		System.out.println("BlistCommand boardDtoList.size()==> " + boardDtoList.size());
		model.addAttribute("boardList",boardDtoList);
	}
	
	
}
