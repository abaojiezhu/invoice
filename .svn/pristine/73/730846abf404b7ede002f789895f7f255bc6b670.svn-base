package com.ztessc.einvoice.util;

import java.util.List;

public class TreeUtil {

	public static void buildTrees(List<PageData> allList, String parentId, List<PageData> childList) {
		for (PageData data : allList) {
			if(parentId.equals(data.getString("parentId"))) {
				childList.add(data);
				buildTrees(allList, data.getString("id"), childList);
			}
		}
	}
	
}
