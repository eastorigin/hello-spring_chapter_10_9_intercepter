package com.ktdsuniversity.edu.hello_spring.access.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.hello_spring.access.dao.AccessLogDao;
import com.ktdsuniversity.edu.hello_spring.access.vo.AccessLogVO;

@Repository
public class AccessLogDaoImpl extends SqlSessionDaoSupport implements AccessLogDao{

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public int insertNewAccessLog(AccessLogVO accessLogVO) {
		return getSqlSession().insert(NAMESPACE+ ".insertNewAccessLog", accessLogVO);
	}
	
	@Override
	public int selectLoginFailCount(String ip) {
		return getSqlSession().selectOne(NAMESPACE + ".selectLoginFailCount", ip);
	}
}
