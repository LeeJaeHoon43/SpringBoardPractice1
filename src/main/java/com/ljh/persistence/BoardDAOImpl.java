package com.ljh.persistence;

import java.util.List;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.ljh.domain.BoardVO;
import com.ljh.domain.Criteria;
import com.ljh.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO{
	
	// 마이바티스.
	@Inject
	private SqlSession sqlSession;
	
	// 매퍼.
	private static String namespace = "com.ljh.mappers.boardMapper";

	@Override
	public void write(BoardVO vo) throws Exception {
		sqlSession.insert(namespace + ".write", vo);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return sqlSession.selectOne(namespace + ".read", bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update(namespace + ".update", vo);
	}

	@Override
	public void delete(int bno) throws Exception {
		sqlSession.delete(namespace + ".delete", bno);
	}

	@Override
	public List<BoardVO> list() throws Exception {
		return sqlSession.selectList(namespace + ".list");
	}

	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception {
		return sqlSession.selectList(namespace + ".listPage", cri);
	}

	@Override
	public int listCount() throws Exception {
		return sqlSession.selectOne(namespace + ".listCount");
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria scri) throws Exception {
		return sqlSession.selectList(namespace + ".listSearch", scri);
	}

	@Override
	public int countSearch(SearchCriteria scri) throws Exception {
		return sqlSession.selectOne(namespace + ".countSearch", scri);
	}
}
