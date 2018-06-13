package com.ztessc.einvoice.task;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztessc.einvoice.exception.BizException;
import com.ztessc.einvoice.service.InvoiceService;
import com.ztessc.einvoice.service.SysTaskService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.DateUtil;
import com.ztessc.einvoice.util.HmacSHA1Util;
import com.ztessc.einvoice.util.HttpClientUtil;
import com.ztessc.einvoice.util.PageData;

@Component
@EnableScheduling
public class InvoiceVerificationCronTask implements SchedulingConfigurer {
	
	@Autowired
	private SysTaskService sysTaskService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private Environment environment;
	
	private final String TASK_CODE = "INVOICE_VERIFICATION";
	
	private final String cron = "0 0/1 * * * ?";
	
	private boolean enabled = false;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				if(enabled) {
					PageData param = new PageData();
					param.put("taskCode", TASK_CODE);
					PageData task = sysTaskService.findByCode(param);
					
					param.put("id", task.getString("id"));
					
					//请求开始时间
					Date beginTime = DateUtil.getCurrentDateTime();
					param.put("lastRunTime", beginTime);
					
					String url = environment.getProperty("invoice.check.url");
					String appId = environment.getProperty("invoice.check.appId");
					String encryptKey = environment.getProperty("invoice.check.encryptKey");
					
					List<PageData> list = invoiceService.listInvoiceCheck();
					for (PageData pageData : list) {
						String checkCode = pageData.getString("checkCode");
						if(StringUtils.isNotBlank(checkCode) && checkCode.length() >= 6){
							checkCode = checkCode.substring(checkCode.length()-6, checkCode.length());   //获取校验码后6位
						}
						pageData.put("checkCode", checkCode);
						Map<String,String> params = new HashMap<String, String>();
						params.put("data", pageData.toString());
						params.put("appId", appId);
						params.put("sign", HmacSHA1Util.encrypt(pageData.toString(), encryptKey));
						
						String result = HttpClientUtil.post(url, params);
						JSONObject data = JSON.parseObject(result);
						System.err.println(result);
						if((boolean) data.get("ok")){
							invoiceService.updateInvoice(result,pageData.getString("id"));
						}else{
							PageData pd = new PageData();
							pd.put("id", pageData.getString("id"));
							pd.put("validState", Const.DIC_VALID_STATE_TYPE_YYSB);
							invoiceService.update(pd);
						}
					}
					//请求结束时间
					Date endTime = DateUtil.getCurrentDateTime();
					param.put("lastEndTime", endTime);
					sysTaskService.updateByTask(param);
					
				}
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger trigger = new CronTrigger(cron);
				enabled = false;

				PageData param = new PageData();
				param.put("taskCode", TASK_CODE);
				PageData task = sysTaskService.findByCode(param);
				if(task != null) {
					if(Const.DIC_ENABLED_TYPE_QY.equals(task.getString("enabled"))) {
						// 立即执行：Z009001 ，  计划执行：Z009002
						try {
							if("Z009001".equals(task.getString("runType"))){ 
								if(StringUtils.isNotBlank(task.getString("planEndTime"))){
									if(DateUtil.getCurrentDateTime().getTime() <= DateUtil.fomatDateYmdhms(task.getString("planEndTime")).getTime()){
										enabled = true;
									}
								}	
							}else{
								if(StringUtils.isNotBlank(task.getString("planStartTime")) && StringUtils.isNotBlank(task.getString("planEndTime"))){
									if(DateUtil.getCurrentDateTime().getTime() >=DateUtil.fomatDateYmdhms(task.getString("planStartTime")).getTime()&&DateUtil.getCurrentDateTime().getTime() <= DateUtil.fomatDateYmdhms(task.getString("planEndTime")).getTime()){
										enabled = true;
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						

						String intervalTime = task.getString("intervalTime");
						// 任务触发，可修改任务的执行周期
						if(StringUtils.isNotBlank(intervalTime)){
							trigger = new CronTrigger(intervalTime);
						}
					}
				}
		        
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});
	}
}