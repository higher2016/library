package mybatis;

import java.util.List;

public interface BeanMapper {
	 //接口方法默认自带public
    int insertBean(Bean bean);// 插入 int判断是否执行成功

    Bean selectOneById(int id);//查询一条数据

    List<Bean> selectAllBean();//查询列表 封装到list中
    
    void deleteBean(int id);
}
