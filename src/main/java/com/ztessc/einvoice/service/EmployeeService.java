package com.ztessc.einvoice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztessc.einvoice.dao.DaoSupport;
import com.ztessc.einvoice.util.DateUtil;
import com.ztessc.einvoice.util.MD5;
import com.ztessc.einvoice.util.PageData;

@Service
public class EmployeeService {
	@Autowired
	private DaoSupport daoSupport;
	

	public List<PageData> listBaseUser(PageData pd) {
		String registerStartDt = pd.getString("registerStartDt");
		String registerEndDt = pd.getString("registerEndDt");
		if(StringUtils.isNotBlank(registerStartDt) && StringUtils.isNotBlank(registerEndDt)){
			Date data = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				 data = sdf.parse(registerEndDt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.DAY_OF_MONTH, 1);
			
			Date time = c.getTime();
			pd.put("registerEndDt",time);
		}
		return daoSupport.listForPageData("EmployeeMapper.listBaseUser", pd);
	}
	
	
	
	@Transactional
	public void save(PageData pd) {
		
		String pwd = pd.getString("password");
		
		if(StringUtils.isNotBlank(pwd)){
			String password = MD5.md5(pwd);
			pd.put("password", password);
		}
		
		//获取当前时间
		pd.fillCreatedData();
		
		pd.fillUpdatedData();
		
		pd.put("registerDt", DateUtil.getCurrentDateTime());
		
		daoSupport.save("EmployeeMapper.save", pd);
	}

	@Transactional
	public void update(PageData pd) {
		
		String enabled = pd.getString("enabled");
		String pwd = pd.getString("password");
		
		if(StringUtils.isNotBlank(enabled)){
			if("Z001002".equals(enabled)){
				pd.put("disableDt", DateUtil.getCurrentDateTime());
			}
		}
		
		if(StringUtils.isNotBlank(pwd)){
			String password = MD5.md5(pwd);
			pd.put("password", password);
		}
		
		//获取当前时间
		pd.fillUpdatedData();
		pd.transEmptyToNull();
		daoSupport.update("EmployeeMapper.update", pd);
	}
	

}
