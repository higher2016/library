package mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.higherli.library.log.LogInit;

public class TestBeanMapper{

	private static SqlSessionFactory sqlSessionFactory;

	@BeforeClass
	public static void beforeClass() throws IOException {
		LogInit.init();
		Reader reader = Resources.getResourceAsReader("mybatistest/mybatis-cfg.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		deleteAll();
	}
	
	@AfterClass
	public static void afterClass(){
		deleteAll();
	}

	private static void deleteAll() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		BeanMapper mapper = sqlSession.getMapper(BeanMapper.class);
		mapper.deleteBean(1);
		sqlSession.commit();
		sqlSession.close();
	}

	@Test
	public void testInsertBean() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		BeanMapper mapper = sqlSession.getMapper(BeanMapper.class);
		Bean bean = new Bean(1, 2, "s");
		mapper.insertBean(bean);
		sqlSession.commit();
		sqlSession.close();
	}

	@Test
	public void testSelectOneById() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		BeanMapper mapper = sqlSession.getMapper(BeanMapper.class);
		mapper.selectOneById(1);
		mapper.selectOneById(2);
		sqlSession.close();
	}

	@Test
	public void testSelectAllBean() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		BeanMapper mapper = sqlSession.getMapper(BeanMapper.class);
		mapper.selectAllBean();
	}
}
