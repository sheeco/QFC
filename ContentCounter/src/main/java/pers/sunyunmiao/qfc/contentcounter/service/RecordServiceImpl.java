package pers.sunyunmiao.qfc.contentcounter.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import pers.sunyunmiao.qfc.contentcounter.mapper.RecordMapper;
import pers.sunyunmiao.qfc.contentcounter.po.Record;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RecordServiceImpl implements RecordService {

	public List<Record> getRecordList() throws Exception {

		SqlSession sqlSession = getSqlSession();
		//映射sql的标识字符串
		String statement = "pers.sunyunmiao.qfc.contentcounter.mapper.RecordMapper.selectAll";
		//执行插入操作
		List<Record> list = sqlSession.selectList(statement);
		//手动提交事务
		sqlSession.commit();
		//使用SqlSession执行完SQL之后需要关闭SqlSession
		sqlSession.close();
		return list;
	}

	@Override
	public boolean ifExists(String url) throws Exception {

		SqlSession sqlSession = getSqlSession();
		String statement = "pers.sunyunmiao.qfc.contentcounter.mapper.RecordMapper.selectByUrl";
		List<Record> list = sqlSession.selectList(statement, url);
		sqlSession.commit();
		sqlSession.close();
		return !list.isEmpty();
	}

	public void insert(Record record) throws Exception {

		SqlSession sqlSession = getSqlSession();
		String statement = "pers.sunyunmiao.qfc.contentcounter.mapper.RecordMapper.insert";
		int ret = sqlSession.insert(statement, record);
		sqlSession.commit();
		sqlSession.close();

	}

	@Override
	public void update(Record record) throws Exception {

		SqlSession sqlSession = getSqlSession();
		String statement = "pers.sunyunmiao.qfc.contentcounter.mapper.RecordMapper.updateByUrl";
		int ret = sqlSession.update(statement, record);
		sqlSession.commit();
		sqlSession.close();
	}

	/**
	 * 获取SqlSessionFactory
	 *
	 * @return SqlSessionFactory
	 */
	public static SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
		return factory;
	}

	/**
	 * 获取SqlSession
	 *
	 * @return SqlSession
	 */
	public static SqlSession getSqlSession() throws IOException {
		return getSqlSessionFactory().openSession();
	}

	/**
	 * 获取SqlSession
	 *
	 * @param isAutoCommit true 表示创建的SqlSession对象在执行完SQL之后会自动提交事务
	 *                     false 表示创建的SqlSession对象在执行完SQL之后不会自动提交事务，这时就需要我们手动调用sqlSession.commit()提交事务
	 * @return SqlSession
	 */
	public static SqlSession getSqlSession(boolean isAutoCommit) throws IOException {
		return getSqlSessionFactory().openSession(isAutoCommit);
	}
}
