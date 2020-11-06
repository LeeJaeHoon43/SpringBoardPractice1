package com.ljh.service;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.ljh.domain.MemberVO;
import com.ljh.persistence.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Inject
	private MemberDAO dao;

	@Override
	public void register(MemberVO vo) throws Exception {
		dao.register(vo);
	}

	@Override
	public MemberVO login(MemberVO vo) throws Exception {
		return dao.login(vo);
	}

	@Override
	public void modify(MemberVO vo) throws Exception {
		dao.modify(vo);
	}

	@Override
	public void withdrawal(MemberVO vo) throws Exception {
		dao.withdrawal(vo);
	}

	@Override
	public MemberVO idCheck(String userId) throws Exception {
		return dao.idCheck(userId);
	}
}
