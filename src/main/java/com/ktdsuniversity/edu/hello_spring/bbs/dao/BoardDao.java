package com.ktdsuniversity.edu.hello_spring.bbs.dao;

import java.util.List;

import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteBoardVO;

public interface BoardDao {

	public int selectBoardAllCount();
	
	public List<BoardVO> selectAllBoard();
	
	public int inserNewBoard(WriteBoardVO writeBoardVO);
	
	public int updateViewCount(int id);
	
	public BoardVO selectOneBoard(int id);
	
	public int updateOneBoard(ModifyBoardVO modifyBoardVO);
	
	public int deleteOneBoard(int id);
}
