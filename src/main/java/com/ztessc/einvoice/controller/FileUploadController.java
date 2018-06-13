package com.ztessc.einvoice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ztessc.einvoice.exception.BizException;
import com.ztessc.einvoice.service.AppBootConfigService;
import com.ztessc.einvoice.util.Const;
import com.ztessc.einvoice.util.FileDownload;
import com.ztessc.einvoice.util.FileUpload;
import com.ztessc.einvoice.util.FileUtil;
import com.ztessc.einvoice.util.MessageUtil;
import com.ztessc.einvoice.util.PageData;
import com.ztessc.einvoice.util.UuidUtil;

@RequestMapping(value = "/file")
@RestController
public class FileUploadController extends BaseController {

	@Autowired
	private AppBootConfigService appBootConfigService;
	
	/**
	 * 文件上传
	 * @author: 徐益森
	 * @date: 2018年4月18日 下午6:47:57
	 * @param {"type":"1001"}
	 * @return PageData
	 */
	@PostMapping(value = "/upload")
	public PageData upload(MultipartHttpServletRequest multipartHttpServletRequest) {
		PageData params = this.getPageData();
		String dirPath = Const.FILE_UPLOAD_DIR.get(params.getString("type"));
		if(StringUtils.isBlank(dirPath)) {
			throw new BizException("类型参数不存在");
		}
		MultipartFile file = multipartHttpServletRequest.getFile("file");
		if(file != null){
			String fileName =  FileUpload.fileUp(file, FileUtil.getUploadPath(dirPath), UuidUtil.getUUID());
			params.put("fileName", FileUtil.getDownloadPath(dirPath, fileName));
			params.put("realName", file.getOriginalFilename());
		}
		return MessageUtil.getSuccessMessage(params);
	}
	
	/**
	 * 文件下载
	 * @author: 徐益森
	 * @date: 2018年4月19日 上午8:51:59
	 * @param {"type":"1001","fileName":"xxxx.png"}
	 * @return void
	 */
	@PostMapping(value="/download")
	public void download(HttpServletRequest request,HttpServletResponse response){
		PageData params = this.getPageData();
		String dirPath = Const.FILE_UPLOAD_DIR.get(params.getString("type"));
		String fileName = params.getString("fileName");
		FileDownload.fileDownload(response, FileUtil.getUploadPath(dirPath) + fileName, fileName, false);
	}
}
