package com.cpapp.sys.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cpapp.common.utils.DateUtils;
import com.cpapp.common.utils.DownloadFileUtils;
import com.cpapp.common.utils.LoadProperties;
import com.cpapp.sys.bean.LogFileBean;

/*******************************************************************************
 * 系统日志____Controller
 * 
 * @author zengxiangtao
 * @version 2016-07-01
 ******************************************************************************/
@Controller
@RequestMapping("/admin/sys/logFile/")
public class SysLogController {

	/* 日志文件列表 */
	@RequestMapping("/logDisplay")
	public ModelAndView logDisplay() {
		ModelAndView mav = new ModelAndView("/admin/sys/logFile/logDisplay");
		return mav;
	}

	/* 日志文件数据 */
	@RequestMapping("/fileData")
	@ResponseBody
	public List<LogFileBean> fileData(
			@RequestParam(value = "filePathStr", required = false) String filePathStr) {
		filePathStr = StringUtils.isBlank(filePathStr) ? LoadProperties
				.getByDefaultProperties("my_logFilePath") : filePathStr;
		File file = new File(filePathStr);
		LogFileBean bean = new LogFileBean();
		bean.setFileName(file.getName());
		bean.setDownloadPath(file.getAbsolutePath());
		bean.setFolder(file.isDirectory());
		bean.setId(Long.valueOf("1"));
		bean.setDateStr(DateUtils.formatDate(new Date(file.lastModified()),
				"yyyy-MM-dd HH:mm:ss"));
		bean.setFileSize(file.length());
		bean.setChildren(showChildrenFile(file, 1));
		bean.setState("closed");
		if(bean.getChildren().isEmpty()) {
			//空文件夹插件显示为文件图标
			bean.setIconCls("tree-folder");
		}
		List dataList = new ArrayList(1);
		dataList.add(bean);
		return dataList;
	}

	/* 日志文件下载 */
	@RequestMapping("downloadLogFile")
	public void downloadLogFile(
			@RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "filePath") String filePath,
			HttpServletResponse response) throws IOException {
		File file = new File(filePath);
		/*if (!file.exists()) {
			return null;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);*/
		DownloadFileUtils.downloadFile(file, response);
	}

	private List<LogFileBean> showChildrenFile(File f, long pareneId) {
		if (null != f && f.exists()) {
			File[] files = f.listFiles();
			List<LogFileBean> sub = new ArrayList<LogFileBean>();
			for (int i = 0; i < files.length; i++) {
				LogFileBean lfb = new LogFileBean();
				lfb.setFileName(files[i].getName());
				lfb.setDownloadPath(files[i].getAbsolutePath());
				lfb.setFolder(files[i].isDirectory());
				lfb.setId(pareneId * 10 + i);
				lfb.setDateStr(DateUtils.formatDate(
						new Date(files[i].lastModified()),
						"yyyy-MM-dd HH:mm:ss"));
				lfb.setFileSize(files[i].length());
				if (files[i].isDirectory()) {
					lfb.setChildren(showChildrenFile(files[i], lfb.getId()));
		            if (files[i].list().length > 0) {
		                lfb.setState("closed");
		            } else {
		            	//空文件夹插件显示为文件图标
		            	lfb.setIconCls("tree-folder");
		            }
				}
				sub.add(lfb);
			}
			return sub;
		}
		return null;
	}
}
