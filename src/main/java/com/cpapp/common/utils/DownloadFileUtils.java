package com.cpapp.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载
 * @author PeiqiHuang
 *
 */
public class DownloadFileUtils {
	
	public static void downloadFile(File file, HttpServletResponse response) {
		if (file.exists()) {
            // response.setContentType("application/force-download");// 设置强制下载不打开
            response.setContentType("application/octet-stream");
            String appNameTrans = file.getName();
            try {
            	// 文件名中文乱码问题
				appNameTrans = new String(appNameTrans.getBytes("UTF-8"), "ISO-8859-1");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + appNameTrans);// 设置文件名
            response.addHeader("Content-Length", Long.toString(file.length()));// 设置文件大小
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
	}
}
