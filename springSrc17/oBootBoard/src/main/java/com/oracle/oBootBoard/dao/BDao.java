package com.oracle.oBootBoard.dao;

import java.util.ArrayList;

import com.oracle.oBootBoard.dto.BDto;

public interface BDao {
	public ArrayList<BDto> boardList();
	public BDto contentView(String strId);
	public void modify(int bId, String bName, String bTitle, String bContent);
	public void modify3(BDto bDto);
	public void delete(int bid);
	//public void write(BDto bDto);
	public void write(String bName, String bTitle, String bContent);
	public BDto reply_view(int bId);
	public BDto reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent);
}
