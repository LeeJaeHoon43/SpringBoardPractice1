package com.ljh.persistence;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.ljh.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	// 마이바티스.
	@Inject
	private SqlSession sqlSession;
	
	// 매퍼.
	private static String namespace = "com.ljh.mappers.replyMapper";

	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		return sqlSession.selectList(namespace + ".readReply", bno);
	}

	@Override
	public void writeReply(ReplyVO vo) throws Exception {
		sqlSession.insert(namespace + ".writeReply", vo);
	}

	@Override
	public ReplyVO readReplySelect(int rno) throws Exception {
		return sqlSession.selectOne(namespace + ".readReplySelect", rno);
	}

	@Override
	public void replyUpdate(ReplyVO vo) throws Exception {
		sqlSession.update(namespace + ".updateReply", vo);
	}

	@Override
	public void replyDelete(ReplyVO vo) throws Exception {
		sqlSession.delete(namespace + ".deleteReply", vo);
	}
}
