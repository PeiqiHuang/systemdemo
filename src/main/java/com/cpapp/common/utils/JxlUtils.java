package com.cpapp.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/*******************************************************************************
 * jxl工具类
 * 
 * @author zengxiangtao
 ******************************************************************************/
public class JxlUtils {

	/** 读取会员卡excel文件 */
	public static List<Object[]> readHotSettingExcelFile(String hotType,
			InputStream is) {
		List<Object[]> list = null;
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(is);
			if (null != workbook) {
				// 获得第一页工作簿
				Sheet sheet = workbook.getSheet(0);
				if (null != sheet) {
					// 获得数据记录数
					int sheetSize = sheet.getRows();
					if (sheetSize > 1) {
						list = new ArrayList<Object[]>(sheetSize);
						for (int i = 0; i < sheetSize; i++) {
							Cell[] arr = sheet.getRow(i);
							if (null != arr
									&& arr.length > 0
									&& StringUtils.isNotBlank(arr[0]
											.getContents())) {
								list.add(new Object[] {
										arr[0].getContents().trim(), hotType, i });
							}
						}
					}
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != workbook) {
				workbook.close();
			}
		}
		return list;
	}

	/** 读取兑码码excel文件 */
	public static List<String> readRedeeCodeExcelFile(MultipartFile mf) {
		List<String> list = null;
		Workbook workbook = null;
		InputStream is = null;
		try {
			is = mf.getInputStream();
			workbook = Workbook.getWorkbook(is);
			if (null != workbook) {
				// 获得第一页工作簿
				Sheet sheet = workbook.getSheet(0);
				if (null != sheet) {
					// 获得数据记录数
					int sheetSize = sheet.getRows();
					if (sheetSize > 1) {
						list = new ArrayList<String>(sheetSize);
						for (int i = 0; i < sheetSize; i++) {
							Cell[] arr = sheet.getRow(i);
							if (null != arr
									&& arr.length > 0
									&& StringUtils.isNotBlank(arr[0]
											.getContents())) {
								list.add(arr[0].getContents().trim());
							}
						}
					}
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != workbook) {
				workbook.close();
			}
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
