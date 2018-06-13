package com.ztessc.einvoice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ztessc.einvoice.service.TestService;
import com.ztessc.einvoice.util.DateUtil;
import com.ztessc.einvoice.util.FileUpload;
import com.ztessc.einvoice.util.FileUtil;
import com.ztessc.einvoice.util.Log;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;

@RestController
//@RequestMapping("/init")
public class TestController extends BaseController{

	private final Logger log = Log.get();
	
	@Autowired
	private TestService testService;
	
	@RequiresPermissions("发票获取")
	@RequestMapping("/findByPage")
	@ResponseBody
    public PageData findByPage() {
//		PageData user = ShiroUtil.getCurrentUserData();
		PageData pageData = this.getPageData();
		System.out.println(pageData);
		PageData pd = new PageData();
//		pd.put("name", "张三");
//		Page<Object> page = PageHelper.startPage(0, 2);
		List<PageData> list = testService.findAll(pd);
//		for (PageData data : list) {
//			System.out.println("data=====" + data);
//		}
		log.debug("list---------" + list);
//		ShiroUtil.clearAuthorizationCache();
//        return MessageUtil.getSuccessMessage(this.getPageResult(page, list));
		pd.put("list", list);
		
		return pd;
    }
	
//	@RequiresPermissions("发票新增")
	@RequestMapping("/save")
	@ResponseBody
    public PageData save(HttpServletRequest request) {
		return MessageUtil.getSuccessMessage("发票新增成功");
	}
	
	@RequestMapping("/upload")
	@ResponseBody
    public PageData upload() throws IOException {
		FileInputStream in = new FileInputStream(new File("D:\\helloworld.png"));
		FileUpload.copyFile(in, FileUtil.getUploadPath("boot"), "helloworld.png");
		return MessageUtil.getSuccessMessage();
	}
	
	@RequestMapping("/download")
	@ResponseBody
    public PageData download() throws IOException {
//		String path = FileUtil.getDownloadPath("helloworld.png");
//System.out.println("path----------" + path);		
		return MessageUtil.getSuccessMessage();
	}
	
//    @Scheduled(cron = "0/5 * * * * *")
//    public void timer(){
//    	//获取当前时间
//        System.out.println("a当前时间为:" + DateUtil.getDateTime(new Date()));
//        List<PageData> list = testService.findAll(new PageData());
//        System.out.println(list);
//    }
    
}
