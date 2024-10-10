package com.ktdsuniversity.edu.hello_spring.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.hello_spring.access.dao.AccessLogDao;
import com.ktdsuniversity.edu.hello_spring.access.vo.AccessLogVO;
import com.ktdsuniversity.edu.hello_spring.common.beans.Sha;
import com.ktdsuniversity.edu.hello_spring.common.utils.RequestUtil;
import com.ktdsuniversity.edu.hello_spring.member.dao.MemberDao;
import com.ktdsuniversity.edu.hello_spring.member.service.MemberService;
import com.ktdsuniversity.edu.hello_spring.member.vo.LoginMemberVO;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberRegistVO;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private AccessLogDao accessLogDao;
	
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private Sha sha;
	
	@Override
	public boolean insertNewMember(MemberRegistVO memberRegistVO) {
		int emailCount = memberDao.selectEmailCount(memberRegistVO.getEmail());
		if(emailCount > 0) {
			throw new IllegalArgumentException("Email이 이미 사용 중입니다");
		}
		
		// 1. Salt 발급
		String salt = sha.generateSalt();
		
		// 2. 사용자의 비밀번호 암호화
		String password = memberRegistVO.getPassword();
		password = sha.getEncrypt(password, salt);
		
		memberRegistVO.setPassword(password);
		memberRegistVO.setSalt(salt);
		
		int insertCount = memberDao.insertNewMember(memberRegistVO);
		return insertCount > 0;
	}
	
	@Override
	public boolean checkAvailableEmail(String email) {
		return this.memberDao.selectEmailCount(email) == 0;
	}
	
	@Override
	public MemberVO readMember(LoginMemberVO loginMemberVO) {
		
		boolean isIpBlock = this.accessLogDao.selectLoginFailCount(RequestUtil.getIp()) >= 5;
		if(isIpBlock) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다");
		}
		
		// 1. Salt 조회
		String salt = this.memberDao.selectSalt(loginMemberVO.getEmail());
		if(salt == null) {
			
			AccessLogVO accessLogVO = new AccessLogVO();
			accessLogVO.setAccessType("LOGIN");
			accessLogVO.setAccessUrl(RequestUtil.getRequest().getRequestURI());
			accessLogVO.setAccessMethod(RequestUtil.getRequest().getMethod().toUpperCase());
			accessLogVO.setAccessIp(RequestUtil.getIp());
			
			this.accessLogDao.insertNewAccessLog(accessLogVO);
			
			throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다");
		}
		
		// 2. 사용자가 입력한 비밀번호를 salt를 이용해 암호화
		String password = loginMemberVO.getPassword();
		password = this.sha.getEncrypt(password, salt);
		loginMemberVO.setPassword(password);
		
		// 3. 이메일과 암호화된 비밀번호로 데이터베이스에서 회원 정보 조회
		MemberVO memberVO = this.memberDao.selectOneMember(loginMemberVO);
		if(memberVO == null) { // 이메일이 있지만 비밀번호가 일치하지 않음
			
			// LOGIN_FAIL_COUNT 하나 증가시킨다.
			// LATEST_LOGIN_FAIL_DATE 현재 날짜로 갱신한다.
			// LATEST_LOGIN_IP 요청자의 IP로 갱신한다.
			loginMemberVO.setIp(RequestUtil.getIp());
			this.memberDao.updateLoginFailState(loginMemberVO);
			throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다");
		}
		
		// LOGIN_FAIL_COUNT가 5 이상 && 마지막 로그인 실패시간이 1시간이 지나지 않았다면,
		// 정상적인 로그인 시도라고 하더라도 로그인은 실패시켜야 한다
		boolean isBlockState = this.memberDao.selectLoginImpossibleCount(loginMemberVO.getEmail()) > 0;
		if(isBlockState) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다");
		}
		
		// LOGIN_FAIL_COUNT가 5 이상 && 마지막 로그인 실패시간이 1시간이 지났다면,
		// 혹은 LOGIN_FAIL_COUNT가 5 미만일 경우
		// 정상적인 로그인 시도일 경우 로그인을 성공시킨다.
		// 이 때, LOGIN_FAIL_COUNT는 0으로 초기화시키고 LATEST_LOGIN_FAIL_DATE는 NULL로 초기화.
		// LATEST_LOGIN_IP 요청자의 IP로 갱신. LATEST_LOGIN_SUCCESS_DATE는 현재 날짜로 갱신.
		
		loginMemberVO.setIp(RequestUtil.getIp());
		this.memberDao.updateLoginSuccessState(loginMemberVO);
		
		AccessLogVO accessLogVO = new AccessLogVO();
		accessLogVO.setAccessType("LOGIN");
		accessLogVO.setAccessEmail(loginMemberVO.getEmail());
		accessLogVO.setAccessUrl(RequestUtil.getRequest().getRequestURI());
		accessLogVO.setAccessMethod(RequestUtil.getRequest().getMethod().toUpperCase());
		accessLogVO.setAccessIp(RequestUtil.getIp());
		accessLogVO.setLoginSuccessYn("Y");
		this.accessLogDao.insertNewAccessLog(accessLogVO);
		
		return memberVO;
	}
	
	@Override
	public boolean deleteMe(String email) {
		int deleteCount = memberDao.deleteMe(email);
		return deleteCount > 0;
	}
}
