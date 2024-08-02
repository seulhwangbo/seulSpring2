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
	
	//Dao 방식
	public void bModifyCmd(Model model) {
		// Chapter 1
		// 1. model Map선언
		// 2. parameter ->  bId, bName , bTitle , bContent
		// 3. jdbcDao.modify(bId, bName, bTitle, bContent);
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int bId = 	Integer.parseInt(request.getParameter("bId"));
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
//		jdbcDao.modify(bId, bName, bTitle, bContent);
		
		// Chapter 2
		// .............. 똑같은 걸 DTO로 바꾸기
		BDto bDto = new BDto(bId, bName, bTitle, bContent);
		jdbcDao.modify3(bDto);
		
	}

	public void bDeleteCmd(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int bid = Integer.parseInt(request.getParameter("bId"));
		jdbcDao.delete(bid);
	}

	public void bWriteCmd(Model model) {
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		jdbcDao.write(bName, bTitle, bContent);
		/*
		 * BDto bDto = new BDto(bName, bTitle, bContent); jdbcDao.write(bDto);
		 */
	}

	public void bReplyViewCmd(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		int bId = Integer.parseInt(request.getParameter("bId"));
		
		BDto dto = jdbcDao.reply_view(bId);
		model.addAttribute("reply_view",dto);
//		  1)  model이용 , map 선언
//		  2) request 이용 ->  bid  추출
//		  3) dao  instance 선언
//		  4) reply_view method 이용하여 (bid)
//		    - BDto dto = dao.reply_view(bId);
	}
	
	public void bReplyCmd(Model model) {
		
//		  1)  model이용 , map 선언
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		int bId    		= Integer.parseInt(request.getParameter("bId"));
		int bGroup 		= Integer.parseInt(request.getParameter("bGroup"));
		int bStep  		= Integer.parseInt(request.getParameter("bStep"));
		int bIndent 	= Integer.parseInt(request.getParameter("bIndent"));
		String bName 	= request.getParameter("bName");
		String bTitle 	= request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDto dto = jdbcDao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
		model.addAttribute("reply",dto);
		//		2) request 이용 -> bid,         bName ,  bTitle,
//		                    bContent ,  bGroup , bStep ,
//		                    bIndent 추출
//		  3) dao  instance 선언
//		  4) reply method 이용하여 댓글저장 
//		    - dao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
	}
	
}

