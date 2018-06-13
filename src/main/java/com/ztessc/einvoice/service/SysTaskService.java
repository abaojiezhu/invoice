package com.ztessc.einvoice.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.exception.BizException;
import com.ztessc.einvoice.util.DateUtil;
import com.ztessc.einvoice.util.PageData;

@Service
public class SysTaskService {
	@Autowired
	private DaoSupport daoSupport;
	

	/**
	 * 
	 * @return
	 */
	public List<PageData> listSysTask(PageData pd) {
		String updatedEndDt = pd.getString("updatedEndDt");
		if(StringUtils.isNotBlank(updatedEndDt)){
			Date date = DateUtil.fomatDateYmd(updatedEndDt);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, 1);
			
			Date time = c.getTime();
			pd.put("updatedEndDt",time);
		}
		List<PageData> list = daoSupport.listForPageData("SysTaskMapper.listSysTasks", pd);
		
		for (PageData pageData : list) {
			String sec = null;
			String min = null;
			String date = null;
			String intervalTime = pageData.getString("intervalTime");
			String[] b = intervalTime.split(" ");
			if(b[1].contains("/")){
				String[] split = b[1].split("/");
				sec = split[1];
			}else{
				sec = b[1];
			}
			
			if(b[2].contains("/")){
				String[] split = b[2].split("/");
				 min = split[1];
			}else{
				 min = b[2];
			}
			if(b[3].contains("/")){
				String[] split = b[3].split("/");
				date = split[1];
			}else{
				date = b[3];
			}
			
			if(sec.equals("*")){
				sec = "0";
			}
			if(min.equals("*")){
				min = "0";
			}
			if(date.equals("*")){
				date = "0";
			}
			pageData.put("intervalTimeValue", sec +"," +min +","+ date);
		}
		
		return list;
	}
	
	public PageData findByCode(PageData pd) {
		return daoSupport.findForPageData("SysTaskMapper.findByCode", pd);
	}
	
	@Transactional
	public void update(PageData pd) {
		if(StringUtils.isNotBlank(pd.getString("planStartTime"))){
			if(pd.getString("planStartTime").length() < 19){
				pd.put("planStartTime", pd.getString("planStartTime")+":00");
			}
		}
		if(StringUtils.isNotBlank(pd.getString("planEndTime"))){
			if(pd.getString("planEndTime").length() < 19){
				pd.put("planEndTime", pd.getString("planEndTime")+":00");
			}
		}
		
		// 立即执行：Z009001 ，  计划执行：Z009002
		if("Z009001".equals(pd.getString("runType"))){ 
			if(StringUtils.isNotBlank(pd.getString("planEndTime"))){
				Date planEndTime = DateUtil.fomatDateYmdhms(pd.getString("planEndTime"));
				if(planEndTime.getTime() < DateUtil.getCurrentDateTime().getTime()){
					throw new BizException("计划结束时间不能小于当前服务器时间！");
				}
			}else{
				throw new BizException("计划结束时间不能为空！");
			}
			
		}else{
			if(StringUtils.isBlank(pd.getString("planStartTime"))){
				throw new BizException("计划开始时间不能为空！");
			}
			if(StringUtils.isBlank(pd.getString("planEndTime"))){
				throw new BizException("计划结束时间不能为空！");
			}
			if(DateUtil.fomatDateYmdhms(pd.getString("planStartTime")).getTime()>= DateUtil.fomatDateYmdhms(pd.getString("planEndTime")).getTime()){
				throw new BizException("计划结束时间不能小于计划开始时间！");
			}
		}
		
		
		String intervalTime = pd.getString("intervalTime");
		
		if(StringUtils.isNotBlank(intervalTime)){
    		String[] intervalTimeAry = intervalTime.split(",");
    		int m = Integer.parseInt(intervalTimeAry[0]);  //分钟
    		int h = Integer.parseInt(intervalTimeAry[1]);  //小时
    		int d = Integer.parseInt(intervalTimeAry[2]);  //天
    		String a = "0";
    		
    		if(m > 0 && h < 1 && d < 1){
    			a = a + " 0/" + m ;
    		}else if(m > 0 && h > 0 || d > 0){
    			a = a + " " + m ;
    		}else{
    			a = a + " *" ;
    		}
    		
    		if(h > 0 && d < 1 ){
    			a = a + " 0/" + h ;
    		}else if(h > 0 && d > 0){
    			a = a + " " + h ;
    		}else{
    			a = a + " *" ;
    		}
    		
    		if(d > 0){
    			a = a + " 1/" + d ;
    		}else{
    			a = a + " *" ;
    		}
    		
    		a = a + " * ?" ;
    		
    		pd.put("intervalTime", a);
    	}
		
		
		//获取当前时间
		pd.fillUpdatedData();
		
		daoSupport.update("SysTaskMapper.update", pd);
	}
	
	@Transactional
	public void updateByTask(PageData pd) {
		daoSupport.update("SysTaskMapper.update", pd);
	}
	

	
	
	
}
