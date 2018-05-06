package mybatis_spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.higherli.library.log.LogInit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/mybatis_springtest/spring.xml" })
public class TestBeanMapper {
	static {
		LogInit.init();
	}

	@Autowired
	private BeanMapper mapper;

	@Test
	public void test() {
		Bean bean = new Bean(1, 2, "s");
		mapper.insertBean(bean);
	}
}
