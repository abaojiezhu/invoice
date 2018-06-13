package com.ztessc.einvoice.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.ztessc.einvoice.exception.BizException;



public class FileUpload {
	
	static Logger log = Log.get();

	/**
	 * @param file 			//文件对象
	 * @param filePath		//上传路径
	 * @param fileName		//文件名
	 * @return  文件名
	 */
	public static String fileUp(MultipartFile file, String dirPath, String fileName){
		String subfix = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0){
				subfix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			return copyFile(file.getInputStream(), dirPath, fileName + subfix);
		} catch (IOException e) {
			throw new BizException("上传文件异常" + e.getMessage(),e);
		}
	}
	
	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	public static String copyFile(InputStream in, String dir, String realName)
			throws IOException {
		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}
}
