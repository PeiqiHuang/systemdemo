package com.cpapp.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cpapp.common.utils.string.UUIDUtils;

/**
 * 文件下载
 * @author PeiqiHuang
 *
 */
public class UploadFileUtils {
	
	public static String STATUS = "status";
	public static String URL = "url";
	public static String MSG = "msg";
	
	/**
	 * 
	 * @param uploadFile
	 * @param fileName 要保存的文件名
	 * @param tempPath 上传到的临时文件夹
	 * @param desPath 保存表单时临时文件转移的目的文件夹，这里可为空
	 * @return
	 */
	public static Map<String, Object> uploadFile(MultipartFile uploadFile, String fileName, String tempPath, String desPath) {
		Map<String, Object> data = new HashMap<String, Object>(2);
		
		File file = new File(tempPath);
		if (!file.exists()) {
			file.mkdirs();
		} else {
			// 清空temp文件夹
			for (File child : file.listFiles()) {
				child.delete();
			}
		}
		String tempFilePath = tempPath + fileName;
		File tempFile = new File(tempFilePath);
		if ((new File(desPath + fileName)).exists()) {
			data.put(STATUS, false);
			data.put(MSG, "已存在同名文件，请上传另外的文件或者修改文件名称后再上传");
			return data;
		}
		
		// 上传文件到temp文件夹
		try {
			FileUtils.writeByteArrayToFile(tempFile, uploadFile.getBytes());
		} catch (IOException e) {
			data.put(STATUS, false);
			data.put(MSG, "上传文件失败");
			return data;
		}
		
		data.put(STATUS, true);
		return data;
	}
}
