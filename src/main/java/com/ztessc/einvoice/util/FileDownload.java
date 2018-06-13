package com.ztessc.einvoice.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;



public class FileDownload {

	private static final Logger log = Log.get();
	/**
	 * @param response 
	 * @param filePath		//文件完整路径(包括文件名和扩展名)
	 * @param fileName		//下载后看到的文件名
	 * @param isDelete		//是否删除,true删除，false不删除
	 * @return  文件名
	 */
	public static void fileDownload(final HttpServletResponse response,
			String filePath, String fileName, boolean isDelete) {

		OutputStream outputStream = null;
		try {
			byte[] data = FileUtil.toByteArray2(filePath);
			// fileName = URLEncoder.encode(fileName, "UTF-8");
			fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/x-msdownload;charset=UTF-8");
			outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(data);
		} catch (IOException e) {
			log.error("下载文件失败", e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
					response.flushBuffer();
					File file = new File(filePath);
					if (file != null && file.exists()) {
						if (isDelete) {
							file.delete();
						}
					}
				} catch (IOException e) {
					log.error("关闭流或者删除缓存文件失败", e);
				}
			}
		}
	}
	
	public static void fileDownload(String sourFile, String saveFile) {
		File file = new File(saveFile);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		InputStream is = null;
		OutputStream os = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		byte[] bt = new byte[4096];	// 1K
		int leng = 0;
		try {
			URL url = new URL(sourFile);
			// 从服务器上获取图片并保存
			URLConnection connection = url.openConnection();
			is = connection.getInputStream();
			
			//为了提高读取速度使用字节缓冲流
			bis = new BufferedInputStream(is);
			os = new FileOutputStream(saveFile);
			bos = new BufferedOutputStream(os);
			while((leng = bis.read(bt)) != -1){
				//写bt数组中的数据
				//0表示bt数组下标从0开始
				//leng表示数组写入的长度
				bos.write(bt, 0, leng);
			}
			bos.flush();	//刷新,清空缓冲区
		} catch (FileNotFoundException e) {
			log.error("文件不存在",e);
		} catch (IOException e) {
			log.error("下载文件失败", e);
		} finally {
			//关闭相应连接
			try {
				if(bos != null){
					bos.close();
					bos = null;
				}
				else if(os != null){
					os.close();
					os = null;
				}
				else if(bis != null){
					bis.close();
					bis = null;
				}
				else if(is != null){
					is.close();
					is = null;
				}
			} catch (IOException e) {
				log.error("关闭流或者删除缓存文件失败", e);
			} finally {
				bos = null;
				os = null;
				bis = null;
				is = null;
			}
		}
	}

}
