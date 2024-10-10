package com.ktdsuniversity.edu.hello_spring.member.service;

import com.ktdsuniversity.edu.hello_spring.member.vo.LoginMemberVO;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberRegistVO;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

public interface MemberService {
	
	public boolean insertNewMember(MemberRegistVO memberRegistVO);
	
	public boolean checkAvailableEmail(String email);
	
	public MemberVO readMember(LoginMemberVO loginMemberVO);
	
	/**
	 * 회원을 탈퇴시킨다
	 * @param email 탈퇴시킬 회원의 이메일
	 * @return 탈퇴 성공 여부
	 */
	public boolean deleteMe(String email);

}
