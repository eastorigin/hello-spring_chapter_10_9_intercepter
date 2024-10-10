package com.ktdsuniversity.edu.hello_spring.member.dao;

import com.ktdsuniversity.edu.hello_spring.member.vo.LoginMemberVO;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberRegistVO;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

public interface MemberDao {

	public String NAMESPACE = "com.ktdsuniversity.edu.hello_spring.member.dao.MemberDao";
	
	public int selectEmailCount(String email);
	
	public int insertNewMember(MemberRegistVO memberRegistVO);
	
	public String selectSalt(String email);
	
	public MemberVO selectOneMember(LoginMemberVO loginMemberVO);
	
	public int updateLoginFailState(LoginMemberVO loginMemberVO);
	
	public int selectLoginImpossibleCount(String email);
	
	public int updateLoginSuccessState(LoginMemberVO loginMemberVO);
	
	/**
	 * 회원 DELETE 쿼리를 실행한다
	 * @param email 삭제할 회원의 이메일
	 * @return DB에 DELETE 한 회원의 개수
	 */
	public int deleteMe(String email);
}
