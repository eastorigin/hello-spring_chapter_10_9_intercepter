package com.ktdsuniversity.edu.hello_spring.bbs.vo;

import java.util.List;

public class BoardListVO {

	/**
	 * 조회된 게시글의 수
	 */
	private int boardCnt;
	
	/**
	 * 조회된 게시글의 목록
	 */
	private List<BoardVO> boardList;

	public int getBoardCnt() {
		return boardCnt;
	}

	public void setBoardCnt(int boardCnt) {
		this.boardCnt = boardCnt;
	}

	public List<BoardVO> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<BoardVO> boardList) {
		this.boardList = boardList;
	}
}
