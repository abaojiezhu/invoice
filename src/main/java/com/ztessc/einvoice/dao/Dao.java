package com.ztessc.einvoice.dao;

import java.util.List;

public interface Dao {

	public Object save(String str, Object obj) throws Exception;
	
	public Object update(String str, Object obj) throws Exception;
	
	public Object delete(String str, Object obj) throws Exception;

	public List<String> listForString(String str, Object obj) throws Exception;
	
	public Object findForMap(String sql, Object obj, String key , String value) throws Exception;
	
}
