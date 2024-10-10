package com.ktdsuniversity.edu.hello_spring.bbs.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteBoardVO;

@Repository
public class BoardDaoImpl extends SqlSessionDaoSupport implements BoardDao {

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		System.out.println("Autowiring sqlSessionTemplate: " + sqlSessionTemplate);
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public int selectBoardAllCount() {
		return this.getSqlSession().selectOne("com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao.selectBoardAllCount");
	}
	
	@Override
	public List<BoardVO> selectAllBoard() {
		return this.getSqlSession().selectList("com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao.selectAllBoard");
	}
	
	@Override
	public int inserNewBoard(WriteBoardVO writeBoardVO) {
		return this.getSqlSession().insert("com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao.inserNewBoard", writeBoardVO);
	}
	
	@Override
	public int updateViewCount(int id) {
		return this.getSqlSession().update("com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao.updateViewCount", id);
	}
	
	@Override
	public BoardVO selectOneBoard(int id) {
		return this.getSqlSession().selectOne("com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao.selectOneBoard", id);
	}
	
	@Override
	public int updateOneBoard(ModifyBoardVO modifyBoardVO) {
		return this.getSqlSession().update("com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao.updateOneBoard", modifyBoardVO);
	}
	
	@Override
	public int deleteOneBoard(int id) {
		return this.getSqlSession().delete("com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao.deleteOneBoard", id);
	}
}
