package com.ztessc.einvoice.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ztessc.einvoice.util.PageData;


@Repository
public class DaoSupport implements Dao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public Object save(String str, Object obj) {
		try{
			return sqlSessionTemplate.insert(str, obj);
		}catch(Exception e){
			throw new RuntimeException("添加数据异常",e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void batchInsert(String str, List objs) {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate
				.getSqlSessionFactory();
		// 批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(
				ExecutorType.BATCH, false);
		try {
			if (objs != null) {
				for (int i = 0, size = objs.size(); i < size; i++) {
					sqlSession.insert(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		} catch (Exception e) {
			throw new RuntimeException("批量添加数据异常",e);
		} finally {
			sqlSession.close();
		}
	}
	
	@Override
	public Object update(String str, Object obj)  {
		try{
			return sqlSessionTemplate.update(str, obj);
		}catch(Exception e){
			throw new RuntimeException("更新数据异常",e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void batchDelete(String str, List objs ) {
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		// 批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try{
			if (objs != null) {
				for (int i = 0, size = objs.size(); i < size; i++) {
					sqlSession.delete(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}catch(Exception e){
			throw new RuntimeException("批量删除数据异常",e);
		}
	}
	
	@Override
	public Object delete(String str, Object obj)  {
		try{
			return sqlSessionTemplate.delete(str, obj);
		}catch(Exception e){
			throw new RuntimeException("删除数据异常",e);
		}
	}

	public PageData findForPageData(String str, Object obj)  {
		try{
			PageData pd = sqlSessionTemplate.selectOne(str, obj);
			pd = PageData.columnNamesToFieldNames(pd);
			return pd;
		}catch(Exception e){
			throw new RuntimeException("查询单条数据异常",e);
		}
	}

	@Override
	public List<String> listForString(String str, Object obj) {
		try{
			List<String> list = sqlSessionTemplate.selectList(str, obj);
			return list;
		}catch(Exception e){
			throw new RuntimeException("查询数据异常",e);
		}
	}
	
	public List<PageData> listForPageData(String str, Object obj) {
		try{
			List<PageData> list = sqlSessionTemplate.selectList(str, obj);
			list = PageData.columnNamesToFieldNames(list);
			return list;
		}catch(Exception e){
			throw new RuntimeException("查询数据异常",e);
		}
	}
	
	@Override
	public Object findForMap(String str, Object obj, String key, String value) {
		try{
			return sqlSessionTemplate.selectMap(str, obj, key);
		}catch(Exception e){
			throw new RuntimeException("查询Map类型数据异常",e);
		}
	}

}
