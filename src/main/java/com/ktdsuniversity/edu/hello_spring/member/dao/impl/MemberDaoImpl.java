package com.ktdsuniversity.edu.hello_spring.member.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.hello_spring.member.dao.MemberDao;
import com.ktdsuniversity.edu.hello_spring.member.vo.LoginMemberVO;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberRegistVO;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

@Repository
public class MemberDaoImpl extends SqlSessionDaoSupport implements MemberDao{

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public int selectEmailCount(String email) {
		return getSqlSession().selectOne(NAMESPACE + ".selectEmailCount",email);
	}
	
	@Override
	public int insertNewMember(MemberRegistVO memberRegistVO) {
		return getSqlSession().insert(NAMESPACE + ".insertNewMember", memberRegistVO);
	}
	
	@Override
	public String selectSalt(String email) {
		return getSqlSession().selectOne(NAMESPACE + ".selectSalt", email);
	}
	
	@Override
	public MemberVO selectOneMember(LoginMemberVO loginMemberVO) {
		return getSqlSession().selectOne(NAMESPACE + ".selectOneMember", loginMemberVO);
	}
	
	@Override
	public int updateLoginFailState(LoginMemberVO loginMemberVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateLoginFailState", loginMemberVO);
	}
	
	@Override
	public int selectLoginImpossibleCount(String email) {
		return this.getSqlSession().selectOne(NAMESPACE + ".selectLoginImpossibleCount", email);
	}
	
	@Override
	public int updateLoginSuccessState(LoginMemberVO loginMemberVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateLoginSuccessState", loginMemberVO);
	}
	
	@Override
	public int deleteMe(String email) {
		return this.getSqlSession().delete(NAMESPACE + ".deleteMe", email);
	}
}
