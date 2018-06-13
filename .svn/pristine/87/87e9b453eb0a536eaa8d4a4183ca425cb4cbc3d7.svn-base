package com.ztessc.einvoice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztessc.einvoice.dao.DaoSupport;

/**
 * 数据字典工具类
 */
public class DicUtil {
	
	private static DaoSupport daoSupport = (DaoSupport) ApplicationContextUtil.getContext().getBean("daoSupport");
	
	/**
	 * 存放类型和类型相关的数据字典
	 */
	public static Map<String,Map<String,String>[]> DIC_MAP = new HashMap<String, Map<String,String>[]>();
	
	/**
	 * 存放KEY-VALUE数据
	 */
	public static Map<String,String> DIC_MAP_SIMPLE = new HashMap<String, String>();
	
	public static Map<String,String> DIC_MAP_STRS = new HashMap<String, String>();
	
	
	//将数据字典数据初始化到缓存
	static {
		refresh();
	}
	
	public static String getValue(String key){
		return DIC_MAP_SIMPLE.get(key);
	}
	
	public static String getValue(String key,String parentKey){
		return DIC_MAP.get(parentKey) == null ? null
				: DIC_MAP.get(parentKey)[0].get(key);
	}
	
	public static String getKey(String value,String parentKey){
		return DIC_MAP.get(parentKey) == null ? null
				: DIC_MAP.get(parentKey)[1].get(value);
	}
	
	/**
	 * 通过父级 获取数据字典 属性
	 * @param parentKey
	 * @return
	 * @throws Exception
	 */
	public static String[] getValueArray(String parentKey) {
		if(DIC_MAP.get(parentKey) == null){
			return null;
		}
		return  DIC_MAP.get(parentKey)[0].values().toArray(new String[DIC_MAP.get(parentKey)[0].size()]);
	}
	
	private static void clear(){
		DIC_MAP.clear();
		DIC_MAP_SIMPLE.clear();
		DIC_MAP_STRS.clear();
	}
	
	public static List<PageData> getByParentKey(String parentKey){
		List<PageData> list = new ArrayList<PageData>();
		Map<String, String> map = DIC_MAP.get(parentKey)[0];
		if(map != null && map.size() > 0) {
			for(Map.Entry<String, String> entry : map.entrySet()) {
				PageData data = new PageData();
				data.put("key",entry.getKey());
				data.put("value", entry.getValue());
				list.add(data);
			}
		}
		return list;
	}
	
	
	/**
	 * 刷新缓存
	 */
	@SuppressWarnings("unchecked")
	public static void refresh(){
		clear();
		List<PageData> data = daoSupport.listForPageData("DictionaryMapper.findAll", null);
		Map<String,String>[] maps;
		String parentKey;
		String key;
		String value;
		for(PageData pd: data){
			parentKey = pd.getString("parentKey");
			key = pd.getString("dicKey");
			value = pd.getString("dicValue");
			DIC_MAP_SIMPLE.put(key, value);
			if(StringUtils.isBlank(parentKey)){
				continue;
			}
			maps = DIC_MAP.get(parentKey);
			if(maps == null){
				maps = new Map[2];
				//使用LinkedHashMap以保证顺序
				maps[0] = new LinkedHashMap<String, String>();
				maps[1] = new LinkedHashMap<String, String>();
				DIC_MAP.put(parentKey, maps);
			}
			maps[0].put(key, value);
			maps[1].put(value, key);
		}
		//初始化数据字典用逗号分隔的值字符串
		String strs;
		for(String parentCode : DIC_MAP.keySet()){
			Map<String,String> childs = DIC_MAP.get(parentCode)[0];
			if(childs == null || childs.isEmpty()){
				continue;
			}
			strs = "";
			for(String str : childs.values()){
				strs += "," + str;
			}
			DIC_MAP_STRS.put(parentCode, strs.substring(1));
		}
	}
}
