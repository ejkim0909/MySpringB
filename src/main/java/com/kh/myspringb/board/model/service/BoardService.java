package com.kh.myspringb.board.model.service;

import java.util.List;

import com.kh.myspringb.board.model.domain.Board;

public interface BoardService {
	int listCount();
	
	int insertBoard(Board b);
	
	List<Board> selectList();   // 전체 읽기
	
	Board selectOne(String board_num);
	
//	int addReadCount(String board_num);
	
	Board updateBoard(Board b);

	void deleteBoard(String board_num);
}
