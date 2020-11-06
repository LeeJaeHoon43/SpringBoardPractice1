package com.ljh.persistence;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.ljh.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{

	// 마이바티스.
	@Inject
	private SqlSession sqlSession;

	// 매퍼.
	private static String namespace = "com.ljh.mappers.memberMapper";

	@Override
	public void register(MemberVO vo) throws Exception {
		sqlSession.insert(namespace + ".register", vo);
	}

	@Override
	public MemberVO login(MemberVO vo) throws Exception {
//		return sqlSession.selectOne(namespace + ".login", vo);
		return sqlSession.selectOne(namespace + ".loginBcrypt", vo);
	}

	@Override
	public void modify(MemberVO vo) throws Exception {
		sqlSession.update(namespace + ".modify", vo);
	}

	@Override
	public void withdrawal(MemberVO vo) throws Exception {
		sqlSession.delete(namespace + ".withdrawal", vo);
	}

	@Override
	public MemberVO idCheck(String userId) throws Exception {
		return sqlSession.selectOne(namespace + ".idCheck", userId);
	}
}
